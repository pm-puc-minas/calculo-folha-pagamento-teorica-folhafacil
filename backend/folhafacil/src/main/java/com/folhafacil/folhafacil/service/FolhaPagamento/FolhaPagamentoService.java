package com.folhafacil.folhafacil.service.FolhaPagamento;

import org.springframework.security.oauth2.jwt.Jwt;

public interface FolhaPagamentoService {
    public void gerarFolhaPagamento(Jwt token) throws RuntimeException;
}
