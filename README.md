# API Auto-Escola — Checkpoint 1

Projeto **Spring Boot (3ESPF/3ESA)** com **CRUD de Instrutor e Aluno**, **health-check**, **Flyway** e **agendamento/cancelamento de aulas** com regras de negócio.

---

## 👥 Integrantes
- **[Nome 1]** (RM: ______)
- **[Nome 2]** (RM: ______)
- **[Nome 3]** (RM: ______)

> Substitua pelos nomes/ RMs do grupo antes de enviar o link no Portal do Aluno.

---

## 🧰 Stack
- **Java 21** (compatível com 17)
- **Spring Boot 3.5.4** — Web, Data JPA, Validation, DevTools
- **Hibernate 6**, **Flyway**, **MySQL 8**
- **Lombok**
- **Maven**

---

## 🚀 Como rodar

### 1) Banco de dados
Crie o schema no MySQL:
```sql
CREATE DATABASE autoescola CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2) Configuração (arquivo `src/main/resources/application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autoescola?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=SUASENHA

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

> Ao iniciar, o **Flyway** aplica automaticamente as migrações (V1…V6).

### 3) Executar
- **Eclipse**: Run na classe `SpringBootProject3EspfApplication`
- **Maven (CLI)**:
```bash
mvn spring-boot:run
```

**Health-check:** `GET http://localhost:8080/health-check` → `API 3ESA OK!`

---

## 🗃️ Migrações (Flyway)
- **V1**: tabela `instrutores`
- **V2**: `instrutores.telefone`
- **V3**: `instrutores.ativo`
- **V4**: ajuste `ativo` para `BIT(1)`
- **V5**: tabela `alunos`
- **V6**: tabela `aulas`

> Se ocorrer *checksum mismatch*, você pode **dropar o DB** e subir novamente ou ajustar o `checksum` da versão na tabela `flyway_schema_history` para o valor “Resolved locally” do log.

---

## 🌳 Estrutura do projeto (`src/main/java`)
```text
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
```

---

## 🔌 API

**Base URL:** `http://localhost:8080`

### Health
- `GET /health-check` → *"API 3ESA OK!"*

### Instrutor
- `POST /instrutor` — cadastra
  ```json
  {
    "nome": "Joao Instrutor",
    "email": "joao@auto.com",
    "telefone": "11999990001",
    "cnh": "11111111111",
    "especialidade": "CARROS",
    "endereco": {
      "logradouro": "Rua 1",
      "numero": "10",
      "bairro": "Centro",
      "cidade": "SP",
      "uf": "SP",
      "cep": "01310930"
    }
  }
  ```
- `GET /instrutor?size=10&page=0&sort=nome` — lista **somente ativos** (paginado)
- `PUT /instrutor` — atualiza **nome/telefone/endereco** (não altera email/CNH/especialidade)
  ```json
  { "id": 1, "nome": "Joao A.", "telefone": "11988887777" }
  ```
- `DELETE /instrutor/{id}` — **exclusão lógica** (`ativo=false`)

### Aluno
- `POST /aluno` — cadastra (valida `cpf` com 11 dígitos)
  ```json
  {
    "nome": "Ana Aluna",
    "email": "ana@aluno.com",
    "telefone": "11977770001",
    "cpf": "12345678901",
    "endereco": {
      "logradouro": "Av A",
      "numero": "100",
      "bairro": "Centro",
      "cidade": "SP",
      "uf": "SP",
      "cep": "01310930"
    }
  }
  ```
- `GET /aluno?size=10&page=0&sort=nome` — lista **somente ativos** (paginado)
- `PUT /aluno` — atualiza **nome/telefone/endereco** (não altera email/CPF)
- `DELETE /aluno/{id}` — **exclusão lógica**

### Aulas (agendamento/cancelamento)
- `POST /aulas` — agenda aula
  ```json
  {
    "alunoId": 1,
    "instrutorId": null,
    "dataHoraInicio": "2025-09-15T10:00:00"
  }
  ```
  **Regras:**
  - Seg–Sáb, **06:00–21:00** (aula dura **1h** e deve **terminar até 21:00**)
  - **Antecedência mínima: 30 min**
  - **Máximo 2 aulas/dia** por aluno
  - Instrutor não pode estar ocupado no horário
  - Aluno/Instrutor devem estar **ativos**
  - `instrutorId` é **opcional**; o sistema escolhe um disponível aleatório
- `DELETE /aulas/{id}` — cancela com body:
  ```json
  { "motivo": "ALUNO_DESISTIU" }
  ```
  **Regra:** somente com **≥24h** de antecedência.

---

## 🧪 Postman (opcional)
Inclua no repositório uma pasta `postman/` com:
- `AutoEscola_CP1.postman_collection.json`
- `AutoEscola_Localhost.postman_environment.json`

Importe no Postman e execute na ordem: **Health → Instrutor → Aluno → Aulas**.

---

## ✅ Checklist de entrega
- Repositório **público** no GitHub
- `README.md` com **nomes dos integrantes** e instruções
- Link do repositório enviado no **Portal do Aluno** (apenas **um** integrante)
- App sobe e `GET /health-check` responde
- CRUD **Instrutor/Aluno** funcional
- Agendamento/Cancelamento com regras implementadas

---

## ℹ️ Observações
- Campo booleano `ativo` usa **`BIT(1)`** no MySQL (compatível com Hibernate 6).
- Em desenvolvimento, para reiniciar migrações: **drope o DB** e suba novamente.
