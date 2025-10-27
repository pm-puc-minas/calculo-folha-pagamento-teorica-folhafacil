package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.dto.Log.Funcionario.TipoFuncionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "LogFuncionario")
public class LogFuncionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idResponsavel", referencedColumnName = "id")
    @JsonIgnore
    private Funcionario idResponsavel;

    @ManyToOne
    @JoinColumn(name = "idManipulado", referencedColumnName = "id")
    @JsonIgnore
    private Funcionario idManipulado;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false, length = 150)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TipoFuncionario tipo;
}
