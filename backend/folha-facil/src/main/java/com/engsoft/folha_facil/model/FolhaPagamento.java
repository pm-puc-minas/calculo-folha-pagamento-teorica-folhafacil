package com.engsoft.folha_facil.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FolhaPagamento {

    private Long id;

    private Funcionario funcionario;
    private double salarioLiquido;
    private Imposto imposto;
    private List<FolhaPagamento> historicoPagamentos = new ArrayList<>();
    private double salarioBruto;
    private int diasFaltados;
    private int horaExtra;
    private List<Beneficio> beneficios = new ArrayList<>();
    private Date dataPagamento;

    public FolhaPagamento() {}

    public FolhaPagamento(Funcionario funcionario, double salarioLiquido, Imposto imposto,
                          List<FolhaPagamento> historicoPagamentos, double salarioBruto, int diasFaltados,
                          int horaExtra, List<Beneficio> beneficios, Date dataPagamento) {
        this.funcionario = funcionario;
        this.salarioLiquido = salarioLiquido;
        this.imposto = imposto;
        if (historicoPagamentos != null) this.historicoPagamentos = historicoPagamentos;
        this.salarioBruto = salarioBruto;
        this.diasFaltados = diasFaltados;
        this.horaExtra = horaExtra;
        if (beneficios != null) this.beneficios = beneficios;
        this.dataPagamento = dataPagamento;
    }

    public List<FolhaPagamento> consultarHistorico() {
        return new ArrayList<>(historicoPagamentos);
    }

    public Long getId() { return id; }

    // Getters e Setters
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
    public double getSalarioLiquido() { return salarioLiquido; }
    public void setSalarioLiquido(double salarioLiquido) { this.salarioLiquido = salarioLiquido; }
    public Imposto getImposto() { return imposto; }
    public void setImposto(Imposto imposto) { this.imposto = imposto; }
    public List<FolhaPagamento> getHistoricoPagamentos() { return historicoPagamentos; }
    public void setHistoricoPagamentos(List<FolhaPagamento> historicoPagamentos) { this.historicoPagamentos = historicoPagamentos; }
    public double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(double salarioBruto) { this.salarioBruto = salarioBruto; }
    public int getDiasFaltados() { return diasFaltados; }
    public void setDiasFaltados(int diasFaltados) { this.diasFaltados = diasFaltados; }
    public int getHoraExtra() { return horaExtra; }
    public void setHoraExtra(int horaExtra) { this.horaExtra = horaExtra; }
    public List<Beneficio> getBeneficios() { return beneficios; }
    public void setBeneficios(List<Beneficio> beneficios) { this.beneficios = beneficios; }
    public Date getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(Date dataPagamento) { this.dataPagamento = dataPagamento; }
}