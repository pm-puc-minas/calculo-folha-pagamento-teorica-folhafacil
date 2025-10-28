package com.folhafacil.folhafacil.service.FolhaPagamento;

public class FolhaPagamentoServiceImpl {
    /*public FolhaPagamento gerarFolha(Funcionario funcionario, int mes, int ano) {
        // Implemente a lógica de geração da folha conforme necessário
        // Exemplo básico:
        FolhaPagamento folha = new FolhaPagamento();
        folha.setFuncionario(funcionario);
        // Defina outros campos conforme sua lógica de negócio
        return folha;
    }


    public FolhaPagamento consuFolhaPagamento(FolhaPagamento fp){
        return FolhaPagamentoRepository.findAll().stream().filter(f -> f.equals(fp)).findFirst().orElse(null);
    }

    public double consultarDescontos(FolhaPagamento fp){
        if(fp == null || fp.getFuncionario() == null){
            return 0.0;
        }
        Imposto imposto = impostoService.calcularImpostos(fp.getFuncionario().getCpf());
        return imposto.getDescontoTotal();
    }

    //Calcula e retorna o salario liquido
    public double consultarSalarioLiquido(FolhaPagamento fp){
        if(fp == null || fp.getFuncionario() == null){
            return 0.0;
        }

        Imposto imposto = impostoService.calcularImpostos(fp.getFuncionario().getCpf());

        double salarioBruto = fp.getSalarioBruto();

        double descontoFaltas = (salarioBruto / 30)*fp.getDiasFaltados();

        double valorHora = salarioBruto/220.0;
        double adicionarHoraExtra = valorHora * 1.5 * fp.getHoraExtra();

        double totalBeneficios = fp.getBeneficios() == null ? 0.0 :
                fp.getBeneficios().stream()
                        .filter(b -> b.getTipo() != BeneficioTipo.VALE_TRANSPORTE && b.getTipo() != BeneficioTipo.VALE_ALIMENTACAO)
                        .mapToDouble(Beneficio::getValor)
                        .sum();

        //calcula salario final
        double salarioLiquido = salarioBruto - imposto.getDescontoTotal() - descontoFaltas + adicionarHoraExtra + totalBeneficios;

        fp.setSalarioLiquido(salarioLiquido);
        fp.setImposto(imposto);

        return salarioLiquido;
    }

    public List<FolhaPagamento> consultarHistoricoFuncionario(Funcionario funcionario){
        return FolhaPagamentoRepository.findByFuncionario(funcionario);
    }

    public List<FolhaPagamento> consultarPorCPF(String cpf){
        return FolhaPagamentoRepository.findByCpf(cpf);
    }

    public List<FolhaPagamento> consutarPorPeriodo(Date inicio, Date fim){
        return FolhaPagamentoRepository.findByPeriodo(inicio, fim);
    }

    public void removerFolha(long id){
        FolhaPagamentoRepository.delete(id);
    }*/
}
