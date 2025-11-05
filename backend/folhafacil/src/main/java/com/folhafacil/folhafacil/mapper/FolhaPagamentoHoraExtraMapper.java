package com.folhafacil.folhafacil.mapper;

import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamentoHoraExtra;
import com.folhafacil.folhafacil.entity.HoraExtra;
import com.folhafacil.folhafacil.entity.ID.FolhaPagamentoHoraExtraID;

import java.util.ArrayList;
import java.util.List;

public class FolhaPagamentoHoraExtraMapper {
    public static List<FolhaPagamentoHoraExtra> toList(List<HoraExtra> he, FolhaPagamento fp) {
        if(he==null || he.isEmpty()){
            return null;
        }

        List<FolhaPagamentoHoraExtra> fhs = new ArrayList<>();

        for(HoraExtra h : he){
            FolhaPagamentoHoraExtra fh = new FolhaPagamentoHoraExtra();

            fh.setId(new FolhaPagamentoHoraExtraID(fp.getId(),h.getId()));
            fh.setFolhaPagamento(fp);
            fh.setHoraExtra(h);

            fhs.add(fh);
        }

        return fhs;
    }
}
