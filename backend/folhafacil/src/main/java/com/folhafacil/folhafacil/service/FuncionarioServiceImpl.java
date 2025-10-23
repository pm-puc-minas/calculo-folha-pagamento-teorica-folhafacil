package com.folhafacil.folhafacil.service;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.mapper.FuncionarioMapper;
import com.folhafacil.folhafacil.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final KeycloakService keycloakService;

    public FuncionarioServiceImpl(
            FuncionarioRepository funcionarioRepository,
            KeycloakService keycloakService
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.keycloakService = keycloakService;
    }

    public void salvar(FuncionarioDTO d) throws RuntimeException {
        try {
            Funcionario f = FuncionarioMapper.toEntity(d);

            if(f.getId() == null){
                String[] nameArr = f.getNome().split(" ");
                String username = nameArr[0]+"."+nameArr[nameArr.length-1];
                String password = "FolhaFacil2025";
                String uid = keycloakService.criarUsuario(username, f.getEmail(), nameArr[0], nameArr[nameArr.length-1], password, f.getCargo());
                f.setId(uid);
            }

            funcionarioRepository.save(f);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
