/*package com.engsoft.folha_facil.folha;

import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.service.ImpostoService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculoINSSTest {

    @Test
    void testeCalculoINSSSalario3000() {
        ImpostoService impostoService = new ImpostoService(null);

        Imposto imposto = impostoService.calcularImpostos("55566677788");
        double inssEsperado = (1412.00 * 0.075) + ((2666.68 - 1412.00) * 0.09) + ((3000.00 - 2666.68) * 0.12);

        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001, "INSS deve ser calculado corretamente para salário 3000");
    }
}*/
