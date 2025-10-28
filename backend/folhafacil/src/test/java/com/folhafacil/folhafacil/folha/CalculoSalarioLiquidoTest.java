/*
package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

public class CalculoSalarioLiquidoTest {
    @Test
    void testeCalculoSalarioLiquido() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);

        Funcionario f = new Funcionario(
            "Maria Souza",
            "55566677788",
            "Assistente",
            "3199999-6666",
            "Rua B",
            "Bairro C",
            110,
            "maria.souza@empresa.com",
            LocalDate.of(2001, 3, 2),
            LocalDate.of(2021, 3, 2),
            3000.00,
            40,
            120.00,
            3000.00, null,
            2,
            0.00
        );
        funcionarioRepository.save(f);

        Imposto imposto = impostoService.calcularImpostos("55566677788");

        double salarioBase = 3000.00;
        double inss = (1412.00 * 0.075)
                    + ((2666.68 - 1412.00) * 0.09)
                    + ((3000.00 - 2666.68) * 0.12);

        double baseIRRF = salarioBase - inss - (2 * 189.59);
        double irrf = (baseIRRF <= 2112.00) ? 0.0
            : (baseIRRF <= 2826.65) ? (baseIRRF * 0.075) - 158.40
            : (baseIRRF <= 3751.05) ? (baseIRRF * 0.15) - 370.40
            : (baseIRRF <= 4664.68) ? (baseIRRF * 0.225) - 651.73
            : (baseIRRF * 0.275) - 884.96;
        if (irrf < 0) irrf = 0.0;

        double salarioLiquidoEsperado = salarioBase - inss - irrf;
        double salarioLiquidoCalculado = salarioBase - imposto.getINSS() - imposto.getIRRF();
        assertEquals(salarioLiquidoEsperado, salarioLiquidoCalculado, 0.01);
    }
}*/
