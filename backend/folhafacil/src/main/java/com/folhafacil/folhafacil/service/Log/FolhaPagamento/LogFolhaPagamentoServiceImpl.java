package com.folhafacil.folhafacil.service.Log.FolhaPagamento;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.LogFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.TipoLogFolhaPagamento;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFolhaPagamento;
import com.folhafacil.folhafacil.infra.utils.DataUtils;
import com.folhafacil.folhafacil.repository.Log.FolhaPagamento.LogFolhaPagamentoCustomRepository;
import com.folhafacil.folhafacil.repository.Log.FolhaPagamento.LogFolhaPagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogFolhaPagamentoServiceImpl implements LogFolhaPagamentoService {
    private final LogFolhaPagamentoRepository logFolhaPagamentoRepository;
    private final LogFolhaPagamentoCustomRepository logFolhaPagamentoCustomRepository;

    public LogFolhaPagamentoServiceImpl(
            LogFolhaPagamentoRepository logFolhaPagamentoRepository,
            LogFolhaPagamentoCustomRepository logFolhaPagamentoCustomRepository
    ) {
        this.logFolhaPagamentoRepository = logFolhaPagamentoRepository;
        this.logFolhaPagamentoCustomRepository = logFolhaPagamentoCustomRepository;
    }

    public LogFolhaPagamento gerarLogGeradaAtualizada(String uid, LocalDate data){
        LogFolhaPagamento e = new LogFolhaPagamento();

        Funcionario f = new Funcionario();
        f.setId(uid);
        e.setIdResponsavel(f);

        e.setMensagem("O usuário " + uid + " gerou/ataualizou a folha referente a " + DataUtils.formatarMesAno(data));
        e.setData(LocalDateTime.now());
        e.setTipo(TipoLogFolhaPagamento.GERADA_ATUALIZADA);

        return logFolhaPagamentoRepository.save(e);
    }

    @Override
    public List<LogFolhaPagamentoResponseDTO> buscar(LogFilterDTO filter){
        return logFolhaPagamentoCustomRepository.buscar(filter);
    }
}
