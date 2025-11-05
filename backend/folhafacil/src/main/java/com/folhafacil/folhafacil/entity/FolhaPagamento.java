package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "status", nullable = false, length = 15)
    private StatusFolhaPagamento status;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "INSS", precision = 9, scale = 2)
    private BigDecimal INSS;

    @Column(name = "FGTS", precision = 9, scale = 2)
    private BigDecimal FGTS;

    @Column(name = "IRRF", precision = 9, scale = 2)
    private BigDecimal IRRF;

    @Column(name = "totalValorImposto", precision = 9, scale = 2)
    private BigDecimal totalValorImposto;

    @Column(name = "totalValorBeneficios", precision = 9, scale = 2)
    private BigDecimal totalValorBeneficios;

    @Column(name = "totalHorasExtras", precision = 9, scale = 2)
    private BigDecimal totalHorasExtras;

    @Column(name = "totalValorHorasExtras", precision = 9, scale = 2)
    private BigDecimal totalValorHorasExtras;

    @Column(name = "salarioBruto", precision = 9, scale = 2)
    private BigDecimal salarioBruto;

    @Column(name = "salarioLiquido",precision = 9, scale = 2)
    private BigDecimal salarioLiquido;

    @Column(name = "total", precision = 9, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "folhaPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolhaPagamentoBeneficio> beneficios = new ArrayList<>();

    @OneToMany(mappedBy = "folhaPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolhaPagamentoHoraExtra> horasExtras = new ArrayList<>();
}
