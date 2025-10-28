/*package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class CadastroFuncionarioTest {
    @Test
        void testCadastroFunc() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        Funcionario f = new Funcionario(
            "Matheus Dias",
            "55566677788",
            "Analista de Sistemas",
            "3199999-8888",
            "Rua das Palmeiras",
            "Centro",
            120,
            "matheus.dias@empresa.com",
            LocalDate.of(2001, 3, 2),
            LocalDate.of(2021, 3, 2),
            3000.00,
            40,
            150.00,
            3000.00,
            null,
            2,
            0.00
        );

        funcionarioRepository.save(f);
        assertEquals("Matheus Dias", f.getNome());
        assertEquals("55566677788", f.getCpf());
        assertEquals("Analista de Sistemas", f.getCargo());
        assertEquals(3000.0, f.getSalarioBase(), 0.001);
        }
}*/
