package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.BeneficioTipo;
import com.engsoft.folha_facil.model.FolhaPagamento;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.FolhaPagamentoRepository;
import java.util.List;
import java.util.Date;

public class FolhaPagamentoService {

    private FolhaPagamentoRepository repository;
    private ImpostoService impostoService;

    public FolhaPagamentoService(FolhaPagamentoRepository repo){
        this.repository = repo;
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
    }
}
