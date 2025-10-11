package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
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
            "123.456.789-00",                
            "Analista de Sistemas",          
            "3199999-8888",                  
            "Rua das Palmeiras",             
            "Centro",                        
            120,                             
            "matheus.dias@empresa.com",      
            new Date(99, 4, 12),
            new Date(125, 0, 10),
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
}
