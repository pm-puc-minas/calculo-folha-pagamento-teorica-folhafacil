package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.dto.HoraExtra.StatusHoraExtra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "HoraExtra")
public class HoraExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idFuncionario", referencedColumnName = "id")
    @JsonIgnore
    private Funcionario idFuncionario;

    @Column(name = "dataInicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "dataFim")
    private LocalDateTime dataFim;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusHoraExtra status;
}
