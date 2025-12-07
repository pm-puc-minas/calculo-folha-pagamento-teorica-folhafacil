package com.folhafacil.folhafacil.service.HoraExtra;

import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraFilterDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraReponseDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface HoraExtraService {
    public void iniciar(HoraExtraDTO d, Jwt token) throws RuntimeException;

    public void finalizar(Long id) throws RuntimeException;

    public void cancelar(Long id) throws RuntimeException;

    public List<HoraExtraReponseDTO> buscar(HoraExtraFilterDTO f);

    public List<HoraExtraReponseDTO> minhasHoras(Jwt jwt);
}
