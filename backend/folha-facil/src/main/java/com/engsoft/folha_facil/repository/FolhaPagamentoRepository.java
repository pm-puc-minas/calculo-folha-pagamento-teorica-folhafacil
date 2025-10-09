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
    
    // Buscar todas as folhas
    public static List<FolhaPagamento> findAll(){
        try(Reader reader = new FileReader(FILE_PATH)){
            List<FolhaPagamento> list = gson.fromJson(reader, new TypeToken<List<FolhaPagamento>>(){}.getType());
            return (list != null) ? list : new ArrayList();
        }
        catch (Exception e){
            return new ArrayList();
        }
    }

    // Salvar ou atualizar
    public static void save(FolhaPagamento folha){
        List<FolhaPagamento> folhas = findAll();

        boolean updated = false;
        for (int i = 0; i < folhas.size(); i++) {
            if (folhas.get(i).getId().equals(folha.getId())) {
                folhas.set(i, folha);

                updated = true;
                break;
            }
        }
        if(!updated){
            folhas.add(folha);
        }

        writeToFile(folhas);
    }

// Buscar por funcionário
    public static List<FolhaPagamento> findByFuncionario(Funcionario funcionario) {
        return findAll().stream()
                .filter(f -> f.getFuncionario() != null && f.getFuncionario().equals(funcionario))
                .collect(Collectors.toList());
    }

     // Buscar por CPF
    public static List<FolhaPagamento> findByCpf(String cpf) {
        return findAll().stream()
                .filter(f -> f.getFuncionario() != null && cpf.equals(f.getFuncionario().getCpf()))
                .collect(Collectors.toList());
    }

    // Buscar por período
    public static List<FolhaPagamento> findByPeriodo(Date inicio, Date fim) {
        return findAll().stream()
                .filter(f -> f.getDataPagamento() != null &&
                        !f.getDataPagamento().before(inicio) &&
                        !f.getDataPagamento().after(fim))
                .collect(Collectors.toList());
    }


     // Remover folha
    public static void delete(Long id) {
        List<FolhaPagamento> folhas = findAll();
        folhas.removeIf(f -> f.getId().equals(id));
        writeToFile(folhas);
    }

    // Salvar no arquivo
    private static void writeToFile(List<FolhaPagamento> folhas) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(folhas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}