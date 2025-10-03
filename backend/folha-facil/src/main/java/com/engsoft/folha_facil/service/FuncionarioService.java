package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import java.util.List;;


public class FuncionarioService {
    
    private FuncionarioRepository repo = new FuncionarioRepository();

    public void cadastrarFuncionario(Funcionario f){
        repo.salvar(f);
    }

    public List<Funcionario> listarFuncionarios(){
        return repo.listarTodos();
    }

}
