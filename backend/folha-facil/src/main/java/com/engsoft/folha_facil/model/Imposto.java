package com.engsoft.folha_facil.model;

public class Imposto {

    private double INSS;
    private double FGTS;
    private double IRRF;
    private double descontoTotal;

    public Imposto() {}

    public Imposto(double INSS, double FGTS, double IRRF, double descontoTotal) {
        this.INSS = INSS;
        this.FGTS = FGTS;
        this.IRRF = IRRF;
        this.descontoTotal = descontoTotal;
    }

    // Métodos do diagrama (stubs)
    public void calcularINSS(Funcionario funcionario) {
        // TODO: calcular INSS
    }

    public void calcularFGTS(Funcionario funcionario) {
        // TODO: calcular FGTS
    }

    public void calcularIRRF(Funcionario funcionario) {
        // TODO: calcular IRRF
    }

    public void calcularDescontoTotal() {
        // TODO: somar descontos
    }

    // Getters e Setters
    public double getINSS() { return INSS; }
    public void setINSS(double INSS) { this.INSS = INSS; }
    public double getFGTS() { return FGTS; }
    public void setFGTS(double FGTS) { this.FGTS = FGTS; }
    public double getIRRF() { return IRRF; }
    public void setIRRF(double IRRF) { this.IRRF = IRRF; }
    public double getDescontoTotal() { return descontoTotal; }
    public void setDescontoTotal(double descontoTotal) { this.descontoTotal = descontoTotal; }
}