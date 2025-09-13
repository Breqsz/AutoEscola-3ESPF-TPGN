CREATE TABLE aulas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aluno_id BIGINT NOT NULL,
    instrutor_id BIGINT NULL,
    data_hora_inicio DATETIME NOT NULL,
    data_hora_fim DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    motivo_cancelamento VARCHAR(30) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_aulas_aluno FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    CONSTRAINT fk_aulas_instrutor FOREIGN KEY (instrutor_id) REFERENCES instrutores(id)
);

CREATE INDEX idx_aulas_inicio ON aulas(data_hora_inicio);
CREATE INDEX idx_aulas_aluno_dia ON aulas(aluno_id, data_hora_inicio);
CREATE INDEX idx_aulas_instrutor_inicio ON aulas(instrutor_id, data_hora_inicio);
