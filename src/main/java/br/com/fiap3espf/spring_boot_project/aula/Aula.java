package br.com.fiap3espf.spring_boot_project.aula;

import br.com.fiap3espf.spring_boot_project.aluno.Aluno;
import br.com.fiap3espf.spring_boot_project.instrutor.Instrutor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Aula")
@Table(name = "aulas")
@Getter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "aluno_id")
    Aluno aluno;

    @ManyToOne(optional = true) @JoinColumn(name = "instrutor_id")
    Instrutor instrutor; // opcional no agendamento

    @Column(name = "data_hora_inicio", nullable = false)
    LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    StatusAula status;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento")
    MotivoCancelamento motivoCancelamento;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();
}
