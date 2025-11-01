package com.folhafacil.folhafacil.service.Beneficio;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;

import java.util.List;

public interface BeneficioService {
    public void salvar(BeneficioDTO d)throws RuntimeException;

    public List<BeneficioResponseDTO> buscar();

    public void deletar(Long id)throws RuntimeException;
}
