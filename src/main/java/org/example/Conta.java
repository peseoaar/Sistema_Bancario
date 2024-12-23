package org.example;

public abstract class Conta {
    private Cliente cliente;
    private static int numeroDaConta;
    private Double saldo;

    public enum TipoConta{
        CORRENTE,
        POUPANCA
    }
    public static enum Status{
        ATIVA,
        INATIVA,
        ENCERRADA
    }
    private TipoConta tipoConta;
    private static Status status;


    // ENCAPSULAMENTO
    public Cliente getCliente(){return cliente;}

    public int getNumeroDaConta(){return numeroDaConta;}

    public void setNumeroDaConta(int numeroDaConta){
        Conta.numeroDaConta = numeroDaConta;
    }

    public Double getSaldo(){return saldo;}

    public void setSaldo(Double saldo){
        this.saldo = saldo;
    }

    public TipoConta getTipoConta(){return tipoConta;}

    public void setTipoConta(TipoConta tipoConta){
        this.tipoConta = tipoConta;
    }
    public static Status getStatus(){return status;}

    public void setStatus(Status status){
        Conta.status = status;
    }




    // METODOS
    public abstract void CriarConta();

    public abstract void Depositar();

    public abstract void Sacar();

    public abstract void ExibirSaldo();

}


