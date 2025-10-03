package com.engsoft.folha_facil.model;

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
    private List<Beneficio> planoBeneficios = new ArrayList<>();
    private int numDependentes;
    private double pensaoAlimenticia;

    public Funcionario() {}

    public Funcionario(String nome, String cpf, String cargo, String telefone, String rua, String bairro, int numero,
                       String email, Date dataNascimento, Date dataAdmissao, double salarioBase, int horasSemanais,
                       List<Beneficio> planoBeneficios, int numDependentes, double pensaoAlimenticia) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.telefone = telefone;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.salarioBase = salarioBase;
        this.horasSemanais = horasSemanais;
        if (planoBeneficios != null) this.planoBeneficios = planoBeneficios;
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
    public List<Beneficio> getPlanoBeneficios() { return planoBeneficios; }
    public int getNumDependentes() { return numDependentes; }
    public double getPensaoAlimenticia() { return pensaoAlimenticia; }
    
    
    // Setters
    public void setNome(String nome) { this.nome = verificaStringVazia("nome", nome) ; }
    public void setCpf(String cpf) { this.cpf = verificaStringVazia("cpf", cpf); }
    public void setCargo(String cargo) { this.cargo = verificaStringVazia("cargo", cargo); }
    public void setTelefone(String telefone) { this.telefone = verificaStringVazia("telefone", telefone); }
    public void setRua(String rua) { this.rua = verificaStringVazia("rua", rua); }
    public void setBairro(String bairro) { this.bairro = verificaStringVazia("bairro", bairro); }
    public void setNumero(int numero) { this.numero = verificaNegativo("numero", numero); }
    public void setEmail(String email) { this.email = verificaStringVazia("email", email); }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = verificarDataNula("dataNascimento", dataNascimento); }
    public void setDataAdmissao(Date dataAdmissao) { this.dataAdmissao = verificarDataNula("dataAdmissão", dataAdmissao); }
    public void setPlanoBeneficios(List<Beneficio> planoBeneficios) { this.planoBeneficios = verificaListaMinima("palnoBenefício", planoBeneficios); }
    public void setNumDependentes(int numDependentes) { this.numDependentes = numDependentes; }  //*Valor situacional e pode ser 0 */
    public void setPensaoAlimenticia(double pensaoAlimenticia) { this.pensaoAlimenticia = pensaoAlimenticia; } //*Valor situacional e pode ser 0 */

    public void setSalarioBase(double salarioBase){
        verificaNegativo("SalarioBase", salarioBase);
    }
    
    public void setHorasSemanais(int horasSemanais){ 
        verificaNegativo("horasSemanais", horasSemanais);
        this.horasSemanais = horasSemanais;
    }


    //método que verificam os valores.
    private static double verificaNegativo(String campo, double valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }

    private static int verificaNegativo(String campo, int valor) {
        if (valor < 0) throw new IllegalArgumentException(campo + " não pode ser negativo");
        return valor;
    }

    private static <E> List<E> verificaListaMinima(String campo, List<E> valor){
        if(valor == null || valor.size() < 1) throw new IllegalArgumentException(campo + "deve conter ao menos 1 benefício!");
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

}