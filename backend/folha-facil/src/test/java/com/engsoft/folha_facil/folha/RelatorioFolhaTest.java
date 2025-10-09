package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.ImpostoService;

public class RelatorioFolhaTest {

    @Test
    void testeRelatorioFolhaPagamentoSimples() {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ImpostoService impostoService = new ImpostoService(funcionarioRepository);

        Funcionario f1 = new Funcionario(
            "Maria Souza", "555.666.777-88", "Assistente", "3199999-6666", "Rua B", "Bairro C",
            110, "maria.souza@empresa.com", new Date(101, 2, 2), new Date(121, 2, 2),
            3000.00, 40, 120.00, 3000.00, null, 1, 0.00
        );
        Funcionario f2 = new Funcionario(
            "João Silva", "111.222.333-44", "Auxiliar", "3199999-7777", "Rua A", "Bairro B",
            100, "joao.silva@empresa.com", new Date(100, 1, 1), new Date(120, 1, 1),
            2000.00, 40, 100.00, 2000.00, null, 1, 0.00
        );

        List<Funcionario> funcionarios = Arrays.asList(f1, f2);

        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;

        for (Funcionario f : funcionarios) {
            Imposto imposto = impostoService.calcularImpostos(f);
            double salarioBase = f.getSalarioBase();
            double salarioLiquido = salarioBase - imposto.getINSS() - imposto.getIRRF();
            double descontos = imposto.getINSS() + imposto.getIRRF();

            totalSalarioBruto += salarioBase;
            totalDescontos += descontos;
            totalSalarioLiquido += salarioLiquido;
        }

        assertEquals(5000.00, totalSalarioBruto, 0.01);
        assertTrue(totalDescontos > 0);
        assertTrue(totalSalarioLiquido > 0);
        assertEquals(totalSalarioBruto - totalDescontos, totalSalarioLiquido, 0.01);
    }
}