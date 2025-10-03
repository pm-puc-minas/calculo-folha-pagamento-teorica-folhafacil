package com.engsoft.folha_facil.repository;

import com.engsoft.folha_facil.model.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository {
    
    private List<Funcionario> funcionarios = new ArrayList<>();

    public void salvar(Funcionario f){
        funcionarios.add(f);
    }

    public List<Funcionario> listarTodos(){
        return new ArrayList<>(funcionarios);
    }
}


