package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

public class ImpostoService {
    private final FuncionarioRepository funcionarioRepository;

    public ImpostoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    //Alterado somente para testes
    public Imposto calcularImpostos(Funcionario f){
        //Funcionario f = FuncionarioRepository.findByCpf(funcionario);
        if(f == null){
            throw new IllegalArgumentException("Funcionario do CPF " + f + "não foi encontrado.");
        }

        Imposto imposto = new Imposto();
        double salario = f.getSalarioBase();

        //calcular INSS
        double inss;
        if(salario <= 1412.00){
            inss = salario * 0.075;
        }else if(salario <= 2666.68){
            inss = (1412.00 * 0.075) + ((salario - 1412.00) * 0.09);
        }else if(salario <= 4000.03){
            inss = (1412.00 * 0.075) + ((2666.68 - 1412.00) * 0.09) + ((salario - 2666.68) * 0.12);
        }else if(salario <= 7786.02){
            inss = (1412.00 * 0.075) + ((2666.68 - 1412.00) * 0.09) + ((4000.03 - 2666.68) * 0.12) + ((salario - 4000.03) * 0.14);
        }else {
            inss = (1412.00 * 0.075) + ((2666.68 - 1412.00) * 0.09) + ((4000.03 - 2666.68) * 0.12) + ((7786.02 - 4000.03) * 0.14);
        }
        imposto.setINSS(inss);

        //calcular FGTS
        imposto.setFGTS(salario * 0.08);

        //calcular IRRF
        double irrf;
        double baseIRRF = salario - inss - (f.getNumDependentes() * 189.59);

        if(baseIRRF <= 2112.00){
            irrf = 0.0;
        }else if(baseIRRF <= 2826.65){
            irrf = (baseIRRF * 0.075) - 158.40;
        }else if(baseIRRF <= 3751.05){
            irrf = (baseIRRF * 0.15) - 370.40;
        }else if(baseIRRF <= 4664.68){
            irrf = (baseIRRF * 0.225) - 651.73;
        }else{
            irrf = (baseIRRF * 0.275) - 884.96;
        }

        if(irrf < 0) irrf = 0.0;

        imposto.setIRRF(irrf);

        //calcular Desconto Total
        imposto.setDescontoTotal(inss + imposto.getFGTS() + irrf);

        return imposto;
    }
}