package com.folhafacil.folhafacil.service.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.Funcionario.TipoLogFuncionario;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioCustomRepository;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogFuncionarioServiceImpl implements LogFuncionarioService {
    private final LogFuncionarioRepository logFuncionarioRepository;
    private final LogFuncionarioCustomRepository logFuncionarioCustomRepository;
    private final FuncionarioRepository funcionarioRepository; // Injeção do Repositório de Funcionário

    public LogFuncionarioServiceImpl(
            LogFuncionarioRepository logFuncionarioRepository,
            LogFuncionarioCustomRepository logFuncionarioCustomRepository,
            FuncionarioRepository funcionarioRepository // Adicionado aqui
    ) {
        this.logFuncionarioRepository = logFuncionarioRepository;
        this.logFuncionarioCustomRepository = logFuncionarioCustomRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    private void salvarLog(String idResponsavel, String idManipulado, TipoLogFuncionario tipo, String acaoDescricao) {
        try {
            LogFuncionario e = new LogFuncionario();

            // --- CORREÇÃO DEFINITIVA: BUSCAR O REAL NO BANCO ---
            String idParaSalvar;
            String nomeResponsavelTexto;

            // 1. Define qual ID usar (SISTEMA -> usa o próprio manipulado)
            if (idResponsavel == null || idResponsavel.equals("SISTEMA") || idResponsavel.isBlank()) {
                idParaSalvar = idManipulado;
                nomeResponsavelTexto = "SISTEMA";
            } else {
                idParaSalvar = idResponsavel;
                nomeResponsavelTexto = idResponsavel;
            }

            // 2. Busca o objeto real no banco (Isso evita o erro NULL)
            // Como acabamos de salvar o funcionário no serviço principal, ele COM CERTEZA existe.
            Optional<Funcionario> responsavelOpt = funcionarioRepository.findById(idParaSalvar);
            
            if (responsavelOpt.isPresent()) {
                e.setIdResponsavel(responsavelOpt.get());
                
                // Configura o resto do Log
                // Para o manipulado, podemos usar o mesmo objeto se for o mesmo ID, ou buscar novamente
                if (idParaSalvar.equals(idManipulado)) {
                    e.setIdManipulado(responsavelOpt.get());
                } else {
                    Optional<Funcionario> manipuladoOpt = funcionarioRepository.findById(idManipulado);
                    manipuladoOpt.ifPresent(e::setIdManipulado);
                }

                e.setTipo(tipo);
                e.setData(LocalDateTime.now());
                e.setMensagem(nomeResponsavelTexto + " " + acaoDescricao + " o usuário " + idManipulado);

                logFuncionarioRepository.save(e);
            }
        } catch (Exception ex) {
            // Se o Log falhar por qualquer motivo bizarro, apenas imprime no console e NÃO trava o cadastro
            System.err.println("Erro ao salvar Log (ignorado para não travar fluxo): " + ex.getMessage());
        }
    }

    @Override
    public void gerarLogCriado(String idResponsavel, String idManipulado){
        salvarLog(idResponsavel, idManipulado, TipoLogFuncionario.CRIADO, "criou");
    }

    @Override
    public void gerarLogEditado(String idResponsavel, String idManipulado){
        salvarLog(idResponsavel, idManipulado, TipoLogFuncionario.ALTERADO, "editou");
    }

    @Override
    public void gerarLogHabilitado(String idResponsavel, String idManipulado){
        salvarLog(idResponsavel, idManipulado, TipoLogFuncionario.HABILITADO, "habilitou");
    }

    @Override
    public void gerarLogDesativado(String idResponsavel, String idManipulado){
        salvarLog(idResponsavel, idManipulado, TipoLogFuncionario.DESABILITADO, "desabilitou");
    }

    @Override
    public List<LogFuncionarioResponseDTO> buscar(LogFilterDTO filter){
        return logFuncionarioCustomRepository.buscar(filter);
    }
}