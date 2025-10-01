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

    // Métodos do diagrama (stubs)
    public List<Beneficio> consultarBeneficios() {
        return new ArrayList<>(planoBeneficios);
    }

    public float consultarDescontos() {
        // TODO: calcular descontos do funcionário
        return 0f;
    }

    public void consultarSalarioLiquido(FolhaPagamento fp) {
        // TODO: consultar salário líquido na folha informada
    }

    public void consultarFolhaPagamento(FolhaPagamento fp) {
        // TODO: consultar dados da folha
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public Date getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(Date dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public int getHorasSemanais() { return horasSemanais; }
    public void setHorasSemanais(int horasSemanais) { this.horasSemanais = horasSemanais; }
    public List<Beneficio> getPlanoBeneficios() { return planoBeneficios; }
    public void setPlanoBeneficios(List<Beneficio> planoBeneficios) { this.planoBeneficios = planoBeneficios; }
    public int getNumDependentes() { return numDependentes; }
    public void setNumDependentes(int numDependentes) { this.numDependentes = numDependentes; }
    public double getPensaoAlimenticia() { return pensaoAlimenticia; }
    public void setPensaoAlimenticia(double pensaoAlimenticia) { this.pensaoAlimenticia = pensaoAlimenticia; }
}