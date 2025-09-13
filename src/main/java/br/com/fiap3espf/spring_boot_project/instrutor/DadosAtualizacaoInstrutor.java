package br.com.fiap3espf.spring_boot_project.instrutor;

import br.com.fiap3espf.spring_boot_project.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoInstrutor(
        @NotNull Long id,
        String nome,
        String telefone,
        @Valid DadosEndereco endereco
) {}
