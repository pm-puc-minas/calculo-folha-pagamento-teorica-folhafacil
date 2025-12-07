package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoBeneficioResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoFilterDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoHoraExtraResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoResponseDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;
import java.util.List;

public interface FolhaPagamentoService {
    public void gerarFolhaPagamento(Jwt token) throws RuntimeException;

    public void pagarFolhaPagamento(List<Long> ids, Jwt token) throws RuntimeException;

    public List<FolhaPagamentoResponseDTO> buscar(FolhaPagamentoFilterDTO f);

    public byte[] exportar(String type, Boolean horasExtras, Boolean beneficios, List<Long> ids) throws IOException ;

    public List<FolhaPagamentoBeneficioResponseDTO> buscarBeneficios(Long idFolha);

    public List<FolhaPagamentoHoraExtraResponseDTO> buscarHorasExtras(Long idFolha);

    public List<FolhaPagamentoResponseDTO> meusBeneficios(Jwt t);
}
