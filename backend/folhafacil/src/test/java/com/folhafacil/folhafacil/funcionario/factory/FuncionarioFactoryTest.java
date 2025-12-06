package com.folhafacil.folhafacil.factory;

import com.folhafacil.folhafacil.model.Funcionario;
import com.folhafacil.folhafacil.factory.FuncionarioFactory.TipoFuncionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o FuncionarioFactory.
 * Valida a criação correta de funcionários CLT, PJ e Estagiários.
 * 
 * @author Sistema FolhaFacil
 * @version 1.0
 */
class FuncionarioFactoryTest {
    
    @Test
    @DisplayName("Deve criar funcionário CLT com configurações padrão")
    void deveCriarFuncionarioCLT() {
        // Arrange
        String nome = "João Silva";
        String cpf = "123.456.789-00";
        String cargo = "Desenvolvedor";
        LocalDate dataNascimento = LocalDate.of(1990, 5, 15);
        double salario = 5000.0;
        
        // Act
        Funcionario funcionario = FuncionarioFactory.criarCLT(
            nome, cpf, cargo,
            "(11) 98765-4321",
            "Rua das Flores", "Centro",
            100, "joao@email.com",
            dataNascimento, salario
        );
        
        // Assert
        assertNotNull(funcionario, "Funcionário não deve ser nulo");
        assertEquals(nome, funcionario.getNome());
        assertEquals(cpf, funcionario.getCpf());
        assertEquals(cargo, funcionario.getCargo());
        assertEquals(salario, funcionario.getSalarioBase());
        assertEquals(44, funcionario.getHorasSemanais(), "CLT deve ter 44h semanais");
        assertEquals(22, funcionario.getDiasTrabalhadosMes(), "Deve ter 22 dias trabalhados");
        
        // Verifica Vale Transporte
        assertNotNull(funcionario.getValeTransporte(), "CLT deve ter vale transporte");
        assertEquals(220.0, funcionario.getValeTransporte().getValor());
        assertEquals(0.06, funcionario.getValeTransporte().getDesconto());
        
        // Verifica benefícios
        assertNotNull(funcionario.getPlanoBeneficios(), "Deve ter plano de benefícios");
        assertFalse(funcionario.getPlanoBeneficios().isEmpty(), "CLT deve ter benefícios");
        
        System.out.println("✅ Funcionário CLT criado com sucesso!");
        funcionario.imprimirDados();
    }
    
    @Test
    @DisplayName("Deve criar funcionário CLT com dependentes e pensão")
    void deveCriarCLTComDependentes() {
        // Arrange & Act
        Funcionario funcionario = FuncionarioFactory.criarCLT(
            "Maria Santos", "987.654.321-00", "Gerente",
            "(11) 91234-5678",
            "Av. Paulista", "Bela Vista",
            1000, "maria@email.com",
            LocalDate.of(1985, 3, 20),
            8000.0,
            2, // 2 dependentes
            800.0 // pensão alimentícia
        );
        
        // Assert
        assertEquals(2, funcionario.getNumDependentes());
        assertEquals(800.0, funcionario.getPensaoAlimenticia());
        
        System.out.println("✅ Funcionário CLT com dependentes criado!");
        funcionario.imprimirDados();
    }
    
    @Test
    @DisplayName("Deve criar funcionário PJ sem benefícios CLT")
    void deveCriarFuncionarioPJ() {
        // Arrange
        String nome = "Carlos Oliveira";
        String cpf = "111.222.333-44";
        String servico = "Consultor TI";
        double valorContrato = 12000.0;
        
        // Act
        Funcionario funcionario = FuncionarioFactory.criarPJ(
            nome, cpf, servico,
            "(11) 99999-8888",
            "Rua dos Pinheiros", "Pinheiros",
            500, "carlos@empresa.com",
            LocalDate.of(1988, 7, 10),
            valorContrato
        );
        
        // Assert
        assertNotNull(funcionario);
        assertEquals(nome, funcionario.getNome());
        assertEquals(valorContrato, funcionario.getSalarioBase());
        assertEquals(40, funcionario.getHorasSemanais(), "PJ tem jornada flexível (40h padrão)");
        
        // PJ não tem vale transporte
        assertNotNull(funcionario.getValeTransporte());
        assertEquals(0.0, funcionario.getValeTransporte().getValor(), "PJ não tem VT");
        
        // PJ não tem benefícios CLT
        assertTrue(funcionario.getPlanoBeneficios().isEmpty(), "PJ não tem benefícios CLT");
        
        // PJ não tem dependentes
        assertEquals(0, funcionario.getNumDependentes());
        assertEquals(0.0, funcionario.getPensaoAlimenticia());
        
        System.out.println("✅ Funcionário PJ criado com sucesso!");
        funcionario.imprimirDados();
    }
    
