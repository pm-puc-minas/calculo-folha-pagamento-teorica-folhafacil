package com.folhafacil.folhafacil.service.Imposto;

import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.model.Imposto;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImpostoServiceImpl {
    private final FuncionarioRepository funcionarioRepository;

    public ImpostoServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Imposto calcularImpostos(String cpfFuncionario) {
        Funcionario f = funcionarioRepository.findByCpf(cpfFuncionario);
        if (f == null) {
            throw new IllegalArgumentException("Funcionario do CPF " + cpfFuncionario + " não foi encontrado.");
        }

        Imposto imposto = new Imposto();
        BigDecimal salario = f.getSalarioBase();

        BigDecimal faixa1 = new BigDecimal("1412.00");
        BigDecimal faixa2 = new BigDecimal("2666.68");
        BigDecimal faixa3 = new BigDecimal("4000.03");
        BigDecimal faixa4 = new BigDecimal("7786.02");

        BigDecimal aliquota1 = new BigDecimal("0.075");
        BigDecimal aliquota2 = new BigDecimal("0.09");
        BigDecimal aliquota3 = new BigDecimal("0.12");
        BigDecimal aliquota4 = new BigDecimal("0.14");

        BigDecimal inss = BigDecimal.ZERO;

        if (salario.compareTo(faixa1) <= 0) {
            inss = salario.multiply(aliquota1);
        } else if (salario.compareTo(faixa2) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((salario.subtract(faixa1)).multiply(aliquota2));
        } else if (salario.compareTo(faixa3) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((salario.subtract(faixa2)).multiply(aliquota3));
        } else if (salario.compareTo(faixa4) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((faixa3.subtract(faixa2)).multiply(aliquota3))
                    .add((salario.subtract(faixa3)).multiply(aliquota4));
        } else {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((faixa3.subtract(faixa2)).multiply(aliquota3))
                    .add((faixa4.subtract(faixa3)).multiply(aliquota4));
        }
        imposto.setINSS(inss.setScale(2, RoundingMode.HALF_UP));

        imposto.setFGTS(salario.multiply(new BigDecimal("0.08")).setScale(2, RoundingMode.HALF_UP));

        BigDecimal deducaoDependente = new BigDecimal("189.59");
        BigDecimal baseIRRF = salario.subtract(inss)
                .subtract(deducaoDependente.multiply(new BigDecimal(f.getNumDependentes())));
        BigDecimal irrf = BigDecimal.ZERO;

        if (baseIRRF.compareTo(new BigDecimal("2112.00")) <= 0) {
            irrf = BigDecimal.ZERO;
        } else if (baseIRRF.compareTo(new BigDecimal("2826.65")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.075")).subtract(new BigDecimal("158.40"));
        } else if (baseIRRF.compareTo(new BigDecimal("3751.05")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.15")).subtract(new BigDecimal("370.40"));
        } else if (baseIRRF.compareTo(new BigDecimal("4664.68")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.225")).subtract(new BigDecimal("651.73"));
        } else {
            irrf = baseIRRF.multiply(new BigDecimal("0.275")).subtract(new BigDecimal("884.96"));
        }

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        imposto.setIRRF(irrf.setScale(2, RoundingMode.HALF_UP));

        imposto.setDescontoTotal(inss.add(imposto.getFGTS()).add(irrf).setScale(2, RoundingMode.HALF_UP));

        return imposto;
    }
}
