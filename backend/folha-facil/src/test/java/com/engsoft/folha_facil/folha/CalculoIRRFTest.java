package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

public class CalculoIRRFTest {

    @Test
    void testeCalculoIRRFComDependentes() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);

        Imposto imposto = impostoService.calcularImpostos("55566677788");
        double irrfEsperado = 835.99;
        assertEquals(irrfEsperado, imposto.getIRRF(), 0.01, "IRRF deve ser calculado corretamente com 2 dependentes");
    }
}