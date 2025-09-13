package br.com.fiap3espf.spring_boot_project.aula;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoAula(
        @NotNull Long alunoId,
        Long instrutorId,
        @NotNull @Future LocalDateTime dataHoraInicio
) {}