    @Test
    @DisplayName("Deve criar estagiário com jornada reduzida")
    void deveCriarEstagiario() {
        // Arrange
        String nome = "Ana Paula";
        String cpf = "555.666.777-88";
        String curso = "Ciência da Computação";
        double bolsa = 1500.0;
        
        // Act
        Funcionario estagiario = FuncionarioFactory.criarEstagiario(
            nome, cpf, curso,
            "(11) 97777-6666",
            "Rua Universitária", "Vila Mariana",
            200, "ana@estudante.com",
            LocalDate.of(2002, 11, 25),
            bolsa
        );
        
        // Assert
        assertNotNull(estagiario);
        assertEquals(nome, estagiario.getNome());
        assertEquals(curso, estagiario.getCargo(), "Cargo deve ser o curso");
        assertEquals(bolsa, estagiario.getSalarioBase());
        assertEquals(30, estagiario.getHorasSemanais(), "Estagiário máx 30h semanais");
        
        // Estagiário tem vale transporte obrigatório
        assertNotNull(estagiario.getValeTransporte());
        assertEquals(220.0, estagiario.getValeTransporte().getValor(), "Deve ter VT");
        
        // Mas não tem outros benefícios CLT
        assertTrue(estagiario.getPlanoBeneficios().isEmpty(), "Não tem outros benefícios");
        
        System.out.println("✅ Estagiário criado com sucesso!");
        estagiario.imprimirDados();
    }
    
    @Test
    @DisplayName("Deve criar funcionário usando método genérico")
    void deveCriarUsandoMetodoGenerico() {
        // Arrange
        FuncionarioFactory.DadosComplementares dados = 
            new FuncionarioFactory.DadosComplementares(
                "(11) 95555-4444",
                "Rua Teste", "Bairro Teste",
                123, "teste@email.com",
                LocalDate.of(1995, 1, 1)
            );
        
        // Act - CLT
        Funcionario funcCLT = FuncionarioFactory.criar(
            TipoFuncionario.CLT,
            "Pedro Lima", "444.555.666-77", "Analista",
            6000.0, dados
        );
        
        // Act - PJ
        Funcionario funcPJ = FuncionarioFactory.criar(
            TipoFuncionario.PJ,
            "Julia Costa", "888.999.000-11", "Designer",
            10000.0, dados
        );
        
        // Act - Estagiário
        Funcionario estagiario = FuncionarioFactory.criar(
            TipoFuncionario.ESTAGIARIO,
            "Lucas Pereira", "222.333.444-55", "Engenharia",
            2000.0, dados
        );
        
        // Assert
        assertNotNull(funcCLT);
        assertEquals(44, funcCLT.getHorasSemanais());
        
        assertNotNull(funcPJ);
        assertEquals(40, funcPJ.getHorasSemanais());
        
        assertNotNull(estagiario);
        assertEquals(30, estagiario.getHorasSemanais());
        
        System.out.println("✅ Todos os tipos criados pelo método genérico!");
    }
    
    @Test
    @DisplayName("Deve comparar diferenças entre CLT, PJ e Estagiário")
    void deveCompararTiposFuncionario() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARAÇÃO ENTRE TIPOS DE FUNCIONÁRIO");
        System.out.println("=".repeat(70));
        
        LocalDate dataNasc = LocalDate.of(1990, 1, 1);
        
        // CLT
        Funcionario clt = FuncionarioFactory.criarCLT(
            "Funcionário CLT", "111.111.111-11", "Cargo CLT",
            "(11) 1111-1111", "Rua", "Bairro", 1, "clt@email.com",
            dataNasc, 5000.0
        );
        
        // PJ
        Funcionario pj = FuncionarioFactory.criarPJ(
            "Funcionário PJ", "222.222.222-22", "Cargo PJ",
            "(11) 2222-2222", "Rua", "Bairro", 2, "pj@email.com",
            dataNasc, 10000.0
        );
        
        // Estagiário
        Funcionario est = FuncionarioFactory.criarEstagiario(
            "Estagiário", "333.333.333-33", "Curso",
            "(11) 3333-3333", "Rua", "Bairro", 3, "est@email.com",
            dataNasc, 1500.0
        );
        
        System.out.println("\n📊 CLT:");
        System.out.println("   Horas: " + clt.getHorasSemanais() + "h/semana");
        System.out.println("   VT: R$ " + clt.getValeTransporte().getValor());
        System.out.println("   Benefícios: " + clt.getPlanoBeneficios().size());
        
        System.out.println("\n📊 PJ:");
        System.out.println("   Horas: " + pj.getHorasSemanais() + "h/semana");
        System.out.println("   VT: R$ " + pj.getValeTransporte().getValor());
        System.out.println("   Benefícios: " + pj.getPlanoBeneficios().size());
        
        System.out.println("\n📊 ESTAGIÁRIO:");
        System.out.println("   Horas: " + est.getHorasSemanais() + "h/semana");
        System.out.println("   VT: R$ " + est.getValeTransporte().getValor());
        System.out.println("   Benefícios: " + est.getPlanoBeneficios().size());
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ COMPARAÇÃO CONCLUÍDA!");
        System.out.println("=".repeat(70) + "\n");
    }
}
