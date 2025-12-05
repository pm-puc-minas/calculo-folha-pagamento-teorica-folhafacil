package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.service.Beneficio.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("beneficio")
public class BeneficioController {
    @Autowired
    private BeneficioService service;

    @PreAuthorize("hasRole('FF_BENEFICIO_SALVAR')")
    @PostMapping(value = {"", "altera"})
    public void salvar(@RequestBody BeneficioDTO d)throws RuntimeException{
        service.salvar(d);
    }

    @PreAuthorize("hasRole('FF_BENEFICIO_LISTAR')")
    @GetMapping(value = "buscar")
    public List<BeneficioResponseDTO> buscar(){
        return service.buscar();
    }

    @PreAuthorize("hasRole('FF_BENEFICIO_LISTAR')")
    @GetMapping(value = "{id}/funcionarios")
    public List<BeneficioFuncionarioResponseDTO> buscarFuncionarios(@PathVariable Long id) {
        return service.buscarFuncionarios(id);
    }

    @PreAuthorize("hasRole('FF_BENEFICIO_DELETAR')")
    @DeleteMapping(value = "{id}/excluir")
    public void excluir(@PathVariable Long id)throws RuntimeException{
        service.deletar(id);
    }
}
