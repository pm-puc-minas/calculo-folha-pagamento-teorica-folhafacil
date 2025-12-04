package com.folhafacil.folhafacil.dto.FolhaPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolhaPagamentoBeneficioResponseDTO {
    private String nomeBeneficio;
    private BigDecimal valorBeneficio;
}
