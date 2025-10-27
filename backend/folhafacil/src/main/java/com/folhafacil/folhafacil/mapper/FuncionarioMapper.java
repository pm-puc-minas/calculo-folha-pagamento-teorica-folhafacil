package com.folhafacil.folhafacil.mapper;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;

public class FuncionarioMapper {
    public static Funcionario toEntity(FuncionarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Funcionario e = new Funcionario();

        e.setId(dto.getId());
        e.setNome(dto.getNome());
        e.setCpf(dto.getCpf());
        e.setEmail(dto.getEmail());
        e.setEndereco(dto.getEndereco());
        e.setTelefone(dto.getTelefone());
        e.setDataAdmissao(dto.getDataAdmissao());
        e.setDataNascimento(dto.getDataNascimento());
        e.setHorasDiarias(dto.getHorasDiarias());
        e.setNumDependentes(dto.getNumDependentes());
        e.setCargo(dto.getCargo());
        e.setPensao(dto.getPensao());
        e.setSalarioBase(dto.getSalarioBase());
        e.setDiasMensal(dto.getDiasMensal());
        e.setUsuario(dto.getUsuario());

        return e;
    }
}
