package br.com.fiap3espf.spring_boot_project.aula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    boolean existsByInstrutor_IdAndDataHoraInicio(Long instrutorId, LocalDateTime inicio);

    long countByAluno_IdAndDataHoraInicioBetween(Long alunoId, LocalDateTime inicio, LocalDateTime fim);
}
