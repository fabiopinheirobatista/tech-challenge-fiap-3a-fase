# Tech-Challenge - FIAP - 3º fase

## Pré-requisitos

- Java 17
- Maven
- Docker
- Docker Compose

## Configuração

1. Clone o repositório:
    ```sh
    git clone https://github.com/fabiopinheirobatista/tech-challenge-fiap-3a-fase.git
    cd techchallenge
    ```
2. Construa e inicie os containers Docker:
    ```sh
   docker-compose up -d
    ```
   OU
   ```sh
    docker-compose up --build 
    ```

## Execução

1. Para executar a aplicação, use o comando:
    ```sh
    mvn spring-boot:run
    ```

2. A aplicação estará disponível localmente na porta `9090`

## Portas

- **API local**: `9090`
- **Banco de Dados (MySQL)**: `3307`

## Acesso ao BD

1. Pode ser utilizado o DBeaver ou qualquer outra ferramenta de sua preferência.
2. Utilize as credenciais localizadas no arquivo application.properties

## Endpoints

Aqui estão todos os endpoints listados no Postman, organizados por categoria:

**Enfermeiros:**

* `POST http://localhost:9090/api/enfermeiro/cadastrar` (Salvar enfermeiro)
* `GET http://localhost:9090/api/enfermeiro/buscar-todos` (Pesquisar todos os enfermeiros)
* `PUT http://localhost:9090/api/enfermeiro/atualizar/{id}` (Atualizar enfermeiro) - Exemplo: `http://localhost:9090/api/enfermeiro/atualizar/3`
* `GET http://localhost:9090/api/enfermeiro/listar/{id}` (Pesquisar enfermeiro - ID específico) - Exemplo: `http://localhost:9090/api/enfermeiro/listar/1`
* `DELETE http://localhost:9090/api/enfermeiro/deletar/{id}` (Excluir enfermeiro - ID específico) - Exemplo: `http://localhost:9090/api/enfermeiro/deletar/4`

**Médicos:**

* `POST http://localhost:9090/api/enfermeiro/cadastrar` (Salvar médico)
* `GET http://localhost:9090/api/enfermeiro/buscar-todos` (Pesquisar todos os médicos)
* `PUT http://localhost:9090/api/enfermeiro/atualizar/{id}` (Atualizar médico) - Exemplo: `http://localhost:9090/api/medico/atualizar/3`
* `GET http://localhost:9090/api/enfermeiro/listar/{id}` (Pesquisar médico - ID específico) - Exemplo: `http://localhost:9090/api/medico/listar/1`
* `DELETE http://localhost:9090/api/enfermeiro/deletar/{id}` (Excluir médico - ID específico) - Exemplo: `http://localhost:9090/api/medico/deletar/4`

**Pacientes:**

* `POST http://localhost:9090/api/enfermeiro/cadastrar` (Salvar pacientes)
* `GET http://localhost:9090/api/enfermeiro/buscar-todos` (Pesquisar todos os pacientes)
* `PUT http://localhost:9090/api/enfermeiro/atualizar/{id}` (Atualizar paciente) - Exemplo: `http://localhost:9090/api/paciente/atualizar/3`
* `GET http://localhost:9090/api/enfermeiro/listar/{id}` (Pesquisar paciente - ID específico) - Exemplo: `http://localhost:9090/api/paciente/listar/1`
* `DELETE http://localhost:9090/api/enfermeiro/deletar/{id}` (Excluir paciente - ID específico) - Exemplo: `http://localhost:9090/api/paciente/deletar/4`

## Tecnologias utilizadas

- Java
- Spring Boot
- Maven
- Docker
- MySQL