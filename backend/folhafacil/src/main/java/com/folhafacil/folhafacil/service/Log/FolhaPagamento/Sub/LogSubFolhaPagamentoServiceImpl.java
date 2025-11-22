package com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.TipoLogSubFolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.LogFolhaPagamento;
import com.folhafacil.folhafacil.entity.LogSubFolhaPagamento;
import com.folhafacil.folhafacil.repository.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoCustomRepository;
import com.folhafacil.folhafacil.repository.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogSubFolhaPagamentoServiceImpl implements  LogSubFolhaPagamentoService {
    private final LogSubFolhaPagamentoRepository logSubFolhaPagamentoRepository;
    private final LogSubFolhaPagamentoCustomRepository logSubFolhaPagamentoCustomRepository;

    public LogSubFolhaPagamentoServiceImpl(
            LogSubFolhaPagamentoRepository logSubFolhaPagamentoRepository,
            LogSubFolhaPagamentoCustomRepository logSubFolhaPagamentoCustomRepository
    ) {
        this.logSubFolhaPagamentoRepository = logSubFolhaPagamentoRepository;
        this.logSubFolhaPagamentoCustomRepository = logSubFolhaPagamentoCustomRepository;
    }

    @Override
    public List<LogSubFolhaPagamentoResponseDTO> buscar(Long idLogFolhaPagamento){
        return logSubFolhaPagamentoCustomRepository.buscar(idLogFolhaPagamento);
    }

    public LogSubFolhaPagamento gerarLogGerado(Long idLogFP, FolhaPagamento fp) {
        LogSubFolhaPagamento e =  new LogSubFolhaPagamento();

        LogFolhaPagamento lfp = new LogFolhaPagamento();
        lfp.setId(idLogFP);
        e.setIdLogFolhaPagamento(lfp);

        e.setIdFolhaPagamento(fp);
        e.setTotalValorImposto(fp.getTotalValorImposto());
        e.setTotalValorBeneficios(fp.getTotalValorBeneficios());
        e.setTotalHorasExtras(fp.getTotalHorasExtras());
        e.setTotalValorHorasExtras(fp.getTotalValorHorasExtras());
        e.setSalarioBruto(fp.getSalarioBruto());
        e.setMensagem("Folha gerada com sucesso");
        e.setTipo(TipoLogSubFolhaPagamento.GERADO);

        return logSubFolhaPagamentoRepository.save(e);
    }

    public LogSubFolhaPagamento gerarLogAtualizado(Long idLogFP, FolhaPagamento fp){
        LogSubFolhaPagamento e =  new LogSubFolhaPagamento();

        LogFolhaPagamento lfp = new LogFolhaPagamento();
        lfp.setId(idLogFP);
        e.setIdLogFolhaPagamento(lfp);

        e.setIdFolhaPagamento(fp);
        e.setTotalValorImposto(fp.getTotalValorImposto());
        e.setTotalValorBeneficios(fp.getTotalValorBeneficios());
        e.setTotalHorasExtras(fp.getTotalHorasExtras());
        e.setTotalValorHorasExtras(fp.getTotalValorHorasExtras());
        e.setSalarioBruto(fp.getSalarioBruto());
        e.setMensagem("Folha atualizada com sucesso");
        e.setTipo(TipoLogSubFolhaPagamento.ATUALIZADO);

        return logSubFolhaPagamentoRepository.save(e);
    }

    public LogSubFolhaPagamento gerarLogPago(Long idLogFP, FolhaPagamento fp){
        LogSubFolhaPagamento e =  new LogSubFolhaPagamento();

        LogFolhaPagamento lfp = new LogFolhaPagamento();
        lfp.setId(idLogFP);
        e.setIdLogFolhaPagamento(lfp);

        e.setIdFolhaPagamento(fp);
        e.setTotalValorImposto(fp.getTotalValorImposto());
        e.setTotalValorBeneficios(fp.getTotalValorBeneficios());
        e.setTotalHorasExtras(fp.getTotalHorasExtras());
        e.setTotalValorHorasExtras(fp.getTotalValorHorasExtras());
        e.setSalarioBruto(fp.getSalarioBruto());
        e.setMensagem("Folha paga com sucesso");
        e.setTipo(TipoLogSubFolhaPagamento.PAGO);

        return logSubFolhaPagamentoRepository.save(e);
    }

    public LogSubFolhaPagamento gerarLogErro(Long idLogFP, FolhaPagamento fp){
        LogSubFolhaPagamento e =  new LogSubFolhaPagamento();

        LogFolhaPagamento lfp = new LogFolhaPagamento();
        lfp.setId(idLogFP);
        e.setIdLogFolhaPagamento(lfp);

        e.setIdFolhaPagamento(fp);
        e.setTotalValorImposto(fp.getTotalValorImposto());
        e.setTotalValorBeneficios(fp.getTotalValorBeneficios());
        e.setTotalHorasExtras(fp.getTotalHorasExtras());
        e.setTotalValorHorasExtras(fp.getTotalValorHorasExtras());
        e.setSalarioBruto(fp.getSalarioBruto());
        e.setMensagem("Folha já paga");
        e.setTipo(TipoLogSubFolhaPagamento.ERRO);

        return logSubFolhaPagamentoRepository.save(e);
    }
}
