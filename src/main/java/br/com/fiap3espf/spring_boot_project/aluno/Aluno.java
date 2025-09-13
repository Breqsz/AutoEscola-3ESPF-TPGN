package br.com.fiap3espf.spring_boot_project.aluno;

import br.com.fiap3espf.spring_boot_project.endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity(name = "Aluno")
@Table(name = "alunos")
@Getter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "BIT(1)")
    Boolean ativo = true;

    String nome;
    String email;
    String telefone;
    String cpf;

    @Embedded
    Endereco endereco;

    public Aluno(DadosCadastroAluno d) {
        this.nome = d.nome();
        this.email = d.email();
        this.telefone = d.telefone();
        this.cpf = d.cpf();
        this.endereco = new Endereco(d.endereco());
    }

    public void atualizar(@Valid DadosAtualizacaoAluno d) {
        if (d.nome() != null) this.nome = d.nome();
        if (d.telefone() != null) this.telefone = d.telefone();
        if (d.endereco() != null) this.endereco.atualizarInformacoes(d.endereco());
    }

    public void excluir() { this.ativo = false; }
}
