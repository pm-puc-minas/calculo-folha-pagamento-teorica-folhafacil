package com.folhafacil.folhafacil.dto.Log.Funcionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogFuncionarioResponseDTO {
    private Long id;
    private String idResponsavel;
    private String nomeResponsavel;
    private String idManipulado;
    private String nomeManipulado;
    private LocalDateTime data;
    private String mensagem;
    private TipoFuncionario tipo;
}
