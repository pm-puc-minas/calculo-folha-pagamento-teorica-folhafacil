package com.folhafacil.folhafacil.service.Log.FolhaPagamento;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.LogFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;

import java.util.List;

public interface LogFolhaPagamentoService {
    public List<LogFolhaPagamentoResponseDTO> buscar(LogFilterDTO filter);
}
