package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class CadastroFuncionarioTest {
    @Test
        void testCadastroFunc() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        Funcionario f = new Funcionario("João Silva",
                    "11122233344",
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
        assertEquals("João Silva", f.getNome());
        assertEquals("11122233344", f.getCpf());
        assertEquals("Auxiliar", f.getCargo());
        assertEquals(3000.0, f.getSalarioBase(), 0.001);
        }
}
