package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.model.*;

import lombok.EqualsAndHashCode.Include;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.*;

import org.springframework.util.unit.DataUnit;


public class FuncionarioService {
    

    private final FuncionarioRepository funcionarioRepository = new FuncionarioRepository();

    // Cadastrar novo funcionário
    private Funcionario criarFuncionario(String nome, String cpf, String cargo,
                                    String telefone, String rua, String bairro,
                                    int numero, String email,
                                    Date dataNascimento, Date dataAdmissao,
                                    double salarioBase, int horasSemanais,
                                    double valorValeTransporte,
                                    List<Beneficio> planoBeneficios,
                                    int numDependentes, double pensaoAlimenticia){

        Funcionario existente = funcionarioRepository.findByCpf(cpf);
             if (existente != null) {
             throw new IllegalArgumentException("Já existe um funcionário cadastrado com este CPF: " + cpf);
        }

        Funcionario f = new Funcionario(nome, cpf, cargo, telefone, rua, bairro, numero, email,
                                    dataNascimento, dataAdmissao, salarioBase, horasSemanais,
                                    valorValeTransporte, 0.0, planoBeneficios,
                                    numDependentes, pensaoAlimenticia);

        funcionarioRepository.save(f);
        return f;
    }

    // Alterar funcionário   
    public void alterarFuncionario(Funcionario fAtualizado) {
        if (fAtualizado != null && fAtualizado.getId() != null) {
        funcionarioRepository.save(fAtualizado);
        }
    }

    // Excluir funcionário
    public void excluirFuncionario(Long id) {
        funcionarioRepository.delete(id); 
    }

    //Listar todos
    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll(); 
    }

    // Buscar por ID
    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    // Buscar por CPF
    public Funcionario busFuncionarioporCpf(String id){
        return funcionarioRepository.findByCpf(id);
    }

    public int contarDiasUteis(int mes, int ano, boolean incluirSabado){
        YearMonth month = YearMonth.of(ano, mes);
        if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mês inválido (1-12)");
        int diasUteis = 0;

        for(int dia = 1; dia <= month.lengthOfMonth();dia++){
            LocalDate data = LocalDate.of(ano, mes, dia);
            DayOfWeek diaSemana = data.getDayOfWeek();

            if(diaSemana == DayOfWeek.SUNDAY) continue;
            if(!incluirSabado && diaSemana == DayOfWeek.SATURDAY) continue;

            diasUteis ++;
        }
        return diasUteis;
    }
}
