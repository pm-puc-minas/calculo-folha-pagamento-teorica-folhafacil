package com.engsoft.folha_facil.model;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoRH {

    private Long id;

    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<Beneficio> beneficios = new ArrayList<>();
    private FolhaPagamento folhaPagamento;
    private List<Imposto> impostos = new ArrayList<>();

    public DepartamentoRH() {}

    public DepartamentoRH(List<Funcionario> funcionarios, List<Beneficio> beneficios,
                          FolhaPagamento folhaPagamento, List<Imposto> impostos) {
        if (funcionarios != null) this.funcionarios = funcionarios;
        if (beneficios != null) this.beneficios = beneficios;
        this.folhaPagamento = folhaPagamento;
        if (impostos != null) this.impostos = impostos;
    }
    // Getters e Setters
    
    public Long getId() { return id; }

    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }
    public List<Beneficio> getBeneficios() { return beneficios; }
    public void setBeneficios(List<Beneficio> beneficios) { this.beneficios = beneficios; }
    public FolhaPagamento getFolhaPagamento() { return folhaPagamento; }
    public void setFolhaPagamento(FolhaPagamento folhaPagamento) { this.folhaPagamento = folhaPagamento; }
    public List<Imposto> getImpostos() { return impostos; }
    public void setImpostos(List<Imposto> impostos) { this.impostos = impostos; }
}