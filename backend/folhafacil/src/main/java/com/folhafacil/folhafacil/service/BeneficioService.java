package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;
import com.engsoft.folha_facil.model.Funcionario;


public class BeneficioService {

    private final FuncionarioService funcionarioService;

    public BeneficioService(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    public Beneficio adicionarBeneficio(Funcionario funcionario, BeneficioTipo tipo, double valor, double desconto){
        if (funcionario == null) throw new IllegalArgumentException("Funcionario nulo");
       
        Beneficio beneficio = new Beneficio(tipo, valor, desconto);
        funcionario.getPlanoBeneficios().add(beneficio);
        return beneficio;
    }
     // Métodos do diagrama (stubs)
    public Beneficio calcularPericulosidade(Funcionario funcionario) {
        if(funcionario.getSalarioBase() <= 0)
            throw new IllegalArgumentException("O funcionário precisa de um salario!");
            
        double adicional = funcionario.getSalarioBase() * 0.3;
        
        return adicionarBeneficio(funcionario, BeneficioTipo.PERICULOSIDADE, adicional, 0.0);
    }

    public Beneficio calcularInsalubridade(int grausInsalubridade, Funcionario funcionario) {
        if(grausInsalubridade != 10 || grausInsalubridade != 20 || grausInsalubridade != 40 )
            throw new IllegalArgumentException("Valor de grau de insalibridade inválido, deve ser 10,20 ou 40.");
        double adicional = funcionario.getSalarioBase() * (grausInsalubridade/100);
        return adicionarBeneficio(funcionario, BeneficioTipo.INSALUBRIDADE, adicional, 0.0);
    } 

     public String grausInsalubridade(int grausInsalubridade) {
        return switch (grausInsalubridade) {
            case 10 -> "Baixo";
            case 20 -> "Médio";
            case 40 -> "Alto";
            default -> "Inválido";
        };
    }

    public Beneficio calcularValeTransporte(Funcionario funcionario) {

        double salario = funcionario.getSalarioBase();
        double valorVT = funcionario.getValeTransporte().getValor();
        double limite = salario * 0.06;
        
        double descontoVT = (valorVT > limite) ? valorVT:limite;
            

        funcionario.getValeTransporte().setDesconto(descontoVT);
        
        return adicionarBeneficio(funcionario, BeneficioTipo.VALE_TRANSPORTE, valorVT, descontoVT);
    }

    public Beneficio calcularValeAlimentacao(Funcionario funcionario, int mes, int ano, boolean trabalhaSabado, double valorDiario) {
            int diasUteis = funcionarioService.contarDiasUteis(mes, ano, trabalhaSabado);

            double valorTotal = diasUteis * valorDiario;
        
            return adicionarBeneficio(funcionario, BeneficioTipo.VALE_ALIMENTACAO, valorTotal, 0.0);
    }
}
