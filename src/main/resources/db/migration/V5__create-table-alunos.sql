CREATE TABLE alunos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    ativo BIT(1) NOT NULL DEFAULT b'1',
    PRIMARY KEY (id)
);

CREATE INDEX idx_alunos_nome ON alunos(nome);
CREATE UNIQUE INDEX uk_alunos_email ON alunos(email);
