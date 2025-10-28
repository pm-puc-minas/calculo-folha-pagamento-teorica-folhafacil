/*package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.service.BeneficioService;

public class CalculoValeTransporteTest {
    private final BeneficioService beneficioService = new BeneficioService(null);
    @Test
    public void testCalculoValeTransporte() {
        List<Beneficio> beneficios = new ArrayList<>();
        double salarioBase = 3000.00;
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
            3000.00,
            40,
            150.00,
            salarioBase, beneficios,
            2,
            0.00 );
        
        var beneficio = beneficioService.calcularValeTransporte(f);

        assertEquals(150.00, beneficio.getValor(), 0.001);
        assertEquals(BeneficioTipo.VALE_TRANSPORTE, beneficio.getTipo());
        assertTrue(f.getPlanoBeneficios().contains(beneficio));
    }
}*/
