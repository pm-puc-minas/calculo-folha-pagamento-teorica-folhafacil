package com.folhafacil.folhafacil.model;

public enum BenificioTipo {
    VALE_TRANSPORTE(true),
    VALE_ALIMENTACAO(false),
    PERICULOSIDADE(true),
    INSALUBRIDADE(true);


    private final boolean monetario;

    BenificioTipo(boolean monetario) {
        this.monetario = monetario;
    }

    public boolean isMonetario(){
        return monetario;
    }
}
