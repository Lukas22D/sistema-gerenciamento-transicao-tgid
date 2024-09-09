# Sistema de Gerenciamento de Transações (tgid)

Este projeto é um sistema de gerenciamento de transações que permite a realização de depósitos e saques entre clientes e empresas. A aplicação valida o CPF dos clientes e o CNPJ das empresas antes de realizar operações de persistência no banco de dados.

## Funcionalidades

- Realizar depósitos e saques.
- Validação de CPF e CNPJ.
- Atualização de saldo da empresa.
- Registro de transações.
- Gerenciamento de clientes e empresas.

## Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/sistema-gerenciamento-transacoes.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd sistema-gerenciamento-transacoes
    ```
3. Instale as dependências:
    ```bash
    mvn install
    ```

## Uso

1. Inicie a aplicação:
    ```bash
    mvn spring-boot:run
    ```
2. Utilize os seguintes endpoints para interagir com a API:

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


