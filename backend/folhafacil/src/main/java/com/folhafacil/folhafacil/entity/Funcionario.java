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
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone", length = 11, nullable = false)
    private String telefone;

    @Column(name = "endereco", length = 200, nullable = false)
    private String endereco;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "cargo",length = 10, nullable = false)
    private String cargo;

    @Column(name = "dataAdmissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "salarioBase",precision = 9, scale = 2, nullable = false)
    private BigDecimal salarioBase;

    @Column(name = "horasDiarias", nullable = false)
    private Integer horasDiarias;

    @Column(name = "diasMensal", nullable = false)
    private Integer diasMensal;

    @Column(name = "numDependentes")
    private Integer numDependentes;

    @Column(name = "pensao", precision = 9, scale = 2)
    private BigDecimal pensao;

    @Column(name = "usuario", length = 100, nullable = false)
    private String usuario;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
