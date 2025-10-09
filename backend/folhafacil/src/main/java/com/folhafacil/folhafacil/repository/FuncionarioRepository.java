package com.engsoft.folha_facil.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.engsoft.folha_facil.model.Funcionario;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class FuncionarioRepository {
    
    private static final String FILE_PATH = "funcionarios.json";
    private static final Gson gson = new Gson();

    // Buscar todos os Funcionários
    public static List <Funcionario> findAll(){
        try (Reader reader = new FileReader(FILE_PATH)){
            List <Funcionario> list = gson.fromJson(reader, new TypeToken<List<Funcionario>>(){}.getType());
            return (list != null) ? list : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Salvar ou atualizar o funcionário
    public static void save(Funcionario f){

        List <Funcionario> funcionarios = findAll();

        boolean atualizado = false;

        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equals(f.getId())){
                funcionarios.set(i, f);
                atualizado = true;
                break;
            }    
        }

        if(!atualizado){
            funcionarios.add(f);
        }

        writeToFile(funcionarios);
    }


    //Buscar por ID
    public static Funcionario findById(Long id){
        return findAll().stream()
            .filter(f -> f.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    
    //Buscar por CPF
    public static Funcionario findByCpf(String cpf){
        return findAll().stream()
        .filter(f -> cpf.equals(f.getCpf()))
        .findFirst()
        .orElse(null);
    }

    //Deletar funcionario
    public static void delete(Long id){
        List <Funcionario> funcionarios = findAll();
        funcionarios.removeIf(f -> f.getId().equals(id));
        writeToFile(funcionarios);
    }

    private static void writeToFile(List <Funcionario> funcionarios){
        try(Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(funcionarios, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}





