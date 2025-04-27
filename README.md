# Tech Challenge Fase 3 - Sistema Hospitalar (Kafka)

Este projeto implementa um sistema hospitalar simplificado como parte do Tech Challenge Fase 3, utilizando uma arquitetura de microserviços com comunicação assíncrona via Apache Kafka.

## Arquitetura

O sistema é composto pelos seguintes microserviços:

1.  **Agendamento Service (`agendamento-service`):**
    *   Responsável pelo agendamento, atualização e cancelamento de consultas.
    *   Expõe uma API REST para gerenciar consultas, pacientes, médicos e enfermeiros (operações básicas de CRUD podem ser adicionadas se necessário).
    *   Persiste os dados em um banco de dados MySQL (`agendamento_db`).
    *   **Produz** mensagens no tópico Kafka `consultas.topic` sempre que uma consulta é criada ou atualizada, enviando um `ConsultaNotificacaoDTO`.
    *   Porta: `8081`

2.  **Notificação Service (`notificacao-service`):**
    *   Responsável por simular o envio de notificações aos pacientes sobre suas consultas.
    *   **Consome** mensagens do tópico Kafka `consultas.topic`.
    *   Ao receber uma mensagem, processa o `ConsultaNotificacaoDTO` e simula o envio de uma notificação (atualmente, apenas loga a ação).
    *   Não possui persistência própria neste exemplo.
    *   Porta: `8082`

3.  **Histórico Service (`historico-service`):**
    *   Responsável por fornecer um histórico de consultas e dados relacionados.
    *   Expõe uma API GraphQL para consultar informações de pacientes, médicos, enfermeiros e consultas.
    *   Persiste os dados em um banco de dados MySQL (`historico_db`). Assume-se que os dados são sincronizados ou populados de forma independente para fins de consulta histórica (neste exemplo, as tabelas são as mesmas do agendamento para simplificar).
    *   Porta: `8083`
    *   Endpoint GraphQL: `/graphql`
    *   Interface GraphiQL (para testes): `/graphiql`

## Tecnologias Utilizadas

*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3.2.5
*   **Persistência:** Spring Data JPA, Hibernate
*   **Banco de Dados:** MySQL 8.0
*   **Comunicação Assíncrona:** Apache Kafka (via Spring Kafka)
*   **API Histórico:** GraphQL (via Spring for GraphQL)
*   **API Agendamento:** REST (via Spring Web)
*   **Build:** Maven
*   **Testes:** JUnit 5, Mockito, Spring Boot Test, Spring Kafka Test, GraphQL Test
*   **Cobertura de Testes:** JaCoCo
*   **Containerização:** Docker, Docker Compose
*   **Outros:** Lombok, SLF4j (Logging)


## Estrutura do Projeto

```
tech-challenge-fase3-kafka/
├── agendamento-service/      # Microserviço de Agendamento
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── historico-service/        # Microserviço de Histórico
│   ├── src/
│   │   └── main/
│   │       └── resources/
│   │           └── graphql/
│   │               └── schema.graphqls  # Schema GraphQL
│   ├── pom.xml
│   └── Dockerfile
├── notificacao-service/      # Microserviço de Notificação
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml        # Orquestração dos containers

```
