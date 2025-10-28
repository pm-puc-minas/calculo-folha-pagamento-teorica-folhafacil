package com.folhafacil.folhafacil.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Imposto {
    private Long id;

    private BigDecimal INSS;
    private BigDecimal FGTS;
    private BigDecimal IRRF;
    private BigDecimal descontoTotal;

    public Imposto(){}

    public Imposto(BigDecimal INSS, BigDecimal FGTS, BigDecimal IRRF, BigDecimal descontoTotal) {
        this.INSS = INSS;
        this.FGTS = FGTS;
        this.IRRF = IRRF;
        this.descontoTotal = descontoTotal;
    }

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
