package com.folhafacil.folhafacil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Funcionario")
public class Funcionario {
    @Id
    @Column(name = "id", nullable = false)
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

    @Column(name = "cargo", length = 100, nullable = false)
    private String cargo;

    @Column(name = "dataAdmissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "salarioBase", precision = 9, scale = 2, nullable = false)
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
    public static final Boolean HABILITADO = true;
    public static final Boolean DESABILITADO = false;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionarioBeneficio> beneficios = new ArrayList<>();
}
