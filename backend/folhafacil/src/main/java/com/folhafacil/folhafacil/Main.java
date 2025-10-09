package com.folhafacil.folhafacil;

import com.folhafacil.folhafacil.model.Funcionario;
import com.folhafacil.folhafacil.model.Beneficio;
import com.folhafacil.folhafacil.model.BeneficioTipo;
import com.folhafacil.folhafacil.repository.FuncionarioRepository;
import com.folhafacil.folhafacil.service.BeneficioService;
import com.folhafacil.folhafacil.service.FuncionarioService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1️⃣ Criar funcionário

        List<Beneficio> beneficios = new ArrayList<>();
        beneficios.add(new Beneficio(BeneficioTipo.VALE_ALIMENTACAO, 20.0, 0.0));
        beneficios.add(new Beneficio(BeneficioTipo.PERICULOSIDADE, 100.0, 0.0));

        
        Funcionario f1 = new Funcionario(
            "Pedro Vieira",
            "12345678900",
            "Analista",
            "31999999999",
            "Rua A",
            "Bairro B",
            100,
            "pedro@email.com",
            new Date(),
            new Date(),
            3000.0,
            40,
            150.0,
            0.0,
            beneficios,
            0,
            0.0
        );

        BeneficioService beneficioService = new BeneficioService(new FuncionarioService());

        beneficioService.calcularValeTransporte(f1);

        // 2️⃣ Salvar funcionário
        FuncionarioRepository.save(f1);
        System.out.println("Funcionário salvo com sucesso!");

        // 3️⃣ Listar todos
        System.out.println("=== Todos os funcionários ===");
        FuncionarioRepository.findAll().forEach(func -> 
            System.out.println(func.getId() + " - " + func.getNome())
        );

        // 4️⃣ Buscar por ID
        Funcionario buscado = FuncionarioRepository.findById(f1.getId());
        System.out.println("Funcionário buscado: " + (buscado != null ? buscado.getNome() : "Não encontrado"));

        // 5️⃣ Atualizar funcionário
        f1.setCargo("Desenvolvedor");
        FuncionarioRepository.save(f1);
        System.out.println("Cargo atualizado: " + FuncionarioRepository.findById(f1.getId()).getCargo());

        f1.imprimirDados();;

    }
}

