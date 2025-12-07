package com.folhafacil.folhafacil.service.HoraExtra;

import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraFilterDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraReponseDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.StatusHoraExtra;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.HoraExtra;
import com.folhafacil.folhafacil.repository.HoraExtra.HoraExtraCustomRepository;
import com.folhafacil.folhafacil.repository.HoraExtra.HoraExtraRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.ServiceGenerico;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HoraExtraServiceImpl extends ServiceGenerico<HoraExtra, Long> implements HoraExtraService {

    private final HoraExtraRepository horaExtraRepository;
    private final KeycloakService keycloakService;
    private final HoraExtraCustomRepository horaExtraCustomRepository;

    public HoraExtraServiceImpl(
            HoraExtraRepository horaExtraRepository,
            KeycloakService keycloakService,
            HoraExtraCustomRepository horaExtraCustomRepository
    ) {
        super(horaExtraRepository);
        this.horaExtraRepository = horaExtraRepository;
        this.keycloakService = keycloakService;
        this.horaExtraCustomRepository = horaExtraCustomRepository;
    }

    @Override
    public void iniciar(HoraExtraDTO d, Jwt token) throws RuntimeException {
        try {
            String uid = keycloakService.recuperarUID(token);
            HoraExtra e = new HoraExtra();

            Funcionario f = new Funcionario();
            f.setId(uid);
            e.setIdFuncionario(f);

            e.setDataInicio(LocalDateTime.now());
            e.setDescricao(d.getDescricao());
            e.setStatus(StatusHoraExtra.EM_ANDAMENTO);

            horaExtraRepository.save(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void finalizar(Long id) throws RuntimeException {
        try {
            HoraExtra e = horaExtraRepository.findById(id).orElseThrow();
            e.setDataFim(LocalDateTime.now());
            e.setStatus(StatusHoraExtra.CONCLUIDA);
            horaExtraRepository.save(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void cancelar(Long id) throws RuntimeException {
        try {
            HoraExtra e = horaExtraRepository.findById(id).orElseThrow();
            e.setStatus(StatusHoraExtra.CANCELADA);
            horaExtraRepository.save(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<HoraExtraReponseDTO> buscar(HoraExtraFilterDTO f){
        return horaExtraCustomRepository.buscar(f);
    }

    @Override
    public List<HoraExtraReponseDTO> minhasHoras(Jwt jwt) {
        String uid = keycloakService.recuperarUID(jwt);
        HoraExtraFilterDTO f = new HoraExtraFilterDTO();
        f.setIdFuncionario(uid);
        return horaExtraCustomRepository.buscar(f);
    }

    public BigDecimal totalHorasNoMes(String uid, LocalDate data) {
        List<HoraExtra> hes = findByFuncionarioAndMesAno(uid, data);

        double total = hes.stream()
                .filter(h -> h.getDataFim() != null)
                .mapToDouble(h -> Duration.between(h.getDataInicio(), h.getDataFim()).toMinutes() / 60.0)
                .sum();

        return BigDecimal.valueOf(total);
    }

    public List<HoraExtra> findByFuncionarioAndMesAno(String uid, LocalDate data) {
        return horaExtraRepository.findByFuncionarioAndMesAno(uid, data.getMonthValue(), data.getYear());
    }
}
