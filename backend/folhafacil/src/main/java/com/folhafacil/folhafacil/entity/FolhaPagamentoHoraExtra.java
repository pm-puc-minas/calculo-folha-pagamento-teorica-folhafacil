package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.entity.ID.FolhaPagamentoHoraExtraID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "FolhaPagamentoHoraExtra")
public class FolhaPagamentoHoraExtra {
    @EmbeddedId
    private FolhaPagamentoHoraExtraID id;

    @ManyToOne
    @MapsId("idFolhaPagamento")
    @JoinColumn(name = "idFolhaPagamento")
    @JsonIgnore
    private FolhaPagamento folhaPagamento;

    @ManyToOne
    @MapsId("idHoraExtra")
    @JoinColumn(name = "idHoraExtra")
    @JsonIgnore
    private HoraExtra horaExtra;
}
