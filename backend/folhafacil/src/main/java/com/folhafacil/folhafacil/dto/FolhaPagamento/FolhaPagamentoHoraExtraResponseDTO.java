package com.folhafacil.folhafacil.dto.FolhaPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolhaPagamentoHoraExtraResponseDTO {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private BigDecimal valorHora;
}
