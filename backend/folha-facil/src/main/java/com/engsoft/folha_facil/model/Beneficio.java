package com.engsoft.folha_facil.model;

public class Beneficio {

    private double valeTransporte;
    private double valeAlimentacao;
    private double periculosidade;
    private int grausInsalubridade;
    private double insalubridade;
    private double desconto;
    private float valorDiarioVA;

    public Beneficio() {}

    public Beneficio(double valeTransporte, double valeAlimentacao, double periculosidade, int grausInsalubridade,
                     double insalubridade, double desconto, float valorDiarioVA) {
        this.valeTransporte = valeTransporte;
        this.valeAlimentacao = valeAlimentacao;
        this.periculosidade = periculosidade;
        this.grausInsalubridade = grausInsalubridade;
        this.insalubridade = insalubridade;
        this.desconto = desconto;
        this.valorDiarioVA = valorDiarioVA;
    }

    // Métodos do diagrama (stubs)
    public double calcularPericulosidade(Funcionario funcionario) {
        // TODO: calcular periculosidade
        return 0.0;
    }

    public double calcularInsalubridade(int grausInsalubridade, Funcionario funcionario) {
        // TODO: calcular insalubridade
        return 0.0;
    }

    public float descontoBeneficios() {
        // TODO: calcular desconto total de benefícios
        return 0f;
    }

    public double calcularValeTransporte(Funcionario funcionario) {
        // TODO: calcular vale transporte
        return 0.0;
    }

    public double calcularValeAlimentacao(Funcionario funcionario) {
        // TODO: calcular vale alimentação
        return 0.0;
    }

    // Getters e Setters
    public double getValeTransporte() { return valeTransporte; }
    public void setValeTransporte(double valeTransporte) { this.valeTransporte = valeTransporte; }
    public double getValeAlimentacao() { return valeAlimentacao; }
    public void setValeAlimentacao(double valeAlimentacao) { this.valeAlimentacao = valeAlimentacao; }
    public double getPericulosidade() { return periculosidade; }
    public void setPericulosidade(double periculosidade) { this.periculosidade = periculosidade; }
    public int getGrausInsalubridade() { return grausInsalubridade; }
    public void setGrausInsalubridade(int grausInsalubridade) { this.grausInsalubridade = grausInsalubridade; }
    public double getInsalubridade() { return insalubridade; }
    public void setInsalubridade(double insalubridade) { this.insalubridade = insalubridade; }
    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }
    public float getValorDiarioVA() { return valorDiarioVA; }
    public void setValorDiarioVA(float valorDiarioVA) { this.valorDiarioVA = valorDiarioVA; }
}