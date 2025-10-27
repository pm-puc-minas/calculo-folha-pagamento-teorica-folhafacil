package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogFuncionarioService logFuncionarioService;


    @PreAuthorize("hasRole('FF_LOG_FUNCIONARIO_LISTAR')")
    @GetMapping(value = "funcionario")
    public List<LogFuncionarioResponseDTO> buscarFuncionario(){
        return logFuncionarioService.buscar();
    }
}
