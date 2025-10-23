package com.folhafacil.folhafacil.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Funcionario")
public class Funcionario {
    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(length = 11)
    private String telefone;

    @Column(length = 200)
    private String endereco;

    @Column(length = 200)
    private String email;

    private LocalDate dataNascimento;

    @Column(length = 10)
    private String cargo;

    private LocalDate dataAdmissao;

    @Column(precision = 9, scale = 2)
    private BigDecimal salarioBase;

    private Integer horasDiarias;

    private Integer diasMensal;

    private Integer numDependentes;

    @Column(precision = 9, scale = 2)
    private BigDecimal pensao;
}
