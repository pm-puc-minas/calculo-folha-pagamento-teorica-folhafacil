package com.folhafacil.folhafacil.dto.HoraExtra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoraExtraReponseDTO {
    private Long id;
    private String idFuncionario;
    private String usuario;
    private String nomeFuncionario;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String descricao;
    private StatusHoraExtra status;
}
