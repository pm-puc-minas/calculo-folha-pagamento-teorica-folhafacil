package com.engsoft.folha_facil.folha;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CalculoINSSTest {
    FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    ImpostoService impostoService = new ImpostoService(funcionarioRepository);
    @Test
    void testeCalcularINSSFaixa1() {
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
            1200.00,
            40,
            100.00,
            1200.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = 1200.00 * 0.075;
        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001);
    }

    @Test
    void testeCalcularINSSFaixa2() {
        Funcionario f = new Funcionario("Maria Souza",
            "555.666.777-88",
            "Assistente",
            "3199999-6666",
            "Rua B",
            "Bairro C",
            110,
            "maria.souza@empresa.com",
            new Date(101, 2, 2),
            new Date(121, 2, 2),
            2000.00,
            40,
            120.00,
            2000.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = (1412.00 * 0.075) + ((2000.00 - 1412.00) * 0.09);
        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001);
    }

    @Test
    void testeCalcularINSSFaixa3() {
        Funcionario f = new Funcionario("Carlos Lima",
            "999.888.777-66",
            "Coordenador",
            "3199999-5555",
            "Rua C",
            "Bairro D",
            130,
            "carlos.lima@empresa.com",
            new Date(102, 3, 3),
            new Date(122, 3, 3),
            3500.00,
            40,
            130.00,
            3500.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = (1412.00 * 0.075)
                + ((2666.68 - 1412.00) * 0.09)
                + ((3500.00 - 2666.68) * 0.12);
        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001);
    }

    @Test
    void testeCalcularINSSFaixa4() {
        Funcionario f = new Funcionario("Ana Paula",
            "333.444.555-66",
            "Gerente",
            "3199999-4444",
            "Rua D",
            "Bairro E",
            140,
            "ana.paula@empresa.com",
            new Date(103, 4, 4),
            new Date(123, 4, 4),
            5000.00,
            40,
            140.00,
            5000.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = (1412.00 * 0.075)
                + ((2666.68 - 1412.00) * 0.09)
                + ((4000.03 - 2666.68) * 0.12)
                + ((5000.00 - 4000.03) * 0.14);
        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001);
    }

    @Test
    void testeCalcularINSSFaixaMaxima() {
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
            9000.00,
            40,
            150.00,
            9000.00, null,
            1,
            0.00 );

        Imposto imposto = impostoService.calcularImpostos(f);

        double inssEsperado = (1412.00 * 0.075)
                + ((2666.68 - 1412.00) * 0.09)
                + ((4000.03 - 2666.68) * 0.12)
                + ((7786.02 - 4000.03) * 0.14);
        assertNotNull(imposto);
        assertEquals(inssEsperado, imposto.getINSS(), 0.001);
    }
}
