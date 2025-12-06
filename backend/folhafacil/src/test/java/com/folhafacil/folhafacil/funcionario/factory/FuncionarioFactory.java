package com.folhafacil.folhafacil.factory;

import com.folhafacil.folhafacil.model.Funcionario;
import com.folhafacil.folhafacil.model.Beneficio;
import com.folhafacil.folhafacil.model.BeneficioTipo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FuncionarioFactory - Implementação do Padrão de Projeto FACTORY.
 * 
 * Responsabilidade:
 * - Centralizar a criação de funcionários com diferentes perfis
 * - Aplicar configurações padrão baseadas no tipo (CLT, PJ, Estagiário)
 * - Simplificar a criação de objetos complexos
 * 
 * Vantagens:
 * 1. Código limpo e organizado
 * 2. Configurações padronizadas por tipo
 * 3. Fácil manutenção
 * 4. Reduz duplicação de código
 * 
 * @author Sistema FolhaFacil
 * @version 1.0
 */
public class FuncionarioFactory {
    
    // Constantes para valores padrão
    private static final double VALOR_VALE_TRANSPORTE_CLT = 220.0;
    private static final double DESCONTO_VALE_TRANSPORTE_CLT = 0.06; // 6%
    private static final double VALOR_VALE_ALIMENTACAO_CLT = 500.0;
    private static final double DESCONTO_VALE_ALIMENTACAO_CLT = 0.0;
    private static final int HORAS_SEMANAIS_CLT = 44;
    private static final int HORAS_SEMANAIS_ESTAGIARIO = 30;
    private static final int DIAS_TRABALHADOS_MES = 22;
    
    /**
     * Enum com os tipos de funcionário suportados.
     */
    public enum TipoFuncionario {
        CLT("CLT - Consolidação das Leis do Trabalho"),
        PJ("PJ - Pessoa Jurídica"),
        ESTAGIARIO("Estagiário");
        
        private final String descricao;
        
