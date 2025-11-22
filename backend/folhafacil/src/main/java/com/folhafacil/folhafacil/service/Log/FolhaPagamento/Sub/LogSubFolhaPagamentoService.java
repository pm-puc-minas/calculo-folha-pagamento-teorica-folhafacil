package com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoResponseDTO;

import java.util.List;

public interface LogSubFolhaPagamentoService {
    public List<LogSubFolhaPagamentoResponseDTO> buscar(Long idLogFolhaPagamento);
}
