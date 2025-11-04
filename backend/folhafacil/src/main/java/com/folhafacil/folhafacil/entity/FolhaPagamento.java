package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "FolhaPagamento")
public class FolhaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idFuncionario", referencedColumnName = "id")
    @JsonIgnore
    private Funcionario idFuncionario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatusFolhaPagamento status;

    @Column(nullable = false)
    private LocalDate data;

    @Column(precision = 9, scale = 2)
    private BigDecimal INSS;

    @Column(precision = 9, scale = 2)
    private BigDecimal FGTS;

    @Column(precision = 9, scale = 2)
    private BigDecimal IRRF;

    @Column(precision = 9, scale = 2)
    private BigDecimal totalValorImposto;

    @Column(precision = 9, scale = 2)
    private BigDecimal totalValorBeneficios;

    @Column(precision = 9, scale = 2)
    private BigDecimal totalHorasExtras;

    @Column(precision = 9, scale = 2)
    private BigDecimal totalValorHorasExtras;

    @Column(precision = 9, scale = 2)
    private BigDecimal salarioBruto;

    @Column(precision = 9, scale = 2)
    private BigDecimal salarioLiquido;

    @Column(precision = 9, scale = 2)
    private BigDecimal total;
}
