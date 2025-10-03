package com.engsoft.folha_facil.controller;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.service.FuncionarioService;
import java.util.List;

public class FuncionarioController {
    
    private FuncionarioService service = new FuncionarioService();

    public void cadastrar(Funcionario f){
        service.cadastrarFuncionario(f);
        System.out.println("Funcionario cadastrado:" + f.getNome());
    }

    public void listarTodos(){
        List<Funcionario> lista = service.listarFuncionarios();
        for(Funcionario f: lista){
            System.out.println(f.getNome() + "-" + f.getCargo() + "-" + f.getSalarioBase());
        }
    }

}
