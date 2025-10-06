package com.engsoft.folha_facil.model;

import com.engsoft.folha_facil.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Funcionario {

    private String nome;
    private String cpf;
    private String cargo;
    private String telefone;
    private String rua;
    private String bairro;
    private int numero;
    private String email;
    private Date dataNascimento;
    private Date dataAdmissao;
    private double salarioBase;
    private int horasSemanais;
    private int diasTrabalhadosMes;
    private Beneficio valeTransporte;
    private List<Beneficio> planoBeneficios = new ArrayList<>();
    private int numDependentes;
    private double pensaoAlimenticia;
    
    public Funcionario(String nome, String cpf, String cargo, String telefone,
                       String rua, String bairro, int numero, String email,
                       Date dataNascimento, Date dataAdmissao, double salarioBase,
                       int horasSemanais, double valorValeTransporte, double desconto,
                       List<Beneficio> planoBeneficios, int numDependentes,
                       double pensaoAlimenticia) {

        // Campos obrigatórios e validados
        this.nome = verificaStringVazia("nome", nome);
        this.cpf = verificaStringVazia("cpf", cpf);
        this.cargo = verificaStringVazia("cargo", cargo);
        this.telefone = verificaStringVazia("telefone", telefone);
        this.rua = verificaStringVazia("rua", rua);
        this.bairro = verificaStringVazia("bairro", bairro);
        this.numero = verificaNegativo("numero", numero);
        this.email = verificaStringVazia("email", email);
        this.dataNascimento = verificarDataNula("dataNascimento", dataNascimento);
        this.dataAdmissao = verificarDataNula("dataAdmissão", dataAdmissao);
                    
        // Campos numéricos validados
        this.salarioBase = verificaNegativo("salarioBase", salarioBase);
        this.horasSemanais = verificaNegativo("horasSemanais", horasSemanais);

        // Benefícios
        this.valeTransporte = new Beneficio(BeneficioTipo.VALE_TRANSPORTE, valorValeTransporte,desconto);

        this.planoBeneficios = (planoBeneficios != null) ? planoBeneficios : new ArrayList<>();

        // Campos opcionais
        this.numDependentes = numDependentes;
        this.pensaoAlimenticia = pensaoAlimenticia;
    }

    // Getters
    public String getNome() { return nome; }
    public int getHorasSemanais() { return horasSemanais; }
    public String getCpf() { return cpf; }
    public String getCargo() { return cargo; }
    public String getTelefone() { return telefone; }
    public String getRua() { return rua; }
    public String getBairro() { return bairro; }
    public int getNumero() { return numero; }
    public String getEmail() { return email; }
    public Date getDataNascimento() { return dataNascimento; }
    public Date getDataAdmissao() { return dataAdmissao; }
    public double getSalarioBase() { return salarioBase; }
    public Beneficio getValeTransporte() { return valeTransporte; }
    public List<Beneficio> getPlanoBeneficios() { return planoBeneficios; }
    public int getNumDependentes() { return numDependentes; }
    public double getPensaoAlimenticia() { return pensaoAlimenticia; }
    
    
    // Setters
    public void setCargo(String cargo) { this.cargo = verificaStringVazia("cargo", cargo); }
    public void setTelefone(String telefone) { this.telefone = verificaStringVazia("telefone", telefone); }
    public void setRua(String rua) { this.rua = verificaStringVazia("rua", rua); }
    public void setBairro(String bairro) { this.bairro = verificaStringVazia("bairro", bairro); }
    public void setNumero(int numero) { this.numero = verificaNegativo("numero", numero); }
    public void setEmail(String email) { this.email = verificaStringVazia("email", email); }

    public void setValeTransporte(Beneficio valeTransporte) {
    this.valeTransporte = valeTransporte;}
    public void setPlanoBeneficios(List<Beneficio> planoBeneficios) 
    { this.planoBeneficios = planoBeneficios;}


    public void setSalarioBase(double salarioBase){this.salarioBase = verificaNegativo("SalarioBase", salarioBase);}
    
    public void setHorasSemanais(int horasSemanais){this.horasSemanais =verificaNegativo("horasSemanais", horasSemanais);}
    public void setNumDependentes(int numDependentes) { this.numDependentes = numDependentes; }  //*Valor situacional e pode ser 0 */
    public void setPensaoAlimenticia(double pensaoAlimenticia) { this.pensaoAlimenticia = pensaoAlimenticia; } //*Valor situacional e pode ser 0 */



    //método que verificam os valores.
    private static double verificaNegativo(String campo, double valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }

    private static int verificaNegativo(String campo, int valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }


    private static String verificaStringVazia(String campo, String valor){
        if (valor == null || valor.trim().isEmpty()){
            throw new IllegalArgumentException(campo + " não pode ficar vazio!");
        }
        return valor;
    }

    private static Date verificarDataNula(String campo, Date valor){
        if (valor == null) throw new IllegalArgumentException(campo + "não pode ser nula");
        return valor;
    }


    //Método temporário

    public void imprimirDados() {
    System.out.println("=== Dados do Funcionário ===");
    System.out.println("Nome: " + nome);
    System.out.println("CPF: " + cpf);
    System.out.println("Cargo: " + cargo);
    System.out.println("Telefone: " + telefone);
    System.out.println("Endereço: " + rua + ", " + numero + " - " + bairro);
    System.out.println("Email: " + email);
    System.out.println("Data de Nascimento: " + dataNascimento);
    System.out.println("Data de Admissão: " + dataAdmissao);
    System.out.println("Salário Base: " + salarioBase);
    System.out.println("Horas Semanais: " + horasSemanais);

    // Vale Transporte
    if (valeTransporte != null) {
        System.out.println("Vale Transporte: " + valeTransporte.getValor());
    }

    // Benefícios adicionais
    if (planoBeneficios != null && !planoBeneficios.isEmpty()) {
        System.out.println("=== Benefícios Adicionais ===");
        for (Beneficio b : planoBeneficios) {
            System.out.println(b.getTipo() + ": " + b.getValor());
        }
    }

    System.out.println("Número de Dependentes: " + numDependentes);
    System.out.println("Pensão Alimentícia: " + pensaoAlimenticia);
    System.out.println("=============================");
}


}