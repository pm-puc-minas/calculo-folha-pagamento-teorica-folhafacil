package com.folhafacil.folhafacil.dto.Beneficio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficioFuncionarioResponseDTO {
    private String nomeFuncionario;
    private String usuarioFuncionario;
    private BigDecimal valor;
}
