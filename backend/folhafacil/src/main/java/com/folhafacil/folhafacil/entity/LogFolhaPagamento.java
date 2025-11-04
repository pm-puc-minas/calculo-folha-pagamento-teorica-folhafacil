package com.folhafacil.folhafacil.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogFolhaPagamento {
    private Long id;
    private Funcionario idResponsavel;
    private String mensagem;
    private LocalDateTime data;
}
