package com.folhafacil.folhafacil.dto.HoraExtra;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HoraExtraFilterDTO {
    private String idFuncionario;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
