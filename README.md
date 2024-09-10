# Sistema de Gerenciamento de Transações (tgid)

Este projeto é um sistema de gerenciamento de transações que permite a realização de depósitos e saques entre clientes e empresas. A aplicação valida o CPF dos clientes e o CNPJ das empresas antes de realizar operações de persistência no banco de dados.

## Funcionalidades

- Realizar depósitos e saques.
- Validação de CPF e CNPJ.
- Atualização de saldo da empresa.
- Registro de transações.
- Gerenciamento de clientes e empresas.

## Instalação
    
    git clone https://github.com/seu-usuario/sistema-gerenciamento-transacoes.gitl
    
    

## Tecnologias Utilizadas

* **Spring Boot:** 3.3.3
* **Java:** 17
* **Banco de dados:** PostgreSQL 
* **Framework ORM:** Hibernate
* **Build:** Maven
* **Documentação:** Swagger, Javadoc

### Endpoints

- **Buscar todas as transações**
    ```http
    GET /transacao
    ```
    Retorna uma lista de todas as transações registradas.

- **Realizar uma nova transação**
    ```http
    POST /transacao
    Content-Type: application/json

    {
        "cpf_cliente": "123.456.789-00",
        "cnpj_empresa": "12.345.678/0001-00",
        "valor": 100.00,
        "tipo": "deposito",
        "taxa": 2.50
    }
    ```

- **Buscar todos os clientes**
    ```http
    GET /cliente
    ```
    Retorna uma lista de todos os clientes cadastrados.

- **Adicionar um novo cliente**
    ```http
    POST /cliente
    Content-Type: application/json

    {
        "cpfCliente": "123.456.789-00",
        "email": "cliente@example.com"
    }
    ```

- **Buscar todas as empresas**
    ```http
    GET /empresa
    ```
    Retorna uma lista de todas as empresas cadastradas.

- **Adicionar uma nova empresa**
    ```http
    POST /empresa
    Content-Type: application/json

    {
        "cnpj": "12.345.678/0001-00",
        "saldo": 1000.00,
        "taxaAdministracao": 5.00
    }
    ```

### Conexão com Banco de Dados e JPA
Esta aplicação utiliza o Spring Data JPA para interagir com o banco de dados. A configuração necessária para a conexão com o banco de dados está no arquivo application.properties.

```properties
//Configuração do application.properties:
spring.jpa.show-sql =  Define se as consultas SQL geradas pelo Hibernate devem ser exibidas no console.
spring.jpa.hibernate.ddl-auto = Define a estratégia de geração de esquema do banco de dados. O valor update atualiza o esquema do banco de dados conforme as mudanças nas entidades.
spring.jpa.database-platform = Especifica o dialeto do banco de dados a ser usado pelo Hibernate.
spring.datasource.driver-class-name = Define a classe do driver JDBC a ser usada.
spring.datasource.url = URL de conexão com o banco de dados.
spring.datasource.username = Nome de usuário para a conexão com o banco de dados.
spring.datasource.password = Senha para a conexão com o banco de dados.
```


