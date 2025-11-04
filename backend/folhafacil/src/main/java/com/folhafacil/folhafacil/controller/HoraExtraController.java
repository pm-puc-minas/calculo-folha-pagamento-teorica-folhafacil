package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraFilterDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraReponseDTO;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hora-extra")
public class HoraExtraController {
    @Autowired
    private HoraExtraService service;

    @PreAuthorize("hasRole('FF_HORA_EXTRA_INICIAR')")
    @PostMapping(value = "iniciar")
    public void  iniciar(@RequestBody HoraExtraDTO d, @AuthenticationPrincipal Jwt jwt){
        service.iniciar(d,jwt);
    }

    @PreAuthorize("hasRole('FF_HORA_EXTRA_FINALIZAR')")
    @GetMapping(value = "{id}/finalizar")
    public void finalizar(@PathVariable Long id){
        service.finalizar(id);
    }

    @PreAuthorize("hasRole('FF_HORA_EXTRA_CANCELAR')")
    @GetMapping(value = "{id}/cancelar")
    public void cancelar(@PathVariable Long id){
        service.cancelar(id);
    }

    @PreAuthorize("hasRole('FF_MINHA_HORA_EXTRA_LISTAR')")
    @GetMapping(value = "minhas")
    public List<HoraExtraReponseDTO> listarMinhas(@AuthenticationPrincipal Jwt jwt){
        return service.minhasHoras(jwt);
    }
}
