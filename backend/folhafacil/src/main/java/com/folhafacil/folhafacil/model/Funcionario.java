package com.folhafacil.folhafacil.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Funcionario {
    private long id;

    private String nome;
    private String cpf;
    private String cargo;
    private String telefone;
    private String rua;
    private String bairro;
    private int numero;
    private String email;
    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;
    private double salarioBase;
    private int horasSemanais;
    private int diasTrabalhadosMes;
    private Beneficio valeTransporte;
    private List<Beneficio> planoBeneficios = new ArrayList<>();
    private int numDependentes;
    private double pensaoAlimenticia;

    public Funcionario(String nome, String cpf, String cargo, String telefone,
                       String rua, String bairro, int numero, String email,
                       LocalDate dataNascimento2, LocalDate dataAdmissao2, double salarioBase,
                       int horasSemanais, double valorValeTransporte, double desconto,
                       List<Beneficio> planoBeneficios, int numDependentes,
                       double pensaoAlimenticia) {

        this.nome = verificaStringVazia("nome", nome);
        this.cpf = verificaStringVazia("cpf", cpf);
        this.cargo = verificaStringVazia("cargo", cargo);
        this.telefone = verificaStringVazia("telefone", telefone);
        this.rua = verificaStringVazia("rua", rua);
        this.bairro = verificaStringVazia("bairro", bairro);
        this.numero = verificaNegativo("numero", numero);
        this.email = verificaStringVazia("email", email);
        this.dataNascimento = verificarDataNula("dataNascimento", dataNascimento2);
        this.dataAdmissao = verificarDataNula("dataAdmissão", dataAdmissao2);

        this.salarioBase = verificaNegativo("salarioBase", salarioBase);
        this.horasSemanais = verificaNegativo("horasSemanais", horasSemanais);

        this.valeTransporte = new Beneficio(BeneficioTipo.VALE_TRANSPORTE, valorValeTransporte,desconto);

        this.planoBeneficios = (planoBeneficios != null) ? planoBeneficios : new ArrayList<>();

        this.numDependentes = numDependentes;
        this.pensaoAlimenticia = pensaoAlimenticia;
    }

    public Funcionario(String nome2, String cpf2, String cargo2, String telefone2, String rua2, String bairro2,
                       int numero2, String email2, Date dataNascimento2, Date dataAdmissao2, double salarioBase2,
                       int horasSemanais2, double valorValeTransporte, double desconto, List<Beneficio> planoBeneficios2,
                       int numDependentes2, double pensaoAlimenticia2) {
    }

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

    private static LocalDate verificarDataNula(String campo, LocalDate valor){
        if (valor == null) throw new IllegalArgumentException(campo + "não pode ser nula");
        return valor;
    }



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

        if (valeTransporte != null) {
            System.out.println("=== Vale Transporte ===");
            System.out.println("Valor: " + valeTransporte.getValor());
            System.out.println("Desconto: " + valeTransporte.getDesconto());
        }

        if (planoBeneficios != null && !planoBeneficios.isEmpty()) {
            System.out.println("=== Benefícios Adicionais ===");
            for (Beneficio b : planoBeneficios) {
                System.out.println(b.getTipo() + ": " + b.getValor() + " | Desconto: " + b.getDesconto());
            }
        }

        System.out.println("Número de Dependentes: " + numDependentes);
        System.out.println("Pensão Alimentícia: " + pensaoAlimenticia);
        System.out.println("=============================");
    }
}
