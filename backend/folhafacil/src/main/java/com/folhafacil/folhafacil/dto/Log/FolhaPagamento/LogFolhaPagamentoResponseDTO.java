package com.folhafacil.folhafacil.dto.Log.FolhaPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogFolhaPagamentoResponseDTO {
    private Long id;
    private String idResponsavel;
    private String usuarioResponsavel;
    private LocalDateTime data;
    private String mensagem;
    private TipoLogFolhaPagamento tipo;
}
