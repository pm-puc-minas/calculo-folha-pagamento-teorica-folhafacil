package com.folhafacil.folhafacil.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.TipoLogSubFolhaPagamento;
import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.TipoLogFolhaPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "LogSubFolhaPagamento")
public class LogSubFolhaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idLogFolhaPagamento", referencedColumnName = "id")
    @JsonIgnore
    private LogFolhaPagamento idLogFolhaPagamento;

    @ManyToOne
    @JoinColumn(name = "idFolhaPagamento", referencedColumnName = "id")
    @JsonIgnore
    private FolhaPagamento idFolhaPagamento;

    @Column(name = "mensagem", nullable = false, length = 150)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoLogSubFolhaPagamento tipo;
}
