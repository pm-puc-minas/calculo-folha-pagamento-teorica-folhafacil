package com.folhafacil.folhafacil.service.Beneficio;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.mapper.BeneficioMapper;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioCustomRepository;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioRepository;
import com.folhafacil.folhafacil.service.ServiceGenerico;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BeneficoServiceImpl extends ServiceGenerico<Beneficio, Long> implements BeneficioService {
    private final BeneficioRepository beneficioRepository;
    private final BeneficioCustomRepository beneficioCustomRepository;

    public BeneficoServiceImpl(
            BeneficioRepository beneficioRepository,
            BeneficioCustomRepository beneficioCustomRepository
    ) {
        super(beneficioRepository);
        this.beneficioRepository = beneficioRepository;
        this.beneficioCustomRepository = beneficioCustomRepository;
    }

    @Override
    public void salvar(BeneficioDTO d) throws RuntimeException{
        try{
            Beneficio b = BeneficioMapper.toEntity(d);
            beneficioRepository.save(b);
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<BeneficioResponseDTO> buscar(){
        return beneficioCustomRepository.buscar();
    }

    @Override
    public void deletar(Long id) throws RuntimeException{
        try {
         beneficioRepository.deleteById(id);
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public Set<String> listarNomesUnicos() {
        List<Beneficio> beneficios = beneficioRepository.findAll();
        Set<String> nomes = new HashSet<>();
        for (Beneficio b : beneficios) {
            nomes.add(b.getNome());
        }
        return nomes;
    }
    
    public Map<String, List<Beneficio>> agruparPorNome() {
        List<Beneficio> beneficios = beneficioRepository.findAll();
        Map<String, List<Beneficio>> mapa = new HashMap<>();
        for (Beneficio b : beneficios) {
            mapa.computeIfAbsent(b.getNome(), k -> new ArrayList<>()).add(b);
        }
        return mapa;
    }
    
    public List<Beneficio> filtrarPorNomeMinimo(String prefixo) {
        List<Beneficio> beneficios = beneficioRepository.findAll();
        return beneficios.stream()
                .filter(b -> b.getNome().startsWith(prefixo))
                .toList();
    }
}
