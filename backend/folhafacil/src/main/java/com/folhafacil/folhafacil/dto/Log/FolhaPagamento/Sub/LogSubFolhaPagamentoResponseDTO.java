package com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogSubFolhaPagamentoResponseDTO {
    private Long id;
    private String usuarioFuncionario;
    private String mensagem;
    private BigDecimal totalValorImposto;
    private BigDecimal totalValorBeneficios;
    private BigDecimal totalHorasExtras;
    private BigDecimal totalValorHorasExtras;
    private BigDecimal salarioBruto;
    private TipoLogSubFolhaPagamento tipo;
}
