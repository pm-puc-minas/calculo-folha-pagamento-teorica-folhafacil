package com.folhafacil.folhafacil.service.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;

import java.util.List;

public interface LogFuncionarioService {

    void gerarLogCriado(String idResponsavel, String idManipulado);
    void gerarLogEditado(String idResponsavel, String idManipulado);
    void gerarLogHabilitado(String idResponsavel, String idManipulado);
    void gerarLogDesativado(String idResponsavel, String idManipulado);

    public List<LogFuncionarioResponseDTO> buscar(LogFilterDTO filter);
}
