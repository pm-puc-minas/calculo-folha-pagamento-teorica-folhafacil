package com.engsoft.folha_facil.service;

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
        return repository.listarTodas().stream().filter(f -> f.equals(fp)).findFirst().orElse(null);
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

        double totalBenficios = 0.0;
        if(fp.getBeneficios() != null){
            for(int i = 0; i < fp.getBeneficios().size(); i++){
                totalBenficios += fp.getBeneficios().get(i).getValor();
            }
        }

        //calcula salario final
        double salarioLiquido = salarioBruto - imposto.getDescontoTotal() - descontoFaltas
    }

    public List<FolhaPagamento> consultarHistoricoFuncionario(Funcionario funcionario){
        return repository.buscarPorFuncionario(funcionario);
    }

    public List<FolhaPagamento> consultarPorCPF(String cpf){
        return repository.buscarPorCPF(cpf);
    }

    public List<FolhaPagamento> consutarPorPeriodo(Date inicio, Date fim){
        return repository.buscarPorPeriodo(inicio ,fim);
    }

    public boolean removerFolha(FolhaPagamento fp){
        return repository.remover(fp);
    }

}
