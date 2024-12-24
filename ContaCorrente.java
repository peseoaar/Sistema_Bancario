package org.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;



public class ContaCorrente extends Conta {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void CriarConta(){
        String idCliente = BuscarIdCliente();
        String sql = null;
        String numeroConta = null;
        int erroSQL = 0;


        try{
            System.out.println("====================");
            System.out.println("CRIAR CONTA CORRENTE");
            System.out.println("====================");
            System.out.print("Numero da conta (11char): ");
            numeroConta = scanner.nextLine();

            /// validacao de dados
            if(numeroConta.matches(".*[a-zA-Z].*")){ // matches verificando se tem letras
                System.out.println("\nValor Invalido! Digite Apenas Numeros.");
                Main.MenuTentarNovamente();
            }

        }catch(InputMismatchException e){
            System.out.print("Valor invalido!");
            scanner.nextLine();
            scanner.close();
            Main.MenuTentarNovamente();
        }

        // gerando conexao
        ConexaoBD conectar = new ConexaoBD();
        Connection conexao = conectar.conectar();

        if(conexao!=null) {
            sql = "INSERT INTO conta(id_cliente, numero_conta, tipo_conta, status) VALUES(? ,?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)){
                stmt.setString(1, idCliente);
                stmt.setString(2, numeroConta);
                stmt.setString(3, TipoConta.CORRENTE.name()); // name esta convertendo enum para String
                stmt.setString(4, Conta.Status.ATIVA.name());

                // executando o update
                stmt.executeUpdate();

            }catch(SQLException e){
                System.out.println("Erro ao executar SQL: "+ e.getMessage());
                Main.MenuTentarNovamente();
            }finally {
                try {
                    conexao.close();
                    System.out.println("Conta Corrente Cadastrada com Sucesso!");
                    Main.MenuTentarNovamente();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar Conexao: " + e.getMessage());
                    Main.MenuTentarNovamente();
                }
            }
        }else{
            System.out.println("Erro ao Conectar ao Banco de Dados SQL!");
            Main.MenuTentarNovamente();
        }


    }

    @Override
    public void Depositar() {
        String idCliente = BuscarIdCliente();
        double valorDeposito = 0.0;
        String confirmacao = null;
        String sql = null;
        int countConfirmacao = 0;
        double valorFinal=0;
        try {
            System.out.println("==========");
            System.out.println("DEPOSITAR");
            System.out.println("==========");
            System.out.print("Valor do Deposito: R$");
            valorDeposito = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Deposito no valor de R$"+valorDeposito+". Tem certeza que deseja continuar?"+
                    "\nDigite 'S' para confirmar ou 'N' para cancelar: ");
            confirmacao = scanner.nextLine().trim();

            if(confirmacao.equals("s") || confirmacao.equals("S")){
                System.out.println("Valor Confirmado!");
                countConfirmacao = countConfirmacao + 1;
            }else if (confirmacao.equals("n") || confirmacao.equals("N")){
                Depositar();
            }
        }catch(InputMismatchException e){
            System.out.println("Erro no deposito: "+e.getMessage());
        }
        if(countConfirmacao==1){
            // gerando conexao
            ConexaoBD conectar = new ConexaoBD();
            Connection conexao = conectar.conectar();

            if(conexao!=null) {
                // pegando saldo em conta
                sql = "SELECT saldo FROM conta WHERE id_cliente=?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setString(1, String.valueOf(idCliente));

                    // pegando valor em conta e transformando a string em double
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        double valorEmContaDouble = rs.getDouble("saldo");
                        valorFinal = valorEmContaDouble + valorDeposito;
                    }

                } catch (SQLException e) {
                    System.out.println("Erro ao executar SQL: " + e.getMessage());
                    Main.MenuTentarNovamente();
                }

                // jogando valor final na conta
                sql = "UPDATE conta SET saldo=? WHERE id_cliente=?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setString(1, String.valueOf(valorFinal));
                    stmt.setString(2, idCliente);

                    // executando o update
                    stmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Erro ao executar SQL: " + e.getMessage());
                    Main.MenuTentarNovamente();
                } finally {
                    try {
                        conexao.close();
                        System.out.println("Deposito Realizado com Sucesso!");
                        Main.MenuTentarNovamente();
                    } catch (SQLException e) {
                        System.out.println("Erro ao fechar Conexao: " + e.getMessage());
                        Main.MenuTentarNovamente();
                    }
                }
            }else{
                System.out.println("Valor nao Confirmado!");
                Depositar();
            }
        }else{
            System.out.println("Erro ao Conectar ao Banco de Dados SQL!");
            Main.MenuTentarNovamente();
        }

    }

    @Override
    public void Sacar() {
        String idCliente = BuscarIdCliente();
        double valorSaque = 0.0;
        String confirmacao = null;
        String sql = null;
        int countConfirmacao = 0;
        double valorFinal=0;
        try {
            System.out.println("==========");
            System.out.println("  SAQUE");
            System.out.println("==========");
            System.out.print("Valor do Saque: R$");
            valorSaque = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("\nSaque no valor de R$"+valorSaque+". Tem certeza que deseja continuar?"+
                    "\nDigite 'S' para confirmar ou 'N' para cancelar: ");
            confirmacao = scanner.nextLine().trim();

            if(confirmacao.equals("s") || confirmacao.equals("S")){
                System.out.println("Valor Confirmado!");
                countConfirmacao = countConfirmacao + 1;
            }else if (confirmacao.equals("n") || confirmacao.equals("N")){
                Sacar();
            }
        }catch(InputMismatchException e){
            System.out.println("Erro no saque: "+e.getMessage());
        }
        if(countConfirmacao==1){
            // gerando conexao
            ConexaoBD conectar = new ConexaoBD();
            Connection conexao = conectar.conectar();

            if(conexao!=null) {
                // pegando saldo em conta
                sql = "SELECT saldo FROM conta WHERE id_cliente=?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setString(1, String.valueOf(idCliente));

                    // pegando valor em conta e transformando a string em double
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        double valorEmContaDouble = rs.getDouble("saldo");
                        // verificando se tem saldo suficiente
                        if(valorEmContaDouble>=valorSaque){
                            valorFinal = valorEmContaDouble - valorSaque;
                        } else{
                            System.out.println("\nValor em conta R$"+valorEmContaDouble+", insuficiente para este saque!");
                            Main.MenuTentarNovamente();
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("Erro ao executar SQL: " + e.getMessage());
                    Main.MenuTentarNovamente();
                }

                // jogando valor final na conta
                sql = "UPDATE conta SET saldo=? WHERE id_cliente=?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setString(1, String.valueOf(valorFinal));
                    stmt.setString(2, idCliente);

                    // executando o update
                    stmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println("Erro ao executar SQL: " + e.getMessage());
                    Main.MenuTentarNovamente();
                } finally {
                    try {
                        conexao.close();
                        System.out.println("Saque Realizado com Sucesso!");
                        Main.MenuTentarNovamente();
                    } catch (SQLException e) {
                        System.out.println("Erro ao fechar Conexao: " + e.getMessage());
                        Main.MenuTentarNovamente();
                    }
                }
            }else{
                System.out.println("Valor nao Confirmado!");
                Depositar();
            }
        }else{
            System.out.println("Erro ao Conectar ao Banco de Dados SQL!");
            Main.MenuTentarNovamente();
        }

    }

    @Override
    public void ExibirSaldo() {
        String idCliente = BuscarIdCliente();
        String sql = null;


        System.out.println("===============");
        System.out.println("  EXIBIR SALDO");
        System.out.println("===============");

        // gerando conexao
        ConexaoBD conectar = new ConexaoBD();
        Connection conexao = conectar.conectar();

        if (conexao != null) {
            // pegando saldo em conta
            sql = "SELECT saldo FROM conta WHERE id_cliente=?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, String.valueOf(idCliente));

                // pegando valor em conta e transformando a string em double
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double valorEmContaDouble = rs.getDouble("saldo");
                    System.out.println("Saldo em conta: R$"+valorEmContaDouble);
                    Main.MenuTentarNovamente();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao executar SQL: " + e.getMessage());
                Main.MenuTentarNovamente();
            }
        }
    }


    public static String BuscarIdCliente(){
        Scanner scanner = new Scanner(System.in);
        String cpfCliente = null;
        String sql = null;

        try{
            System.out.print("CPF do Cliente: ");
            cpfCliente = scanner.nextLine();

            if(!cpfCliente.matches("^[0-9]+$")){ // matches verificando se tem letras
                System.out.println("\nCPF Invalido! Digite Apenas Numeros.");
                Main.MenuTentarNovamente();
            }
        }catch(InputMismatchException e){
            System.out.print("Valor invalido!");
            scanner.nextLine();
            scanner.close();
            Main.MenuTentarNovamente();
        }

        // gerando conexao
        ConexaoBD conectar = new ConexaoBD();
        Connection conexao = conectar.conectar();

        if(conexao!=null) {
            sql = "SELECT id_cliente FROM cliente WHERE cpf=?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)){
                stmt.setString(1, cpfCliente);

                ResultSet rs = stmt.executeQuery(); // criando objeto ResultSet com informacoes da consulta

                if(rs.next()){  // como a maioria das consultas retorna mais de um registro, ele esta verificando se tem mais de um resultado
                    return rs.getString("id_cliente");
                } else{
                    System.out.println("\nCliente nao encontrado!");
                    System.out.println("Verifique o CPF ou crie uma nova conta se \no cliente ainda nao tiver sido cadastrado!");
                    Main.MenuTentarNovamente();
                }
            }catch(SQLException e) {
                System.out.println("Erro ao executar SQL: " + e.getMessage());
                Main.MenuTentarNovamente();
            }
        }else{
            System.out.println("Erro ao Conectar ao Banco de Dados SQL!");
            Main.MenuTentarNovamente();
        }
        return "";
    }


}
