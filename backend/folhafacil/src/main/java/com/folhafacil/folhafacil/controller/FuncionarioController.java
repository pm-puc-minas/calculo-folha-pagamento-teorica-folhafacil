package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;


    @PreAuthorize("hasRole('FF_FUNCIONARIO_SALVAR')")
    @PostMapping(value ={"",  "editar"})
    public void salvar(@RequestBody FuncionarioDTO d, @AuthenticationPrincipal Jwt token) {
       funcionarioService.salvar(d, token);
    }

    @PreAuthorize("hasRole('FF_FUNCIONARIO_HABILITAR')")
    @GetMapping(value = "/{uid}/habilitar")
    public void habilitar(@PathVariable String uid, @AuthenticationPrincipal Jwt token) {
        funcionarioService.habilitar(uid, token);
    }

    @PreAuthorize("hasRole('FF_FUNCIONARIO_DESABILITAR')")
    @GetMapping(value = "/{uid}/desabilitar")
    public void desabilitar(@PathVariable String uid, @AuthenticationPrincipal Jwt token) {
        funcionarioService.desabilitar(uid, token);
    }
}
