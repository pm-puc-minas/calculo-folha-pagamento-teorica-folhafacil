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
public class FuncionarioBeneficioID implements Serializable {
    @Column(name = "idFuncionario")
    private String idFuncionario;

    @Column(name = "idBeneficio")
    private Long idBeneficio;
}
