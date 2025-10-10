package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.service.ImpostoService;

public class CalculoFGTSTest {
    @Test
    void testeCalcularFGTS() {
        ImpostoService impostoService = new ImpostoService(null);
        Funcionario f = new Funcionario("João Silva",
                "111.222.333-44",
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

        Imposto imposto = impostoService.calcularImpostos(f);

        assertEquals(3000.00 * 0.08, imposto.getFGTS(), 0.001);
}
}
