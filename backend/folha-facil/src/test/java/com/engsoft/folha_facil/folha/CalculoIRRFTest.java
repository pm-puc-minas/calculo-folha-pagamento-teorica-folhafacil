package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
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
            "777.888.999-00",
            "Diretor",
            "3199999-3333",
            "Rua E",
            "Bairro F",
            150,
            "pedro.alves@empresa.com",
            new Date(104, 5, 5),
            new Date(124, 5, 5),
            7507.00,
            40,
            150.00,
            7507.00,
            null,
            2, 
            0.00
        );

        Imposto imposto = impostoService.calcularImpostos(f);
        double irrfEsperado = 835.99;
        assertEquals(irrfEsperado, imposto.getIRRF(), 0.01, "IRRF deve ser calculado corretamente com 2 dependentes");
    }
}