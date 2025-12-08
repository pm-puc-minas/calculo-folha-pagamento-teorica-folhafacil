package com.folhafacil.folhafacil.dto.Log;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogFilterDTO {
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
