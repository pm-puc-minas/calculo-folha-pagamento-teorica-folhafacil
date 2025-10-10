package com.engsoft.folha_facil.folha;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

import org.junit.jupiter.api.Test;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculoINSSTest {

    @Test
    void testeCalculoINSSSalario3000() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);

        Funcionario f = new Funcionario(
            "Maria Souza",
            "555.666.777-88",
            "Assistente",
            "3199999-6666",
            "Rua B",
            "Bairro C",
            110,
            "maria.souza@empresa.com",
            new Date(101, 2, 2),
            new Date(121, 2, 2),
            3000.00,
            40,
            120.00,
            3000.00,
            null,
            1,
            0.00
        );

        Imposto imposto = impostoService.calcularImpostos(f);
        double inssEsperado = (1412.00 * 0.075) + ((2666.68 - 1412.00) * 0.09) + ((3000.00 - 2666.68) * 0.12);

        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001, "INSS deve ser calculado corretamente para salário 3000");
    }
}
