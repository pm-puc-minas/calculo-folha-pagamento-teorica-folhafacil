package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.FolhaPagamento;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FolhaPagamentoRepository;
import java.util.List;
import java.util.Date;

public class FolhaPagamentoService {

    private FolhaPagamentoRepository repository;

    public FolhaPagamentoService(FolhaPagamentoRepository repo){
        this.repository = repo;
    }

    public FolhaPagamento consuFolhaPagamento(FolhaPagamento fp){
        return repository.listarTodas().stream().filter(f -> f.equals(fp)).findFirst().orElse(null);
    }
    
    public double consultarDescontos(FolhaPagamento fp){
        return fp != null ? fp.calcularDescontos() : 0.0;
    }

    public double consultarSalarioLiquido(FolhaPagamento fp){
        return fp != null ? fp.calcularSalarioLiquido() : 0.0;
    }

    public void registrarFolha(FolhaPagamento fp){
        if(fp != null){
            fp.registrarPagamento();
            repository.salvar(fp);
        }
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
