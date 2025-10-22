package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.service.KeycloakService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController {
    private final KeycloakService keycloakService;

    public FuncionarioController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }
    @PostMapping(value ="")
    public void salvar( @RequestParam String username,
                        @RequestParam String email,
                        @RequestParam String primeiroNome,
                        @RequestParam String ultimoNome,
                        @RequestParam String senha,
                        @RequestParam String grupo) {
        keycloakService.criarUsuario(username, email, primeiroNome, ultimoNome, senha, grupo);
    }
}
