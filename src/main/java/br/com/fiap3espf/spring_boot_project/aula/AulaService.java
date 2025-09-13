package br.com.fiap3espf.spring_boot_project.aula;

import br.com.fiap3espf.spring_boot_project.aluno.AlunoRepository;
import br.com.fiap3espf.spring_boot_project.instrutor.Instrutor;
import br.com.fiap3espf.spring_boot_project.instrutor.InstrutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AulaService {

    private static final LocalTime ABRE = LocalTime.of(6, 0);
    private static final LocalTime FECHA = LocalTime.of(21, 0); // aula deve TERMINAR até 21:00
    private static final Duration DURACAO = Duration.ofHours(1);
    private static final Duration ANTECEDENCIA_MIN = Duration.ofMinutes(30);
    private static final Duration CANCELA_MIN = Duration.ofHours(24);

    @Autowired AulaRepository aulaRepo;
    @Autowired AlunoRepository alunoRepo;
    @Autowired InstrutorRepository instrutorRepo;

    @Transactional
    public void agendar(@Valid DadosAgendamentoAula d) {
        var aluno = alunoRepo.findById(d.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        if (Boolean.FALSE.equals(aluno.getAtivo())) throw new IllegalStateException("Aluno inativo");

        var inicio = d.dataHoraInicio();
        var agora = LocalDateTime.now();
        if (!inicio.isAfter(agora.plus(ANTECEDENCIA_MIN))) {
            throw new IllegalArgumentException("Agendamento deve respeitar antecedência mínima de 30 minutos");
        }

        // janela de funcionamento: seg–sáb, 06:00–21:00 (fim <= 21:00)
        var diaSemana = inicio.getDayOfWeek();
        if (diaSemana == DayOfWeek.SUNDAY) throw new IllegalArgumentException("Domingo indisponível");
        var fim = inicio.plus(DURACAO);
        if (inicio.toLocalTime().isBefore(ABRE) || !fim.toLocalTime().isBefore(FECHA.plusSeconds(1))) {
            throw new IllegalArgumentException("Horário fora da janela 06:00–21:00 (aula dura 1h e deve terminar até 21:00)");
        }

        // máximo 2 aulas no dia por aluno
        var iniDia = inicio.toLocalDate().atTime(0,0);
        var fimDia = inicio.toLocalDate().atTime(23,59,59);
        long aulasAluno = aulaRepo.countByAluno_IdAndDataHoraInicioBetween(aluno.getId(), iniDia, fimDia);
        if (aulasAluno >= 2) throw new IllegalStateException("Aluno já possui 2 aulas no dia");

        Instrutor instrutor;
        if (d.instrutorId() != null) {
            instrutor = instrutorRepo.findById(d.instrutorId())
                    .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado"));
            if (Boolean.FALSE.equals(instrutor.getAtivo())) throw new IllegalStateException("Instrutor inativo");
            if (aulaRepo.existsByInstrutor_IdAndDataHoraInicio(instrutor.getId(), inicio)) {
                throw new IllegalStateException("Instrutor ocupado neste horário");
            }
        } else {
            List<Instrutor> livres = instrutorRepo.findDisponiveis(inicio);
            if (livres.isEmpty()) throw new IllegalStateException("Nenhum instrutor disponível neste horário");
            instrutor = livres.get(ThreadLocalRandom.current().nextInt(livres.size()));
        }

        var aula = new Aula(null, aluno, instrutor, inicio, fim, StatusAula.AGENDADA, null, LocalDateTime.now());
        aulaRepo.save(aula);
    }

    @Transactional
    public void cancelar(Long aulaId, @Valid DadosCancelamentoAula d) {
        var aula = aulaRepo.findById(aulaId)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada"));

        if (aula.getStatus() == StatusAula.CANCELADA) return;

        var agora = LocalDateTime.now();
        if (!aula.getDataHoraInicio().isAfter(agora.plus(CANCELA_MIN))) {
            throw new IllegalArgumentException("Cancelamento só é permitido com 24h de antecedência");
        }

        aula.status = StatusAula.CANCELADA;
        aula.motivoCancelamento = d.motivo();
        aulaRepo.save(aula);
    }
}
