package com.engsoft.folha_facil.repository;

import com.engsoft.folha_facil.model.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository {
    
    private List<Funcionario> funcionarios = new ArrayList<>();
    
    public Funcionario buscarPorCPF(String cpf){
        return funcionarios.stream().filter(f -> f.getCpf().equals(cpf)).findFirst().orElse(null);
    }
}


