package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.entity.ID.FolhaPagamentoBeneficioID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "FolhaPagamentoBeneficio")
public class FolhaPagamentoBeneficio {
    @EmbeddedId
    private FolhaPagamentoBeneficioID id;

    @ManyToOne
    @MapsId("idFolhaPagamento")
    @JoinColumn(name = "idFolhaPagamento")
    @JsonIgnore
    private FolhaPagamento folhaPagamento;

    @ManyToOne
    @MapsId("idBeneficio")
    @JoinColumn(name = "idBeneficio")
    @JsonIgnore
    private Beneficio beneficio;

    @Column(name = "valor", precision = 9, scale = 2)
    private BigDecimal valor;
}
