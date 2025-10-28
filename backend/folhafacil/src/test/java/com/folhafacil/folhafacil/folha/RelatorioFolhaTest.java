/*package com.engsoft.folha_facil.folha;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
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
            "Maria Souza", "55566677788", "Assistente", "3199999-6666", "Rua B", "Bairro C",
            110, "maria.souza@empresa.com",LocalDate.of(2001, 3, 2), LocalDate.of(2021, 3, 2),
            3000.00, 40, 120.00, 3000.00, null, 1, 0.00
        );
        Funcionario f2 = new Funcionario(
            "João Silva", "11122233344", "Auxiliar", "3199999-7777", "Rua A", "Bairro B",
            100, "joao.silva@empresa.com",             LocalDate.of(2001, 3, 2),
            LocalDate.of(2021, 3, 2),
            2000.00, 40, 100.00, 2000.00, null, 1, 0.00
        );

        List<Funcionario> funcionarios = Arrays.asList(f1, f2);

        for (Funcionario f : funcionarios) {
            Imposto imposto = impostoService.calcularImpostos("55566677788");

            double salarioLiquido = f.getSalarioBase() - imposto.getINSS() - imposto.getIRRF();
            
            assertNotNull(f.getNome());
            assertNotNull(f.getCpf());
            assertTrue(f.getSalarioBase() > 0);
            assertTrue(salarioLiquido > 0);
        }
    }
}*/
