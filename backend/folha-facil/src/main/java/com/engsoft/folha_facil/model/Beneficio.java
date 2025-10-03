package com.engsoft.folha_facil.model;
import com.engsoft.folha_facil.model.BeneficioTipo;

public class Beneficio {

    private BeneficioTipo tipo;
    private double valor;
  

    public Beneficio(BeneficioTipo tipo, double valor){
        this.tipo = tipo;
        this.valor = verificarValor(valor);
    }
    
    // getters
    public BeneficioTipo getTipo() {return tipo;}
    public double getValor() { return valor;}

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

    // Verifcadores

     private double verificarValor(double valor){
        if(valor <= 0 ){
            throw new IllegalArgumentException("Benefício está sem valor!");
        }
        return valor;
    }

}