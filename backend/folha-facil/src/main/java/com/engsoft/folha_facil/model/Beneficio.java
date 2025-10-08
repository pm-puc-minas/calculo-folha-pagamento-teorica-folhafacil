package com.engsoft.folha_facil.model;
import com.engsoft.folha_facil.model.BeneficioTipo;

public class Beneficio {


    private Long id;

    private BeneficioTipo tipo;
    private double valor;
    private double desconto;
  

    public Beneficio(BeneficioTipo tipo, double valor, double desconto){
        this.tipo = tipo;
        this.valor = verificarValor(valor);
        this.desconto = desconto;
    }
    
    // getters

    public Long getId() { return id; }

    public BeneficioTipo getTipo() {return tipo;}
    public double getValor() { return valor;}
    public double getDesconto() {return desconto;}

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    // Verifcadores

     private double verificarValor(double valor){
        if(valor <= 0 ){
            throw new IllegalArgumentException("Benefício está sem valor!");
        }
        return valor;
    }

}