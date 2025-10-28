package com.folhafacil.folhafacil.service.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;

import java.util.List;

public interface LogFuncionarioService {

    public List<LogFuncionarioResponseDTO> buscar(LogFilterDTO filter);
}
