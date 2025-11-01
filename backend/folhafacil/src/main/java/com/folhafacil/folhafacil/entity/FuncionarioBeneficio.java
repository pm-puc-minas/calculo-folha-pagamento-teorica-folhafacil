package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.entity.ID.FuncionarioBeneficioID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "FuncionarioBeneficio")
public class FuncionarioBeneficio {
    @EmbeddedId
    private FuncionarioBeneficioID id;

    @ManyToOne
    @MapsId("idFuncionario")
    @JoinColumn(name = "idFuncionario")
    @JsonIgnore
    private Funcionario funcionario;

    @ManyToOne
    @MapsId("idBeneficio")
    @JoinColumn(name = "idBeneficio")
    @JsonIgnore
    private Beneficio beneficio;

    @Column(name = "valor", precision = 9, scale = 2)
    private BigDecimal valor;
}
