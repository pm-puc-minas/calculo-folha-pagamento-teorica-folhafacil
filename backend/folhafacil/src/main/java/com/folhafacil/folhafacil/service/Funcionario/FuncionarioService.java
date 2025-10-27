package com.folhafacil.folhafacil.service.Funcionario;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface FuncionarioService {
    public void salvar(FuncionarioDTO d, Jwt t) throws RuntimeException;

    public void desabilitar(String uid, Jwt t) throws RuntimeException;

    public void habilitar(String uid, Jwt t) throws RuntimeException;
}
