package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;
import com.engsoft.folha_facil.model.Funcionario;

public class BeneficioService {

    private final FuncionarioService funcionarioService;

    public BeneficioService(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }


    public Beneficio adicionarBenefício(Funcionario funcionario, BeneficioTipo tipo, double valor, double desconto){
        if (funcionario == null) throw new IllegalArgumentException("Funcionario nulo");
        Beneficio beneficio = new Beneficio(tipo, valor, desconto);
        funcionario.getPlanoBeneficios().add(beneficio);
        return beneficio;
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

    public double calcularValeTransporte(Funcionario funcionario) {
        if (funcionario == null) throw new IllegalArgumentException("funcionario nulo");
        if (funcionario.getValeTransporte() == null)
        throw new IllegalStateException("Funcionario sem vale transporte configurado");
        double salario = funcionario.getSalarioBase();
        double valorVT = funcionario.getValeTransporte().getValor();
        double limite = salario * 0.06;
            
        double descontoVT = (valorVT > limite) ? valorVT:limite;
            
        funcionario.getValeTransporte().setDesconto(descontoVT);
        return descontoVT;
    }

    public double calcularValeAlimentacao(Funcionario funcionario) {
        //* */
        return 0.0;
    }
}
