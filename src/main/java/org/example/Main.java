package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MenuPrincipal();
    }

    public static void MenuPrincipal(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n==== MENU BANCO =====");
        System.out.println("1- Cadastrar Cliente");
        System.out.println("2- Criar Conta");
        System.out.println("3- Exibir Saldo");
        System.out.println("4- Depositar");
        System.out.println("5- Sacar");
        System.out.println("6- Sair");
        System.out.print("\nEscolha uma opcao(1-6): ");
        int opcaoMenuPrincipal = scanner.nextInt();
        scanner.nextLine();
        switch (opcaoMenuPrincipal){
            case 1:
                CadastrarCliente();
            case 2:
                System.out.println("\n==== CRIAR CONTA =====");
                System.out.println("1- Conta Corrente");
                System.out.println("2- Conta Poupanca");
                System.out.print("\nEscolha uma opcao(1-2): ");

                try {
                    int opcaoTipoConta = scanner.nextInt();
                    if (opcaoTipoConta == 1) {
                        ContaCorrente ContaCorrente = new ContaCorrente();
                        ContaCorrente.CriarConta();
                    } else if (opcaoTipoConta == 2) {
                        ContaPoupanca ContaPoupanca = new ContaPoupanca();
                        ContaPoupanca.CriarConta();
                    } else {
                        System.out.println("Valor Invalido!");
                        MenuTentarNovamente();
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro de digitacao: "+e.getMessage());
                    MenuTentarNovamente();
                }
            case 3:
                System.out.println("\n==== EXIBIR SALDO =====");
                System.out.println("1- Conta Corrente");
                System.out.println("2- Conta Poupanca");
                System.out.print("\nEscolha uma opcao(1-2): ");

                try {
                    int opcaoExibirSaldo = scanner.nextInt();
                    if (opcaoExibirSaldo == 1) {
                        ContaCorrente ContaCorrente = new ContaCorrente();
                        ContaCorrente.ExibirSaldo();
                    } else if (opcaoExibirSaldo == 2) {
                        ContaPoupanca ContaPoupanca = new ContaPoupanca();
                        ContaPoupanca.ExibirSaldo();
                    } else {
                        System.out.println("Valor Invalido!");
                        MenuTentarNovamente();
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro de digitacao: "+e.getMessage());
                    MenuTentarNovamente();
                }
            case 4:
                System.out.println("\n==== DEPOSITAR =====");
                System.out.println("1- Conta Corrente");
                System.out.println("2- Conta Poupanca");
                System.out.print("\nEscolha uma opcao(1-2): ");

                try {
                    int opcaoDepositar = scanner.nextInt();
                    if (opcaoDepositar == 1) {
                        ContaCorrente ContaCorrente = new ContaCorrente();
                        ContaCorrente.Depositar();
                    } else if (opcaoDepositar == 2) {
                        ContaPoupanca ContaPoupanca = new ContaPoupanca();
                        ContaPoupanca.Depositar();
                    } else {
                        System.out.println("Valor Invalido!");
                        MenuTentarNovamente();
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro de digitacao: "+e.getMessage());
                    MenuTentarNovamente();
                }
            case 5:
                System.out.println("\n==== SACAR =====");
                System.out.println("1- Conta Corrente");
                System.out.println("2- Conta Poupanca");
                System.out.print("\nEscolha uma opcao(1-2): ");

                try {
                    int opcaoSacar = scanner.nextInt();
                    if (opcaoSacar == 1) {
                        ContaCorrente ContaCorrente = new ContaCorrente();
                        ContaCorrente.Sacar();
                    } else if (opcaoSacar == 2) {
                        ContaPoupanca ContaPoupanca = new ContaPoupanca();
                        ContaPoupanca.Sacar();
                    } else {
                        System.out.println("Valor Invalido!");
                        MenuTentarNovamente();
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro de digitacao: "+e.getMessage());
                    MenuTentarNovamente();
                }
            case 6:
                System.out.print("Tem certeza que deseja sair do Sistema? \nDigite 'S' para confirmar ou 'N' para cancelar: ");
                String confirmacao = scanner.nextLine().trim();

                if(confirmacao.equals("s") || confirmacao.equals("S")){
                    System.exit(0);
                }else if (confirmacao.equals("n") || confirmacao.equals("N")){
                    MenuPrincipal();
                }
        }
    }

    public static void CadastrarCliente(){
        Scanner scanner = new Scanner(System.in);
        String nomeCliente = null;
        String cpfCliente = null;
        String telefoneCliente = null;
        String enderecoCliente = null;
        String sql = null;

        System.out.println("=================");
        System.out.println("CADASTRAR CLIENTE");
        System.out.println("=================");
        try{
            System.out.print("Nome cliente: ");
            nomeCliente = scanner.nextLine().toLowerCase(Locale.ROOT);
            System.out.print("CPF cliente: ");
            cpfCliente = scanner.nextLine().toLowerCase(Locale.ROOT);
            System.out.print("Telefone cliente: ");
            telefoneCliente = scanner.nextLine().toLowerCase(Locale.ROOT);
            System.out.print("Endereco cliente: ");
            enderecoCliente = scanner.nextLine().toLowerCase(Locale.ROOT);

            /// validacao de dados
            if(nomeCliente.matches(".*\\d.*")){ // matches verificando se tem numero
                System.out.println("\nNome Invalido! Digite Apenas Letras.");
                MenuTentarNovamente();
            }
            if(cpfCliente.matches("\\d+")){ // matches verificando se tem letras
                System.out.println("\nCPF Invalido! Digite Apenas Numeros.");
                MenuTentarNovamente();
            }
            if(telefoneCliente.matches("\\d+")){ // matches verificando se tem letras
                System.out.println("\nTelefone Invalido! Digite Apenas Numeros.");
                MenuTentarNovamente();
            }
        }catch(InputMismatchException e){
            System.out.print("Valor invalido!");
            scanner.nextLine();
            scanner.close();
            MenuTentarNovamente();
        }

        // gerando conexao
        ConexaoBD conectar = new ConexaoBD();
        Connection conexao = conectar.conectar();

        if(conexao!=null) {
            sql = "INSERT INTO cliente(nome, cpf, telefone, endereco) VALUES(? ,?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)){
                stmt.setString(1, nomeCliente);
                stmt.setString(2, cpfCliente);
                stmt.setString(3, telefoneCliente);
                stmt.setString(4, enderecoCliente);

                // executando o update
                stmt.executeUpdate();

            }catch(SQLException e){
                System.out.println("Erro ao executar SQL: "+ e.getMessage());
                MenuTentarNovamente();
            }finally {
                try {
                    conexao.close();
                    System.out.println("Cliente Cadastrado com Sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar Conexao: " + e.getMessage());
                    MenuTentarNovamente();
                }
                /////// criar pergunta se ja gostaria de criar uma conta, se sim pergunta qual tipo e chama funcao de criacao de conta
            }
        }else{
            System.out.println("Erro ao Conectar ao Banco de Dados SQL!");
            MenuTentarNovamente();
        }
    }



    public static void MenuTentarNovamente(){
        Scanner scanner = new Scanner(System.in);


        System.out.println("\n1 - voltar ao menu principal");
        System.out.print("Digite: ");
        int opcaoTentarNovamente = scanner.nextInt();
        if(opcaoTentarNovamente==1){
            MenuPrincipal();
        } else{
            System.out.println("Opcao Invalida!");
            MenuTentarNovamente();
        }
    }

}
