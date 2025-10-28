package com.folhafacil.folhafacil.service.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.Funcionario.TipoFuncionario;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioCustomRepository;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogFuncionarioServiceImpl implements  LogFuncionarioService {
    private final LogFuncionarioRepository logFuncionarioRepository;
    private final LogFuncionarioCustomRepository  logFuncionarioCustomRepository;

    public LogFuncionarioServiceImpl(
            LogFuncionarioRepository logFuncionarioRepository,
            LogFuncionarioCustomRepository logFuncionarioCustomRepository
    ) {
        this.logFuncionarioRepository = logFuncionarioRepository;
        this.logFuncionarioCustomRepository = logFuncionarioCustomRepository;
    }

    public void gerarLogCriado(String idResponsavel, String idManipulado){
        LogFuncionario e = new LogFuncionario();

        Funcionario responsavel = new Funcionario();
        responsavel.setId(idResponsavel);
        e.setIdResponsavel(responsavel);

        Funcionario manipulado = new Funcionario();
        manipulado.setId(idManipulado);
        e.setIdManipulado(manipulado);
        e.setTipo(TipoFuncionario.CRIADO);
        e.setData(LocalDateTime.now());
        e.setMensagem(idResponsavel + " criou o usuário " + idManipulado);

        logFuncionarioRepository.save(e);
    }

    public void gerarLogHabilitado(String idResponsavel, String idManipulado){
        LogFuncionario e = new LogFuncionario();

        Funcionario responsavel = new Funcionario();
        responsavel.setId(idResponsavel);
        e.setIdResponsavel(responsavel);

        Funcionario manipulado = new Funcionario();
        manipulado.setId(idManipulado);
        e.setIdManipulado(manipulado);

        e.setTipo(TipoFuncionario.HABILITADO);
        e.setData(LocalDateTime.now());
        e.setMensagem(idResponsavel + " habilitou o usuário " + idManipulado);

        logFuncionarioRepository.save(e);
    }

    public void gerarLogDesativado(String idResponsavel, String idManipulado){
        LogFuncionario e = new LogFuncionario();

        Funcionario responsavel = new Funcionario();
        responsavel.setId(idResponsavel);
        e.setIdResponsavel(responsavel);

        Funcionario manipulado = new Funcionario();
        manipulado.setId(idManipulado);
        e.setIdManipulado(manipulado);

        e.setTipo(TipoFuncionario.DESABILITADO);
        e.setData(LocalDateTime.now());
        e.setMensagem(idResponsavel + " desabilitou o usuário " + idManipulado);

        logFuncionarioRepository.save(e);
    }

    @Override
    public List<LogFuncionarioResponseDTO> buscar(LogFilterDTO filter){
        return logFuncionarioCustomRepository.buscar(filter);
    }
}
