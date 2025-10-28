package com.folhafacil.folhafacil.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private double verificarValor(double valor){
        if(valor <= 0 ){
            throw new IllegalArgumentException("Benefício está sem valor!");
        }
        return valor;
    }
}
