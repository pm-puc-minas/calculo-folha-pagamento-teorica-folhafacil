package com.folhafacil.folhafacil.mapper;

import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamentoBeneficio;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.entity.ID.FolhaPagamentoBeneficioID;

import java.util.ArrayList;
import java.util.List;

public class FolhaPagamentoBenficioMapper {
    public static List<FolhaPagamentoBeneficio> toList(List<FuncionarioBeneficio> beneficios, FolhaPagamento fp) {
        if (beneficios == null || beneficios.isEmpty()) {
            return null;
        }

        List<FolhaPagamentoBeneficio> es = new ArrayList<>();

        for (FuncionarioBeneficio b : beneficios) {
            FolhaPagamentoBeneficio fb = new FolhaPagamentoBeneficio();

            fb.setId(new FolhaPagamentoBeneficioID(fp.getId(),b.getBeneficio().getId()));
            fb.setFolhaPagamento(fp);
            fb.setBeneficio(b.getBeneficio());
            fb.setValor(b.getValor());

            es.add(fb);
        }

        return es;
    }
}
