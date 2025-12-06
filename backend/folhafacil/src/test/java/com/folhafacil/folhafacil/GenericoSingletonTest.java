package com.folhafacil.folhafacil.Generico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para validar o padrão Singleton do DatabaseConnection.
 * 
 * Testes implementados:
 * 1. Verifica se sempre retorna a mesma instância
 * 2. Valida thread-safety (múltiplas threads)
 * 3. Testa criação de EntityManager
 * 4. Valida que clone não é permitido
 * 
 * @author Seu Nome
 * @version 1.0
 */
class DatabaseConnectionTest {
    
    @Test
    @DisplayName("Deve retornar sempre a mesma instância (Singleton)")
    void deveRetornarMesmaInstancia() {
        // Arrange & Act
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        DatabaseConnection instance3 = DatabaseConnection.getInstance();
        
        // Assert
        assertNotNull(instance1, "Primeira instância não deve ser nula");
        assertNotNull(instance2, "Segunda instância não deve ser nula");
        assertNotNull(instance3, "Terceira instância não deve ser nula");
        
        // Verifica se são a MESMA instância (mesmo endereço de memória)
        assertSame(instance1, instance2, 
            "instance1 e instance2 devem ser a mesma instância");
        assertSame(instance2, instance3, 
            "instance2 e instance3 devem ser a mesma instância");
        assertSame(instance1, instance3, 
            "instance1 e instance3 devem ser a mesma instância");
        
        // Verifica hashCode (deve ser igual para a mesma instância)
        assertEquals(System.identityHashCode(instance1), 
                     System.identityHashCode(instance2),
                     "HashCodes devem ser iguais");
        
        System.out.println("✅ SINGLETON VALIDADO: Apenas uma instância criada!");
        System.out.println("   Hash instance1: " + System.identityHashCode(instance1));
        System.out.println("   Hash instance2: " + System.identityHashCode(instance2));
        System.out.println("   Hash instance3: " + System.identityHashCode(instance3));
    }
    
    @Test
    @DisplayName("Deve manter Singleton em ambiente multi-thread")
    void deveSerThreadSafe() throws InterruptedException {
        // Arrange
        final int NUM_THREADS = 10;
        Thread[] threads = new Thread[NUM_THREADS];
        DatabaseConnection[] instances = new DatabaseConnection[NUM_THREADS];
        
        // Act - Cria múltiplas threads tentando obter instância simultaneamente
        for (int i = 0; i < NUM_THREADS; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = DatabaseConnection.getInstance();
                System.out.println("Thread " + index + " obteve instância: " + 
                                 System.identityHashCode(instances[index]));
            });
        }
        
        // Inicia todas as threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Aguarda todas finalizarem
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Assert - Todas devem ter a mesma instância
        DatabaseConnection firstInstance = instances[0];
        for (int i = 1; i < NUM_THREADS; i++) {
            assertSame(firstInstance, instances[i], 
                "Thread " + i + " deve ter a mesma instância");
        }
        
        System.out.println("✅ THREAD-SAFETY VALIDADO: Singleton mantido em " + 
                         NUM_THREADS + " threads concorrentes!");
    }
    
    @Test
    @DisplayName("Deve ter EntityManagerFactory aberto")
    void deveTeorEntityManagerFactoryAberto() {
        // Arrange & Act
        DatabaseConnection db = DatabaseConnection.getInstance();
        
        // Assert
        assertTrue(db.isOpen(), "EntityManagerFactory deve estar aberto");
        assertNotNull(db.getEntityManagerFactory(), 
            "EntityManagerFactory não deve ser nulo");
        assertTrue(db.getEntityManagerFactory().isOpen(), 
            "EntityManagerFactory deve estar aberto");
        
        System.out.println("✅ EntityManagerFactory está aberto e funcional!");
    }
    
    @Test
    @DisplayName("Deve criar EntityManager válido")
    void deveCriarEntityManagerValido() {
        // Arrange
        DatabaseConnection db = DatabaseConnection.getInstance();
        EntityManager em = null;
        
        try {
            // Act
            em = db.createEntityManager();
            
            // Assert
            assertNotNull(em, "EntityManager não deve ser nulo");
            assertTrue(em.isOpen(), "EntityManager deve estar aberto");
            
            System.out.println("✅ EntityManager criado com sucesso!");
            
        } finally {
            // Cleanup
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("   EntityManager fechado após teste");
            }
        }
    }
    
    @Test
    @DisplayName("Deve exibir informações corretas do Singleton")
    void deveExibirInformacoesCorretas() {
        // Arrange & Act
        DatabaseConnection db = DatabaseConnection.getInstance();
        String info = db.getInfo();
        
        // Assert
        assertNotNull(info, "Info não deve ser nula");
        assertTrue(info.contains("DatabaseConnection Singleton Info"), 
            "Info deve conter título");
        assertTrue(info.contains("Instância criada: SIM"), 
            "Info deve confirmar instância criada");
        assertTrue(info.contains("EntityManagerFactory aberto: true"), 
            "Info deve confirmar factory aberto");
        
        System.out.println("✅ Informações do Singleton:");
        System.out.println(info);
    }
    
    @Test
    @DisplayName("Não deve permitir clonagem")
    void naoDevePermitirClonagem() {
        // Arrange
        DatabaseConnection db = DatabaseConnection.getInstance();
        
        // Act & Assert
        assertThrows(CloneNotSupportedException.class, () -> {
            // Tenta clonar usando reflexão
            db.clone();
        }, "Deve lançar exceção ao tentar clonar");
        
        System.out.println("✅ Clonagem bloqueada corretamente!");
    }
    
    @Test
    @DisplayName("Deve comparar instâncias usando equals")
    void deveCompararInstanciasCorretamente() {
        // Arrange & Act
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        // Assert
        assertEquals(db1, db2, "Instâncias devem ser iguais");
        assertTrue(db1 == db2, "Instâncias devem ter mesma referência (==)");
        
        System.out.println("✅ Comparação de instâncias validada!");
    }
    
    @Test
    @DisplayName("Demonstra uso típico do Singleton")
    void demonstraUsoTipico() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRAÇÃO DE USO DO SINGLETON");
        System.out.println("=".repeat(60));
        
        // 1. Obter instância
        System.out.println("\n1. Obtendo primeira instância...");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        System.out.println("   ✓ Instância obtida: " + System.identityHashCode(db1));
        
        // 2. Obter novamente (deve retornar a mesma)
        System.out.println("\n2. Obtendo segunda instância...");
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("   ✓ Instância obtida: " + System.identityHashCode(db2));
        System.out.println("   ✓ Mesma instância? " + (db1 == db2));
        
        // 3. Criar EntityManager
        System.out.println("\n3. Criando EntityManager...");
        EntityManager em = db1.createEntityManager();
        System.out.println("   ✓ EntityManager criado e aberto: " + em.isOpen());
        em.close();
        System.out.println("   ✓ EntityManager fechado");
        
        // 4. Exibir info
        System.out.println("\n4. Informações do Singleton:");
        System.out.println(db1.getInfo());
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!");
        System.out.println("=".repeat(60) + "\n");
    }
}