        TipoFuncionario(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    /**
     * Cria funcionário CLT com configurações padrão.
     * 
     * Configurações CLT:
     * - Vale Transporte: R$ 220,00 (desconto 6%)
     * - Vale Alimentação: R$ 500,00
     * - Jornada: 44h semanais
     * - Benefícios completos
     * 
     * @param nome Nome completo
     * @param cpf CPF
     * @param cargo Cargo/função
     * @param telefone Telefone de contato
     * @param rua Endereço - Rua
     * @param bairro Endereço - Bairro
     * @param numero Endereço - Número
     * @param email Email
     * @param dataNascimento Data de nascimento
     * @param salarioBase Salário base mensal
     * @return Funcionario CLT configurado
     */
    public static Funcionario criarCLT(String nome, String cpf, String cargo,
                                       String telefone, String rua, String bairro,
                                       int numero, String email, LocalDate dataNascimento,
                                       double salarioBase) {
        
        LocalDate dataAdmissao = LocalDate.now();
        
        // Benefícios padrão CLT
        List<Beneficio> beneficios = new ArrayList<>();
        beneficios.add(new Beneficio(BeneficioTipo.VALE_ALIMENTACAO, 
                                     VALOR_VALE_ALIMENTACAO_CLT, 
                                     DESCONTO_VALE_ALIMENTACAO_CLT));
        
        Funcionario funcionario = new Funcionario(
            nome, cpf, cargo, telefone, rua, bairro, numero, email,
            dataNascimento, dataAdmissao, salarioBase,
            HORAS_SEMANAIS_CLT,
            VALOR_VALE_TRANSPORTE_CLT,
            DESCONTO_VALE_TRANSPORTE_CLT,
            beneficios,
            0, // sem dependentes por padrão
            0.0 // sem pensão alimentícia por padrão
        );
        
        funcionario.setDiasTrabalhadosMes(DIAS_TRABALHADOS_MES);
        
        return funcionario;
    }
    
    /**
     * Cria funcionário CLT com dependentes e pensão alimentícia.
     */
    public static Funcionario criarCLT(String nome, String cpf, String cargo,
                                       String telefone, String rua, String bairro,
                                       int numero, String email, LocalDate dataNascimento,
                                       double salarioBase, int numDependentes, 
                                       double pensaoAlimenticia) {
        
        Funcionario funcionario = criarCLT(nome, cpf, cargo, telefone, rua, 
                                           bairro, numero, email, dataNascimento, 
                                           salarioBase);
        
        funcionario.setNumDependentes(numDependentes);
        funcionario.setPensaoAlimenticia(pensaoAlimenticia);
        
        return funcionario;
    }
    
    /**
     * Cria funcionário PJ (Pessoa Jurídica).
     * 
     * Configurações PJ:
     * - SEM Vale Transporte
     * - SEM Vale Alimentação
     * - SEM benefícios CLT
     * - Jornada: flexível (padrão 40h)
     * - Pagamento por serviço prestado
     * 
     * @return Funcionario PJ configurado
     */
    public static Funcionario criarPJ(String nome, String cpf, String cargo,
                                      String telefone, String rua, String bairro,
                                      int numero, String email, LocalDate dataNascimento,
                                      double valorContrato) {
        
        LocalDate dataAdmissao = LocalDate.now();
        
        // PJ não tem benefícios CLT
        List<Beneficio> beneficios = new ArrayList<>();
        
        Funcionario funcionario = new Funcionario(
            nome, cpf, cargo, telefone, rua, bairro, numero, email,
            dataNascimento, dataAdmissao, valorContrato,
            40, // jornada flexível, padrão 40h
            0.0, // sem vale transporte
            0.0, // sem desconto
            beneficios,
            0, // PJ não tem dependentes para IR
            0.0 // sem pensão alimentícia
        );
        
        funcionario.setDiasTrabalhadosMes(DIAS_TRABALHADOS_MES);
        
        return funcionario;
    }
    
    /**
     * Cria estagiário.
     * 
     * Configurações Estagiário:
     * - Bolsa-auxílio (não é salário)
     * - Vale Transporte obrigatório
     * - SEM outros benefícios CLT
     * - Jornada: máximo 30h semanais (Lei do Estágio)
     * - SEM descontos de INSS, IRRF, FGTS
     * 
     * @param bolsaAuxilio Valor da bolsa mensal
     * @return Funcionario Estagiário configurado
     */
    public static Funcionario criarEstagiario(String nome, String cpf, String curso,
                                              String telefone, String rua, String bairro,
                                              int numero, String email, LocalDate dataNascimento,
                                              double bolsaAuxilio) {
        
        LocalDate dataAdmissao = LocalDate.now();
        
        // Estagiário tem direito a vale transporte
        List<Beneficio> beneficios = new ArrayList<>();
        
        Funcionario estagiario = new Funcionario(
            nome, cpf, curso, // cargo = curso que está cursando
            telefone, rua, bairro, numero, email,
            dataNascimento, dataAdmissao, bolsaAuxilio,
            HORAS_SEMANAIS_ESTAGIARIO, // máximo 30h semanais
            VALOR_VALE_TRANSPORTE_CLT, // vale transporte obrigatório
            DESCONTO_VALE_TRANSPORTE_CLT,
            beneficios,
            0, // estagiário não tem dependentes
            0.0 // sem pensão alimentícia
        );
        
        estagiario.setDiasTrabalhadosMes(DIAS_TRABALHADOS_MES);
        
        return estagiario;
    }
    
    /**
     * Método genérico que cria funcionário baseado no tipo.
     * 
     * @param tipo TipoFuncionario enum
     * @param nome Nome completo
     * @param cpf CPF
     * @param cargo Cargo/função/curso
     * @param salario Salário base ou bolsa
     * @param dadosCompletos Dados de endereço e contato
     * @return Funcionario configurado
     */
    public static Funcionario criar(TipoFuncionario tipo, 
                                    String nome, String cpf, String cargo,
                                    double salario,
                                    DadosComplementares dadosCompletos) {
        
        switch (tipo) {
            case CLT:
                return criarCLT(
                    nome, cpf, cargo,
                    dadosCompletos.telefone,
                    dadosCompletos.rua,
                    dadosCompletos.bairro,
                    dadosCompletos.numero,
                    dadosCompletos.email,
                    dadosCompletos.dataNascimento,
                    salario
                );
                
            case PJ:
                return criarPJ(
                    nome, cpf, cargo,
                    dadosCompletos.telefone,
                    dadosCompletos.rua,
                    dadosCompletos.bairro,
                    dadosCompletes.numero,
                    dadosCompletos.email,
                    dadosCompletos.dataNascimento,
                    salario
                );
                
            case ESTAGIARIO:
                return criarEstagiario(
                    nome, cpf, cargo,
                    dadosCompletos.telefone,
                    dadosCompletos.rua,
                    dadosCompletos.bairro,
                    dadosCompletos.numero,
                    dadosCompletos.email,
                    dadosCompletos.dataNascimento,
                    salario
                );
                
            default:
                throw new IllegalArgumentException("Tipo de funcionário não suportado: " + tipo);
        }
    }
    
    /**
     * Classe auxiliar para agrupar dados complementares.
     * Facilita chamadas ao método genérico criar().
     */
    public static class DadosComplementares {
        public String telefone;
        public String rua;
        public String bairro;
        public int numero;
        public String email;
        public LocalDate dataNascimento;
        
        public DadosComplementares(String telefone, String rua, String bairro,
                                   int numero, String email, LocalDate dataNascimento) {
            this.telefone = telefone;
            this.rua = rua;
            this.bairro = bairro;
            this.numero = numero;
            this.email = email;
            this.dataNascimento = dataNascimento;
        }
    }
    
    /**
     * Método utilitário para validar CPF básico (formato).
     */
    private static boolean validarCPFFormato(String cpf) {
        if (cpf == null) return false;
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return cpfLimpo.length() == 11;
    }
}
