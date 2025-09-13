package br.com.fiap3espf.spring_boot_project.instrutor;

public record DadosListagemInstrutor(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade
) {
    public DadosListagemInstrutor(Instrutor dados) {
        this(dados.getId(), dados.getNome(), dados.getEmail(), dados.getCnh(), dados.getEspecialidade());
    }
}
