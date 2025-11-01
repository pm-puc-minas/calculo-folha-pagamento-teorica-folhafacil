package com.folhafacil.folhafacil.dto.FuncionarioBeneficio;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FuncionarioBeneficioDTO {
    private Long idBeneficio;
    private String nomeBeneficio;
    private BigDecimal valor;
}
