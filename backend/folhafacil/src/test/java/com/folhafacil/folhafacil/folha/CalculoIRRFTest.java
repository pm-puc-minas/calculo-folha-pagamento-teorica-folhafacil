/*
package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

public class CalculoIRRFTest {

@Test
    void testeCalculoIRRFComDependentes() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);

        Funcionario f = new Funcionario(
            "Pedro Alves",
            "77788899900",
            "Diretor",
            "3199999-3333",
            "Rua E",
            "Bairro F",
            150,
            "pedro.alves@empresa.com",
            LocalDate.of(2001, 3, 2),
            LocalDate.of(2021, 3, 2),
            7507.00,
            40,
            150.00,
            7507.00,
            null,
            2, 
            0.00
        );
        funcionarioRepository.save(f);
        Imposto imposto = impostoService.calcularImpostos("77788899900");
        double irrfEsperado = 835.99;
        assertEquals(irrfEsperado, imposto.getIRRF(), 0.01, "IRRF deve ser calculado corretamente com 2 dependentes");
    }
}*/
