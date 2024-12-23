# Sistema Bancário

Este projeto implementa um sistema bancário simples em Java, permitindo a criação e gerenciamento de contas correntes e poupança.
Os recursos incluem:

- Criação de contas (corrente ou poupança);
- Depósitos;
- Saques;
- Exibição de saldo.

## Estrutura do Projeto

### Principais Classes

1. **Conta**:
   - Classe base para contas bancárias.
   - Contém métodos abstratos implementados por classes filhas.

2. **ContaCorrente**:
   - Implementa as operações para contas correntes.

3. **ContaPoupanca**:
   - Implementa as operações para contas poupança.

4. **ConexaoBD**:
   - Responsável por gerenciar conexões com o banco de dados.

5. **Main**:
   - Gerencia o menu principal e a interação com o usuário.

6. **Enums**:
   - **TipoConta**: Enumeração para distinguir tipos de conta (CORRENTE, POUPANCA).
   - **Status**: Enumeração para status da conta (ATIVA, INATIVA).

## Funcionalidades

### 1. Criação de Conta
- Solicita ao usuário o CPF e o tipo de conta a ser criada.
- Salva as informações no banco de dados.

### 2. Depósito
- Solicita o CPF e o valor a ser depositado.
- Atualiza o saldo da conta correspondente no banco de dados.

### 3. Saque
- Solicita o CPF e o valor a ser sacado.
- Verifica se há saldo suficiente antes de atualizar a conta no banco de dados.

### 4. Exibição de Saldo
- Solicita o CPF e exibe o saldo atual da conta.

## Como Executar

### Requisitos
- Java Development Kit (JDK) 8 ou superior.
- Banco de dados relacional (ex.: MySQL).

### Configuração do Banco de Dados
1. Crie um banco de dados chamado `sistema_bancario`.
2. Execute o script SQL abaixo para criar as tabelas necessárias:

```sql
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE
);

CREATE TABLE conta (
    id_conta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    numero_conta VARCHAR(11) NOT NULL UNIQUE,
    tipo_conta VARCHAR(20) NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0.00,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);
```

3. Configure as credenciais do banco de dados no arquivo `ConexaoBD.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_bancario";
private static final String USUARIO = "seu_usuario";
private static final String SENHA = "sua_senha";
```

### Execução do Projeto
1. Compile o projeto:
   ```bash
   javac Main.java
   ```
2. Execute o projeto:
   ```bash
   java Main
   ```

## Estrutura do Menu
- **1. Criar Conta**
  - Escolha entre conta corrente ou poupança.
- **2. Depositar**
  - Informe o CPF e o valor a ser depositado.
- **3. Sacar**
  - Informe o CPF e o valor a ser sacado.
- **4. Exibir Saldo**
  - Informe o CPF para visualizar o saldo.
- **5. Sair**

## Melhorias Futuras
- Implementar autenticação de usuários.
- Adicionar transferência entre contas.
- Criar uma interface gráfica.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou relatar problemas.

## Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo `LICENSE` para mais informações.


