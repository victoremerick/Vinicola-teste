# API de Compras

## Descrição

Esta é uma API RESTful para gerir dados de produtos, compras e clientes. Atualmente, o estoque é responsável por armazenar dois tipos de bebidas (alcoólicas e não alcoólicas), mas está preparado para suportar a adição de novos tipos no futuro. A API permite o cadastro, consulta e gerenciamento de bebidas e seções de armazenamento, bem como o histórico de entradas e saídas de estoque.

## Motivação

A motivação para criar esta API veio da necessidade de um sistema robusto e flexível para visualizar as informações de compra de clientes.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Lombok
- Docker
- Swagger

## Estrutura do Projeto

### Camadas

- **Controller:** Camada responsável por expor os endpoints da API.
- **Service:** Camada responsável por implementar as regras de negócio.
- **Model:** Classes que representam as entidades e os dados manipulados pela aplicação.

### Tratamento de Exceções

Foi implementado um `ControllerAdvice` para tratamento centralizado de erros, garantindo respostas consistentes e informativas.
