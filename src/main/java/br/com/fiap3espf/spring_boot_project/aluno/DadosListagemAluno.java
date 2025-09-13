package br.com.fiap3espf.spring_boot_project.aluno;

public record DadosListagemAluno(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public DadosListagemAluno(Aluno a) {
        this(a.getId(), a.getNome(), a.getEmail(), a.getCpf());
    }
}
