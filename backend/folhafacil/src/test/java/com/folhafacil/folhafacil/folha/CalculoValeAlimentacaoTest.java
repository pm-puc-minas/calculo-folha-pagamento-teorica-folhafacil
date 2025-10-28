/*package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.service.BeneficioService;
import com.engsoft.folha_facil.service.FuncionarioService;

public class CalculoValeAlimentacaoTest {
    @Test
    public void testCalculoValeAlimentacao() {
        List<Beneficio> beneficios = new ArrayList<>();
        double valorDiario = 24.00;
        Funcionario f = new Funcionario("Matheus Dias",
            "55566677788",                
            "Analista de Sistemas",          
            "3199999-8888",                  
            "Rua das Palmeiras",             
            "Centro",                        
            120,                             
            "matheus.dias@empresa.com",      
            LocalDate.of(2001, 3, 2),
            LocalDate.of(2021, 3, 2),
            2000.00,
            40,
            150.00,
            valorDiario, beneficios,
            2,
            0.00 );

        FuncionarioService funcionarioService = mock(FuncionarioService.class);
        when(funcionarioService.contarDiasUteis(5, 2025, false)).thenReturn(26);
        BeneficioService beneficioService = new BeneficioService(funcionarioService);
        var beneficio = beneficioService.calcularValeAlimentacao(f, 5, 2025, false, valorDiario);

        assertEquals(624.0, beneficio.getValor(), 0.001);
        assertEquals(BeneficioTipo.VALE_ALIMENTACAO, beneficio.getTipo());
        assertTrue(f.getPlanoBeneficios().contains(beneficio));
    }

}*/
