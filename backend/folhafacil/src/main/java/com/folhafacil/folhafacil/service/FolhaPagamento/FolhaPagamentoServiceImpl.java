package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FolhaPagamentoServiceImpl implements FolhaPagamentoService {

    private final FuncionarioServiceImpl funcionarioServiceImpl;
    private final HoraExtraServiceImpl horaExtraServiceImpl;

    public FolhaPagamentoServiceImpl(
            FuncionarioServiceImpl funcionarioServiceImpl,
            HoraExtraServiceImpl horaExtraServiceImpl
    ) {
        this.funcionarioServiceImpl = funcionarioServiceImpl;
        this.horaExtraServiceImpl = horaExtraServiceImpl;
    }

    public void gerarPorFuncionario(Funcionario f, LocalDate data, Long idLog) throws RuntimeException {
        try{
            FolhaPagamento e = new FolhaPagamento();

            e.setIdFuncionario(f);
            e.setStatus(StatusFolhaPagamento.PENDENTE);
            e.setData(data);

            BigDecimal inss = funcionarioServiceImpl.getINSS(f);
            e.setINSS(inss);

            BigDecimal fgst = funcionarioServiceImpl.getFGST(f);
            e.setFGTS(fgst);

            BigDecimal irrf = funcionarioServiceImpl.getIRRF(f);
            e.setIRRF(irrf);

            BigDecimal totalValorImposto = inss.add(fgst).add(irrf);
            e.setTotalValorImposto(totalValorImposto);

            BigDecimal totalValorBeneficios = funcionarioServiceImpl.getTotalValorBeneficios(f);
            e.setTotalValorBeneficios(totalValorBeneficios);

            BigDecimal totalHorasExtras = BigDecimal.ZERO;
            BigDecimal totalValorHorasExtras = BigDecimal.ZERO;

            if(!f.getCargo().equals("ESTAGIARIO")){
                totalHorasExtras = horaExtraServiceImpl.totalHorasNoMes(f.getId(), data);

                if(!(totalHorasExtras.compareTo(BigDecimal.ZERO) <= 0)){
                    totalValorHorasExtras = totalHorasExtras.multiply(funcionarioServiceImpl.valorHoraExtra(f));
                }
            }

            e.setTotalHorasExtras(totalHorasExtras);
            e.setTotalValorHorasExtras(totalValorHorasExtras);

            e.setSalarioBruto(f.getSalarioBase());

            e.setTotal(f.getSalarioBase().add(totalValorHorasExtras).add(totalValorBeneficios).subtract(totalValorImposto));
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
