package br.com.fiap3espf.spring_boot_project.endereco;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    String logradouro;
    String numero;
    String complemento;
    String bairro;
    String cidade;
    String uf;
    String cep;

    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.numero = dados.numero();
        this.complemento = dados.complemento();
        this.bairro = dados.bairro();
        this.cidade = dados.cidade();
        this.uf = dados.uf();
        this.cep = dados.cep();
    }

    public void atualizarInformacoes(@Valid DadosEndereco dados) {
        if (dados.logradouro() != null) this.logradouro = dados.logradouro();
        if (dados.numero() != null) this.numero = dados.numero();
        if (dados.complemento() != null) this.complemento = dados.complemento();
        if (dados.bairro() != null) this.bairro = dados.bairro();
        if (dados.cidade() != null) this.cidade = dados.cidade();
        if (dados.uf() != null) this.uf = dados.uf();
        if (dados.cep() != null) this.cep = dados.cep();
    }
}
