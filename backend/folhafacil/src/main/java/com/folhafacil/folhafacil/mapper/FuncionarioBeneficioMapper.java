package com.folhafacil.folhafacil.mapper;

import com.folhafacil.folhafacil.dto.FuncionarioBeneficio.FuncionarioBeneficioDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.entity.ID.FuncionarioBeneficioID;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioBeneficioMapper {
    public static List<FuncionarioBeneficio> toEntityList(List<FuncionarioBeneficioDTO> ds, String uid){
        if(ds.isEmpty() || uid == null){
            return null;
        }

        List<FuncionarioBeneficio> es = new ArrayList<>();

        for(FuncionarioBeneficioDTO d : ds){
            FuncionarioBeneficio e = new FuncionarioBeneficio();

            FuncionarioBeneficioID id = new FuncionarioBeneficioID(uid, d.getIdBeneficio());
            e.setId(id);

            Funcionario funcionario = new Funcionario();
            funcionario.setId(uid);
            e.setFuncionario(funcionario);

            Beneficio beneficio = new Beneficio();
            beneficio.setId(d.getIdBeneficio());
            e.setBeneficio(beneficio);

            e.setValor(d.getValor());

            es.add(e);
        }

        return es;
    }
}
