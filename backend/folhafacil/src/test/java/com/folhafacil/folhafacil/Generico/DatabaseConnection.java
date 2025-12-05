package com.folhafacil.folhafacil.Generico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe DatabaseConnection implementando o padrão Singleton.
 * Garante que apenas uma instância do EntityManagerFactory seja criada.
 * 
 * PADRÃO DE PROJETO: SINGLETON
 * 
 * Benefícios:
 * - Garante uma única instância do EntityManagerFactory (recurso pesado)
 * - Economia de memória e recursos do sistema
 * - Controle centralizado da conexão com o banco de dados
 * - Thread-safe com Double-Checked Locking
 * 
 * Localização: com.folhafacil.folhafacil.Generico.DatabaseConnection
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class DatabaseConnection {
    
    /**
     * Única instância da classe (Singleton).
     * Volatile garante visibilidade entre threads.
     */
    private static volatile DatabaseConnection instance;
    
    /**
     * EntityManagerFactory é thread-safe e deve ser único na aplicação.
     * É um recurso pesado que cria EntityManagers.
     */
    private final EntityManagerFactory entityManagerFactory;
    
    /**
     * Nome da unidade de persistência definida no persistence.xml.
     * Altere se o seu persistence-unit tiver outro nome.
     */
    private static final String PERSISTENCE_UNIT_NAME = "folhafacilPU";
    
    /**
     * Construtor privado para impedir instanciação externa.
     * Este é o princípio fundamental do padrão Singleton.
     * 
     * Inicializa o EntityManagerFactory uma única vez.
     */
    private DatabaseConnection() {
        try {
            System.out.println("🔄 Inicializando EntityManagerFactory (Singleton)...");
            
            // Cria o EntityManagerFactory baseado no persistence.xml
            this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            
            System.out.println("✓ EntityManagerFactory criado com sucesso!");
            System.out.println("✓ Padrão Singleton aplicado - apenas UMA instância será usada");
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar EntityManagerFactory: " + e.getMessage());
            throw new RuntimeException("Falha ao inicializar conexão com banco de dados", e);
        }
    }
    
    /**
     * Método público estático para obter a única instância da classe.
     * Implementa Double-Checked Locking para garantir thread-safety
     * com melhor performance.
     * 
     * Double-Checked Locking:
     * 1. Primeiro check: verifica se instância é null (sem sincronizar)
     * 2. Se null, sincroniza o bloco
     * 3. Segundo check: verifica novamente dentro do bloco sincronizado
     * 4. Cria instância apenas se ainda for null
     * 
     * @return a única instância de DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        // Primeiro check (sem sincronização para melhor performance)
        if (instance == null) {
            // Sincroniza apenas quando necessário
            synchronized (DatabaseConnection.class) {
                // Segundo check (dentro do bloco sincronizado)
                if (instance == null) {
                    instance = new DatabaseConnection();
                    System.out.println("📌 Nova instância Singleton criada");
                } else {
                    System.out.println("♻️  Reutilizando instância Singleton existente");
                }
            }
        }
        return instance;
    }
    
    /**
     * Retorna o EntityManagerFactory único da aplicação.
     * Use este método para obter o factory e criar EntityManagers.
     * 
     * @return EntityManagerFactory único
     */
    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            throw new IllegalStateException(
                "EntityManagerFactory não está disponível. Verifique a configuração."
            );
        }
        return entityManagerFactory;
    }
    
    /**
     * Cria um novo EntityManager a partir do factory único.
     * IMPORTANTE: Sempre feche o EntityManager após uso!
     * 
     * Exemplo de uso:
     * <pre>
     * EntityManager em = DatabaseConnection.getInstance().createEntityManager();
     * try {
     *     // use o EntityManager
     * } finally {
     *     em.close();
     * }
     * </pre>
     * 
     * @return novo EntityManager
     */
    public EntityManager createEntityManager() {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        System.out.println("🆕 Novo EntityManager criado do Singleton Factory");
        return em;
    }
    
    /**
     * Verifica se o EntityManagerFactory está aberto e funcional.
     * 
     * @return true se está aberto, false caso contrário
     */
    public boolean isOpen() {
        return entityManagerFactory != null && entityManagerFactory.isOpen();
    }
    
    /**
     * Fecha o EntityManagerFactory.
     * ATENÇÃO: Use apenas ao finalizar a aplicação!
     * Após fechar, não será possível criar novos EntityManagers.
     */
    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            System.out.println("🔒 Fechando EntityManagerFactory Singleton...");
            entityManagerFactory.close();
            System.out.println("✓ EntityManagerFactory fechado com sucesso");
        }
    }
    
    /**
     * Retorna informações sobre o estado do Singleton.
     * Útil para debugging e logs.
     * 
     * @return String com informações do estado
     */
    public String getInfo() {
        return String.format(
            "DatabaseConnection Singleton Info:%n" +
            "  - Instância criada: %s%n" +
            "  - EntityManagerFactory aberto: %s%n" +
            "  - Hash da instância: %d",
            (instance != null ? "SIM" : "NÃO"),
            isOpen(),
            System.identityHashCode(this)
        );
    }
    
    /**
     * Impede clonagem da instância Singleton.
     * Lança exceção se alguém tentar clonar.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(
            "Singleton não pode ser clonado! Use getInstance()"
        );
    }
}
