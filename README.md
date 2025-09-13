API Auto-Escola — Checkpoint 1

Projeto Spring Boot (3ESPF/3ESA) com CRUD de Instrutor e Aluno, health-check, Flyway e agendamento/cancelamento de aulas com regras de negócio.

👥 Integrantes

GUILHERME ROCHA BIANCHINI - RM97974
NIKOLAS RODRIGUES MOURA DOS SANTOS - RM551566
PEDRO HENRIQUE PEDROSA TAVARES - RM97877
RODRIGO BRASILEIRO - RM98952
THIAGO JARDIM DE OLIVEIRA - RM551624


🧰 Stack

Java 21 (compatível com 17)

Spring Boot 3.5.4 — Web, Data JPA, Validation, DevTools

Hibernate 6, Flyway, MySQL 8

Lombok

Maven

🚀 Como rodar
1) Banco de dados

Crie o schema no MySQL:

CREATE DATABASE autoescola CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

2) Configuração do Application.properties

(src/main/resources/application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/autoescola?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=(insira sua senha aqui) no arquivo!

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration


Ao iniciar a aplicação, o Flyway aplica as migrações automaticamente.

3) Executar

Eclipse: rode a classe SpringBootProject3EspfApplication

Maven (CLI):

mvn spring-boot:run


Health: GET http://localhost:8080/health-check → API 3ESA OK!

🗃️ Migrações (Flyway)

V1: tabela instrutores

V2: instrutores.telefone

V3: instrutores.ativo

V4: ajuste ativo para BIT(1)

V5: tabela alunos

V6: tabela aulas

Se aparecer checksum mismatch, você pode dropar o DB e subir de novo, ou ajustar o checksum da versão na tabela flyway_schema_history pelo valor “Resolved locally” do log.

🌳 Estrutura (src/main/java)
br.com.fiap3espf.spring_boot_project
├─ SpringBootProject3EspfApplication.java
├─ controller
│  ├─ HealthCheckController.java
│  ├─ InstrutorController.java
│  ├─ AlunoController.java
│  └─ AulaController.java
├─ endereco
│  ├─ DadosEndereco.java
│  └─ Endereco.java
├─ instrutor
│  ├─ Instrutor.java
│  ├─ Especialidade.java
│  ├─ InstrutorRepository.java
│  ├─ DadosCadastroInstrutor.java
│  ├─ DadosAtualizacaoInstrutor.java
│  └─ DadosListagemInstrutor.java
├─ aluno
│  ├─ Aluno.java
│  ├─ AlunoRepository.java
│  ├─ DadosCadastroAluno.java
│  ├─ DadosAtualizacaoAluno.java
│  └─ DadosListagemAluno.java
└─ aula
   ├─ Aula.java
   ├─ AulaRepository.java
   ├─ AulaService.java
   ├─ StatusAula.java
   ├─ MotivoCancelamento.java
   ├─ DadosAgendamentoAula.java
   └─ DadosCancelamentoAula.java

🔌 API

Base URL: http://localhost:8080

Health

GET /health-check → "API 3ESA OK!"

Instrutor

POST /instrutor — cadastra

GET /instrutor?size=10&page=0&sort=nome — lista somente ativos (paginado)

PUT /instrutor — atualiza nome/telefone/endereco (não altera email/CNH/especialidade)

DELETE /instrutor/{id} — exclusão lógica (ativo=false)

Aluno

POST /aluno — cadastra (cpf com 11 dígitos)

GET /aluno?size=10&page=0&sort=nome — lista somente ativos (paginado)

PUT /aluno — atualiza nome/telefone/endereco (não altera email/CPF)

DELETE /aluno/{id} — exclusão lógica

Aulas (agendamento/cancelamento)

POST /aulas — agenda aula
Regras:

Seg–Sáb, 06:00–21:00 (aula dura 1h e deve terminar até 21:00)

Antecedência mínima: 30 min

Máximo 2 aulas/dia por aluno

Instrutor não pode estar ocupado no horário

Aluno/Instrutor devem estar ativos

instrutorId é opcional; o sistema escolhe um disponível aleatório

DELETE /aulas/{id} — cancela com body:

{ "motivo": "ALUNO_DESISTIU" }


Regra: somente com ≥24h de antecedência.

ℹ️ Observações

Campo booleano ativo usa BIT(1) no MySQL (compatível com Hibernate 6).

Em dev, para reiniciar migrações: dropar o DB e subir novamente.
