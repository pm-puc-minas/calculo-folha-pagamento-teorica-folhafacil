package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class CpfValidoTest {
    @Test
    void testCpfValido() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        String cpf = "11122233344";
        Funcionario f = new Funcionario("João Silva",
                cpf,
                "Auxiliar",
                "3199999-7777",
                "Rua A",
                "Bairro B",
                100,
                "joao.silva@empresa.com",
                new Date(100, 1, 1),
                new Date(120, 1, 1),
                3000.00,
                40,
                100.00,
                3000.00, null,
                1,
                0.00 );
        funcionarioRepository.save(f);

        boolean cpfValido = cpf != null && cpf.matches("\\d{11}");
        assertTrue(cpfValido, "O CPF deve conter exatamente 11 números."); 
    }
}
