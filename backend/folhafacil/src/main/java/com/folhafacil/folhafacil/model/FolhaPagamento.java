package com.folhafacil.folhafacil.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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
}
