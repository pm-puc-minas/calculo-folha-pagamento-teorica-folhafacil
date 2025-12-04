package com.folhafacil.folhafacil.dto.FolhaPagamento;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolhaPagamentoResponseDTO {
    private Long id;
    private String idFuncionario;
    private String usuarioFuncionario;
    private StatusFolhaPagamento status;
    private LocalDate data;
    private BigDecimal INSS;
    private BigDecimal FGTS;
    private BigDecimal IRRF;
    private BigDecimal totalValorImposto;
    private BigDecimal totalValorBeneficios;
    private BigDecimal totalHorasExtras;
    private BigDecimal totalValorHorasExtras;
    private BigDecimal salarioBruto;
    private BigDecimal salarioLiquido;
}
