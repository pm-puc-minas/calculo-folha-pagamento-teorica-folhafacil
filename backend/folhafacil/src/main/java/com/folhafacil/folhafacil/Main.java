package com.engsoft.folha_facil;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        System.out.println("Rodando local sem Spring Boot");
        
        Beneficio vt = new Beneficio(BeneficioTipo.VALE_TRANSPORTE, 250);
        Beneficio va = new Beneficio(BeneficioTipo.VALE_ALIMENTACAO, 650);

        List<Beneficio> extras = new ArrayList<>();
        extras.add(va);


        Funcionario f = new Funcionario(
                "Pedro", 
                "123.456.789-00", 
                "Dev", 
                "31 99999-9999",
                "Rua X", 
                "Bairro Y", 
                123, 
                "email@email.com",
                new Date(95, 7, 20), // data de nascimento
                new Date(125, 0, 1), // data de admissão
                4500.0, // salário
                40,     // horas semanais
                250.0,  // valor do Vale Transporte
                extras, // benefícios adicionais
                0,      // número de dependentes
                0.0     // pensão alimentícia
        );

         System.out.println("Funcionário criado: " + f.getNome());


        
        f.imprimirDados();

        // Exercite seu código aqui (ex.: instanciar Funcionario, chamar setters, etc.)
    }
}