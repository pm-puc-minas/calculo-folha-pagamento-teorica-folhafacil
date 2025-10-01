package com.engsoft.folha_facil.model;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoRH {

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

    // Métodos do diagrama (stubs)
    public void controlarBeneficios() {
        // TODO: lógica de controle
    }

    public void controlarFolhaPagamento() {
        // TODO: lógica de controle
    }

    public String gerarRelatorioConsolidado() {
        // TODO: gerar relatório
        return "";
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        // TODO: cadastrar
    }

    public void alterarFuncionario(Funcionario funcionario) {
        // TODO: alterar
    }

    public void excluirFuncionario(Funcionario funcionario) {
        // TODO: excluir
    }

    public void cadastrarBeneficio(Beneficio beneficio) {
        // TODO: cadastrar
    }

    public void alterarBeneficio(Beneficio beneficio) {
        // TODO: alterar
    }

    public void excluirBeneficio(Beneficio beneficio) {
        // TODO: excluir
    }

    public void revisarFolhaPagamento(FolhaPagamento folha) {
        // TODO: revisar
    }

    public void aprovarFolhaPagamento(FolhaPagamento folha) {
        // TODO: aprovar
    }

    public boolean validarConformidadeLegal() {
        // TODO: validações legais
        return true;
    }

    // Getters e Setters
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }
    public List<Beneficio> getBeneficios() { return beneficios; }
    public void setBeneficios(List<Beneficio> beneficios) { this.beneficios = beneficios; }
    public FolhaPagamento getFolhaPagamento() { return folhaPagamento; }
    public void setFolhaPagamento(FolhaPagamento folhaPagamento) { this.folhaPagamento = folhaPagamento; }
    public List<Imposto> getImpostos() { return impostos; }
    public void setImpostos(List<Imposto> impostos) { this.impostos = impostos; }
}