package com.folhafacil.folhafacil.dto.FolhaPagamento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FolhaPagamentoFilterDTO {
    private List<Long> ids;
    private LocalDate data;
    private List<String> funcionarios;
    private StatusFolhaPagamento status;
    private String tipoFuncionario;
}
