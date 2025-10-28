package com.folhafacil.folhafacil.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
}
