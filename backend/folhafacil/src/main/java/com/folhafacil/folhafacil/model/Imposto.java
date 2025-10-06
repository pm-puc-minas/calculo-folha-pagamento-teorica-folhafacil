package com.engsoft.folha_facil.model;

import java.util.Date;
import java.util.List;

public class Imposto {

    private double INSS;
    private double FGTS;
    private double IRRF;
    private double descontoTotal;

    public Imposto() {}

    public Imposto(double INSS, double FGTS, double IRRF, double descontoTotal) {
        this.INSS = INSS;
        this.FGTS = FGTS;
        this.IRRF = IRRF;
        this.descontoTotal = descontoTotal;
    }

    // Métodos do diagrama (stubs)
    public void calcularINSS(Funcionario f) {

    }
    
    public void calcularFGTS(Funcionario f) {
        
    }

    public void calcularIRRF(Funcionario f) {
        
    }

    public void calcularDescontoTotal() {
        // TODO: somar descontos
    }

    // Getters e Setters
    public double getINSS() { return INSS; }
    public void setINSS(double INSS) { this.INSS = INSS; }
    public double getFGTS() { return FGTS; }
    public void setFGTS(double FGTS) { this.FGTS = FGTS; }
    public double getIRRF() { return IRRF; }
    public void setIRRF(double IRRF) { this.IRRF = IRRF; }
    public double getDescontoTotal() { return descontoTotal; }
    public void setDescontoTotal(double descontoTotal) { this.descontoTotal = descontoTotal; }

    //método que verificam os valores.
    private static double verificaNegativo(String campo, double valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }

    private static int verificaNegativo(String campo, int valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }

    private static <E> List<E> verificaListaMinima(String campo, List<E> valor){
        if(valor == null || valor.size() < 1) throw new IllegalArgumentException(campo + "deve conter ao menos 1 benefício!");
        return valor;
    }

    private static String verificaStringVazia(String campo, String valor){
        if (valor == null || valor.trim().isEmpty()){
            throw new IllegalArgumentException(campo + " não pode ficar vazio!");
        }
        return valor;
    }

    private static Date verificarDataNula(String campo, Date valor){
        if (valor == null) throw new IllegalArgumentException(campo + "não pode ser nula");
        return valor;
    }

}