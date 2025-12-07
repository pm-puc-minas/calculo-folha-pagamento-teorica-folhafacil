package com.folhafacil.folhafacil.controller;

import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoBeneficioResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoFilterDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoHoraExtraResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_PAGAR')")
    @PostMapping(value = "pagar")
    public void pagarFolhaPagamento(@RequestBody List<Long> ids,@AuthenticationPrincipal Jwt token) throws RuntimeException{
        service.pagarFolhaPagamento(ids, token);
    }

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_EXPORTAR')")
    @PostMapping(value = "exportar")
    public ResponseEntity<byte[]> exportar(
            @RequestBody List<Long> ids,
            @RequestParam String type,
            @RequestParam Boolean horaExtra,
            @RequestParam Boolean beneficios
    ) throws IOException {

        byte[] arquivo = service.exportar(type, horaExtra, beneficios, ids);

        String filename = type.equals("excel") ?
                "folhas_pagamento_" + LocalDate.now() + ".xlsx" :
                "folhas_pagamento_" + LocalDate.now() + ".pdf";

        MediaType contentType = type.equals("excel") ?
                MediaType.APPLICATION_OCTET_STREAM :
                MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .contentType(contentType)
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(arquivo);
    }

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_LISTAR')")
    @PostMapping(value = "buscar")
    public List<FolhaPagamentoResponseDTO> buscar(@RequestBody FolhaPagamentoFilterDTO f){
        return service.buscar(f);
    }

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_BENEFICIOS_LISTAR')")
    @GetMapping(value = "{idFolha}/beneficios")
    public List<FolhaPagamentoBeneficioResponseDTO> buscarBeneficios(@PathVariable Long idFolha){
        return service.buscarBeneficios(idFolha);
    }

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_HORAS_EXTRAS_LISTAR')")
    @GetMapping(value = "{idFolha}/horas-extras")
    public List<FolhaPagamentoHoraExtraResponseDTO> buscarHorasExtras(@PathVariable Long idFolha){
        return service.buscarHorasExtras(idFolha);
    }

    @PreAuthorize("hasRole('FF_FOLHA_PAGAMENTO_MEUS_PAGAMENTOS')")
    @GetMapping(value = "meus-pagamentos")
    public List<FolhaPagamentoResponseDTO> meusPagamentos(@AuthenticationPrincipal Jwt jwt){
        return service.meusBeneficios(jwt);
    }
}
