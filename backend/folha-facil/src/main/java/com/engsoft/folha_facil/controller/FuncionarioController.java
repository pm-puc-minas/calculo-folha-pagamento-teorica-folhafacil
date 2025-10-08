package com.engsoft.folha_facil.controller;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.service.FuncionarioService;
import com.engsoft.folha_facil.repository.*;
import java.util.List;

public class FuncionarioController {
    
    private FuncionarioService service = new FuncionarioService();
    private FuncionarioRepository repository = new FuncionarioRepository();

    public void cadastrar(Funcionario f){
        repository.save(f);
        System.out.println("Funcionario cadastrado:" + f.getNome());
    }

    public void listarTodos(){
        List<Funcionario> lista = repository.findAll();
        for(Funcionario f: lista){
            System.out.println(f.getNome() + "-" + f.getCargo() + "-" + f.getSalarioBase());
        }
    }

}
