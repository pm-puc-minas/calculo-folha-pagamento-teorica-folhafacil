package com.folhafacil.folhafacil.entity.ID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FolhaPagamentoHoraExtraID implements Serializable {
    @Column(name = "idFolhaPagamento")
    private Long idFolhaPagamento;

    @Column(name = "idHoraExtra")
    private Long idHoraExtra;
}
