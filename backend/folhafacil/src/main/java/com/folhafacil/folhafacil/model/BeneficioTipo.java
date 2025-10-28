package com.folhafacil.folhafacil.model;

public enum BeneficioTipo {
    VALE_TRANSPORTE(true),
    VALE_ALIMENTACAO(false),
    PERICULOSIDADE(true),
    INSALUBRIDADE(true);


    private final boolean monetario;

    BeneficioTipo(boolean monetario) {
        this.monetario = monetario;
    }

    public boolean isMonetario(){
        return monetario;
    }
}
