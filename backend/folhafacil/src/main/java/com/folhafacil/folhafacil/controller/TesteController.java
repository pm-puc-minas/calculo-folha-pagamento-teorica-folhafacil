package com.folhafacil.folhafacil.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Endpoint público, sem autenticação";
    }

    @PreAuthorize("hasRole('FF_FOLHA_SALARIAL_LISTAR')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Somente usuários com ROLE_admin podem acessar";
    }

    @PreAuthorize("hasRole('FF_HORA_EXTRA_LISTAR')")
    @GetMapping("/user")
    public String userEndpoint() {
        return "Somente usuários com ROLE_user podem acessar";
    }
}
