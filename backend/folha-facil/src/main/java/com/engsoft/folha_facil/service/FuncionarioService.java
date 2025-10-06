package com.engsoft.folha_facil.service;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;

import lombok.EqualsAndHashCode.Include;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.util.unit.DataUnit;


public class FuncionarioService {
    
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
