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
    void testeCalcularINSSFaixaMaxima() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);
        Funcionario f = new Funcionario("Pedro Alves",
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
            7507.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = (1412.00 * 0.075)
                + ((2666.68 - 1412.00) * 0.09)
                + ((4000.03 - 2666.68) * 0.12)
                + ((7786.02 - 4000.03) * 0.14);

        double baseIRRF = 9000.00 - inssEsperado;
        double irrfEsperado = (baseIRRF <= 2112.00) ? 0.0
            : (baseIRRF <= 2826.65) ? (baseIRRF * 0.075) - 158.40
            : (baseIRRF <= 3751.05) ? (baseIRRF * 0.15) - 370.40
            : (baseIRRF <= 4664.68) ? (baseIRRF * 0.225) - 651.73
            : (baseIRRF * 0.275) - 884.96;
        if (irrfEsperado < 0) irrfEsperado = 0.0;

        assertEquals(irrfEsperado, imposto.getIRRF(), 0.01);
    }
}
