package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class ImpostoService {
    private final FuncionarioRepository funcionarioRepository;

    public ImpostoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Imposto calcularImpostos(String funcionario){
        Funcionario f = funcionarioRepository.findByCpf(funcionario);
        if(f == null){
            throw new IllegalArgumentException("Funcionario do CPF " + funcionario + "não foi encontrado.");
        }

        Imposto imposto = new Imposto();
        double salario = f.getSalarioBase();

        //calcular INSS
        if(salario <= 1412.00){
            imposto.setINSS(salario * 0.075);
        }else if(salario <= 2666.68){
            imposto.setINSS(salario * 0.09);
        }else if(salario <= 4000.03){
            imposto.setINSS(salario * 0.12);
        }else{
            imposto.setINSS(salario * 0.14);
        }

        //calcular FGTS
        imposto.setFGTS(salario * 0.08);

        //calcular IRRF
        double IRRF;

        if(salario <= 2112.00){
            IRRF = 0.0;
        }else if(salario <= 2826.65){
            IRRF = salario * 0.075 - 158.40;
        }else if(salario <= 3751.05){
            IRRF = salario * 0.15 - 370.40;
        }else if(salario <= 4664.68){
            IRRF = salario * 0.225 - 651.73;
        }else{
            IRRF = salario * 0.275 - 884.96;
        }

        if(IRRF < 0) IRRF = 0.0;

        imposto.setIRRF(IRRF);

        //calcular Desconto Total
        imposto.setDescontoTotal(imposto.getINSS() + imposto.getFGTS() + imposto.getIRRF());

        return imposto;
    }
}
