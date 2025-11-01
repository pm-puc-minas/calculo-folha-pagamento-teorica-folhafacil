package com.folhafacil.folhafacil.service.Funcionario;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.mapper.FuncionarioBeneficioMapper;
import com.folhafacil.folhafacil.mapper.FuncionarioMapper;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioServiceImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final KeycloakService keycloakService;
    private final LogFuncionarioServiceImpl logFuncionarioServiceImpl;

    public FuncionarioServiceImpl(
            FuncionarioRepository funcionarioRepository,
            KeycloakService keycloakService,
            LogFuncionarioServiceImpl logFuncionarioServiceImpl
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.keycloakService = keycloakService;
        this.logFuncionarioServiceImpl = logFuncionarioServiceImpl;
    }

    @Override
    public void salvar(FuncionarioDTO d, Jwt t) throws RuntimeException {
        try {
            if(d.getId() == null){
                String[] nameArr = d.getNome().split(" ");
                String username = nameArr[0]+"."+nameArr[nameArr.length-1];
                String password = "FolhaFacil2025";
                String uid = keycloakService.criarUsuario(username, d.getEmail(), nameArr[0], nameArr[nameArr.length-1], password, d.getCargo());
                d.setUsuario(username);
                d.setId(uid);
                d.setStatus(Funcionario.HABILITADO);
                funcionarioRepository.save(FuncionarioMapper.toEntity(d, FuncionarioBeneficioMapper.toEntityList(d.getBeneficios(), d.getId())));
                logFuncionarioServiceImpl.gerarLogCriado(keycloakService.recuperarUID(t), d.getId());
            }else{
                funcionarioRepository.save(FuncionarioMapper.toEntity(d, FuncionarioBeneficioMapper.toEntityList(d.getBeneficios(), d.getId())));
                logFuncionarioServiceImpl.gerarLogEditado(keycloakService.recuperarUID(t), d.getId());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void habilitar(String uid, Jwt t) throws RuntimeException {
        try {
            Funcionario f = funcionarioRepository.findById(uid).get();

            if(f.getStatus()){
                throw new RuntimeException("Conta já habilitada");
            }

            f.setStatus(true);
            funcionarioRepository.save(f);

            logFuncionarioServiceImpl.gerarLogHabilitado(keycloakService.recuperarUID(t), f.getId());
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void desabilitar(String uid, Jwt t) throws RuntimeException {
        try {
            Funcionario f = funcionarioRepository.findById(uid).get();

            if(!f.getStatus()) {
                throw new RuntimeException("Conta ja desabilitada");
            }

            f.setStatus(false);
            funcionarioRepository.save(f);

            logFuncionarioServiceImpl.gerarLogDesativado(keycloakService.recuperarUID(t), f.getId());
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Funcionario findById(String id) {
        return funcionarioRepository.findById(id).get();
    }

    public Funcionario findByCpf(String id){
        return funcionarioRepository.findByCpf(id);
    }

    public int contarDiasUteis(int mes, int ano, boolean incluirSabado){
        YearMonth month = YearMonth.of(ano, mes);
        if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mês inválido (1-12)");
        int diasUteis = 0;

        for(int dia = 1; dia <= month.lengthOfMonth();dia++){
            LocalDate data = LocalDate.of(ano, mes, dia);
            DayOfWeek diaSemana = data.getDayOfWeek();

            if(diaSemana == DayOfWeek.SUNDAY) continue;
            if(!incluirSabado && diaSemana == DayOfWeek.SATURDAY) continue;

            diasUteis ++;
        }
        return diasUteis;
    }
}
