package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("folha-pagamento")
public class FolhaPagamentoController {
    @Autowired
    private FolhaPagamentoService service;

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_GERAR')")
    @GetMapping(value = "gerar")
    public void gerarFolhaPagamento(@AuthenticationPrincipal Jwt jtw) {
        service.gerarFolhaPagamento(jtw);
    }
}
