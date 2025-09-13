package br.com.fiap3espf.spring_boot_project.aula;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoAula(
        @NotNull MotivoCancelamento motivo
) {}
