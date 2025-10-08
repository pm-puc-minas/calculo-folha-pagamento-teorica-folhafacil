package com.engsoft.folha_facil.repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.FolhaPagamento;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FolhaPagamentoRepository {
    private static final String FILE_PATH = "folhas_pagamento.json";
    private static final Gson gson = new Gson();
    
    public List<FolhaPagamento> findAll(){
        try(Reader reader = new FileReader(FILE_PATH)){
            List<FolhaPagamento> list = gson.fromJson(reader, new TypeToken<List<FolhaPagamento>>(){}.getType());
            return (list != null) ? list : new ArrayList();
        }
        catch (Exception e){
            return new ArrayList();
        }
    }

    public void save(FolhaPagamento folha){
        Boolean
    }


    public List<FolhaPagamento> buscarPorFuncionario(Funcionario funcionario){
        return folhas.stream().filter(f -> f.getFuncionario().equals(funcionario)).collect(Collectors.toList());
    }

    public List<FolhaPagamento> buscarPorCPF(String cpf){
        return folhas.stream().filter(f -> f.getFuncionario() != null && f.getFuncionario().getCpf().equals(cpf)).collect(Collectors.toList());
    }

    public List<FolhaPagamento> buscarPorPeriodo(Date inicio, Date fim){
        return folhas.stream().filter(f -> f.getDataPagamento() != null && !f.getDataPagamento().before(inicio) && !f.getDataPagamento().before(fim)).collect(Collectors.toList());
    }

    public boolean remover(FolhaPagamento folha){
        return folhas.remove(folha);
    }
}