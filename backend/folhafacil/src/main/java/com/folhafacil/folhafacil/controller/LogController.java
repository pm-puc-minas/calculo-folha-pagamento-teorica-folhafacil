package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.LogFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoService;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoService;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogFuncionarioService logFuncionarioService;

    @Autowired
    private LogFolhaPagamentoService logFolhaPagamentoService;

    @Autowired
    private LogSubFolhaPagamentoService logSubFolhaPagamentoService;


    @PreAuthorize("hasRole('FF_LOG_FUNCIONARIO_LISTAR')")
    @PostMapping(value = "funcionario")
    public List<LogFuncionarioResponseDTO> buscarFuncionario(@RequestBody LogFilterDTO filter){
        return logFuncionarioService.buscar(filter);
    }

    @PreAuthorize("hasRole('FF_LOG_FOLHA_PAGAMENTO_LISTAR')")
    @PostMapping(value = "folha-pagamento")
    public List<LogFolhaPagamentoResponseDTO> buscarFolhaPagamento(@RequestBody LogFilterDTO filter){
        return  logFolhaPagamentoService.buscar(filter);
    }

    @PreAuthorize("hasRole('FF_LOG_SUB_FOLHA_PAGAMENTO_LISTAR')")
    @GetMapping(value = "folha-pagamento/{idLogFolhaPagamento}/sub")
    public List<LogSubFolhaPagamentoResponseDTO> buscarSubFolhaDePagamento(@PathVariable Long idLogFolhaPagamento){
        return logSubFolhaPagamentoService.buscar(idLogFolhaPagamento);
    }
}
