package com.engsoft.folha_facil.repository;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.FolhaPagamento;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
public class FolhaPagamentoRepository {
    private List<FolhaPagamento> folhas = new ArrayList<>();
    
    public void salvar(FolhaPagamento folha){
        folhas.add(folha);
    }

    public List<FolhaPagamento> listarTodas(){
        return new ArrayList<>(folhas);
    }

    public List<FolhaPagamento> buscarPorFuncionario(Funcionario funcionario){
        return folhas.stream().filter(f -> f.getFuncionario().equals(funcionario)).collect(Collectors.toList());
    }

    public List<FolhaPagamento> buscarPorCPF(String cpf){
        return folhas.stream().filter(f -> f.getFuncionario() != null && f.getFuncionario().getCpf().equals(cpf)).collect(Collectors.toList());
    }

    public List<FolhaPagamento> buscarPorPeriodo(Date inicio, Date fim){
        return folhas.stream().filter(f -> f.getDataPagamento() != null && !f.getDataPagamento().before(inicio) && !f.getDataPagamento().before(fim)).collect(Collectors.toList());
    }

    public boolean remover(FolhaPagamento folha){
        return folhas.remove(folha);
    }
}