package com.folhafacil.folhafacil.mapper;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.entity.Beneficio;

public class BeneficioMapper {
    public static Beneficio toEntity(BeneficioDTO d){
        if(d == null){
            return null;
        }

        Beneficio b = new Beneficio();

        b.setId(d.getId());
        b.setNome(d.getNome());

        return b;
    }

    public static BeneficioDTO toDto(Beneficio b){
        if(b == null){
            return null;
        }

        BeneficioDTO d = new BeneficioDTO();

        d.setId(b.getId());
        d.setNome(b.getNome());

        return d;
    }
}
