package com.folhafacil.folhafacil.dto.HoraExtra;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoraExtraDTO {
    private String descricao;
    private String idFuncionario;
    private LocalDateTime dataInicio;
}
