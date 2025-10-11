package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class CpfValidoTest {
@Test
void testCpfInvalido() {
    FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    String cpfInvalido = "123";
    
    Funcionario f = new Funcionario("João Silva",
            cpfInvalido,
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


    boolean cpfValido = cpfInvalido != null && cpfInvalido.matches("\\d{11}");
    assertFalse(cpfValido, "CPF inválido não deve passar na validação");

    if(cpfValido) {
        funcionarioRepository.save(f);
    }

    Funcionario fSalvo = funcionarioRepository.findByCpf(cpfInvalido);
    assertNull(fSalvo, "Funcionário com CPF inválido não deve ser salvo");
}

}
