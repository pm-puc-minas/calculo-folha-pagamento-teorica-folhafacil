package com.folhafacil.folhafacil.service.Funcionario;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.mapper.FuncionarioBeneficioMapper;
import com.folhafacil.folhafacil.mapper.FuncionarioMapper;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.ServiceGenerico;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioServiceImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioServiceImpl extends ServiceGenerico<Funcionario, String> implements FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final KeycloakService keycloakService;
    private final LogFuncionarioServiceImpl logFuncionarioServiceImpl;

    public FuncionarioServiceImpl(
            FuncionarioRepository funcionarioRepository,
            KeycloakService keycloakService,
            LogFuncionarioServiceImpl logFuncionarioServiceImpl
    ) {
        super(funcionarioRepository);
        this.funcionarioRepository = funcionarioRepository;
        this.keycloakService = keycloakService;
        this.logFuncionarioServiceImpl = logFuncionarioServiceImpl;
    }

    @Override
    public void salvar(FuncionarioDTO d, Jwt t) throws RuntimeException {
        try {
            Funcionario e = FuncionarioMapper.toEntity(
                    d,
                    FuncionarioBeneficioMapper.toEntityList(d.getBeneficios(), d.getId())
            );

            if (d.getId() == null) {
                String[] nameArr = d.getNome().split(" ");
                String username = nameArr[0] + "." + nameArr[nameArr.length - 1];
                String password = "FolhaFacil2025";
                String uid = keycloakService.criarUsuario(username, d.getEmail(), nameArr[0], nameArr[nameArr.length - 1], password, d.getCargo());
                e.setUsuario(username);
                e.setId(uid);
                e.setStatus(Funcionario.HABILITADO);

                funcionarioRepository.save(e);
                logFuncionarioServiceImpl.gerarLogCriado(keycloakService.recuperarUID(t), e.getId());
            } else {
                funcionarioRepository.save(e);
                logFuncionarioServiceImpl.gerarLogEditado(keycloakService.recuperarUID(t), e.getId());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void habilitar(String uid, Jwt t) throws RuntimeException {
        Funcionario f = funcionarioRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (f.getStatus()) {
            throw new RuntimeException("Conta já habilitada");
        }

        f.setStatus(Funcionario.HABILITADO);
        funcionarioRepository.save(f);
        logFuncionarioServiceImpl.gerarLogHabilitado(keycloakService.recuperarUID(t), f.getId());
    }

    @Override
    public void desabilitar(String uid, Jwt t) throws RuntimeException {
        Funcionario f = funcionarioRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (!f.getStatus()) {
            throw new RuntimeException("Conta já desabilitada");
        }

        f.setStatus(Funcionario.DESABILITADO);
        funcionarioRepository.save(f);
        logFuncionarioServiceImpl.gerarLogDesativado(keycloakService.recuperarUID(t), f.getId());
    }

    public List<Funcionario> findByStatus(Boolean status) {
        return funcionarioRepository.findByStatus(status);
    }

    public List<Funcionario> findHabilitados() {
        return findByStatus(Funcionario.HABILITADO);
    }

    public List<Funcionario> findDesabilitados() {
        return findByStatus(Funcionario.DESABILITADO);
    }

    public BigDecimal getTotalValorBeneficios(Funcionario f) {
        if (f.getBeneficios().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalValorBeneficios = BigDecimal.ZERO;
        for (FuncionarioBeneficio b : f.getBeneficios()) {
            totalValorBeneficios = totalValorBeneficios.add(b.getValor());
        }

        return totalValorBeneficios;
    }

    public BigDecimal calcularValorHoraExtra(Funcionario f) {
        BigDecimal totalHoras = new BigDecimal(f.getHorasDiarias() * f.getDiasMensal());
        return f.getSalarioBase().divide(totalHoras, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getINSS(Funcionario f) {
        BigDecimal salario = f.getSalarioBase();

        BigDecimal faixa1 = new BigDecimal("1412.00");
        BigDecimal faixa2 = new BigDecimal("2666.68");
        BigDecimal faixa3 = new BigDecimal("4000.03");
        BigDecimal faixa4 = new BigDecimal("7786.02");

        BigDecimal aliquota1 = new BigDecimal("0.075");
        BigDecimal aliquota2 = new BigDecimal("0.09");
        BigDecimal aliquota3 = new BigDecimal("0.12");
        BigDecimal aliquota4 = new BigDecimal("0.14");

        BigDecimal inss;
        if (salario.compareTo(faixa1) <= 0) {
            inss = salario.multiply(aliquota1);
        } else if (salario.compareTo(faixa2) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((salario.subtract(faixa1)).multiply(aliquota2));
        } else if (salario.compareTo(faixa3) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((salario.subtract(faixa2)).multiply(aliquota3));
        } else if (salario.compareTo(faixa4) <= 0) {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((faixa3.subtract(faixa2)).multiply(aliquota3))
                    .add((salario.subtract(faixa3)).multiply(aliquota4));
        } else {
            inss = faixa1.multiply(aliquota1)
                    .add((faixa2.subtract(faixa1)).multiply(aliquota2))
                    .add((faixa3.subtract(faixa2)).multiply(aliquota3))
                    .add((faixa4.subtract(faixa3)).multiply(aliquota4));
        }
        return inss.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getFGTS(Funcionario f) {
        return f.getSalarioBase().multiply(new BigDecimal("0.08")).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getIRRF(Funcionario f) {
        BigDecimal deducaoDependente = new BigDecimal("189.59");
        BigDecimal baseIRRF = f.getSalarioBase().subtract(getINSS(f))
                .subtract(deducaoDependente.multiply(new BigDecimal(f.getNumDependentes())));
        BigDecimal irrf;

        if (baseIRRF.compareTo(new BigDecimal("2112.00")) <= 0) {
            irrf = BigDecimal.ZERO;
        } else if (baseIRRF.compareTo(new BigDecimal("2826.65")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.075")).subtract(new BigDecimal("158.40"));
        } else if (baseIRRF.compareTo(new BigDecimal("3751.05")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.15")).subtract(new BigDecimal("370.40"));
        } else if (baseIRRF.compareTo(new BigDecimal("4664.68")) <= 0) {
            irrf = baseIRRF.multiply(new BigDecimal("0.225")).subtract(new BigDecimal("651.73"));
        } else {
            irrf = baseIRRF.multiply(new BigDecimal("0.275")).subtract(new BigDecimal("884.96"));
        }

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        return irrf.setScale(2, RoundingMode.HALF_UP);
    }

    public int contarDiasUteis(int mes, int ano, boolean incluirSabado) {
        if (mes < 1 || mes > 12) throw new IllegalArgumentException("Mês inválido (1-12)");
        YearMonth month = YearMonth.of(ano, mes);
        int diasUteis = 0;

        for (int dia = 1; dia <= month.lengthOfMonth(); dia++) {
            LocalDate data = LocalDate.of(ano, mes, dia);
            DayOfWeek diaSemana = data.getDayOfWeek();
            if (diaSemana == DayOfWeek.SUNDAY) continue;
            if (!incluirSabado && diaSemana == DayOfWeek.SATURDAY) continue;
            diasUteis++;
        }
        return diasUteis;
    }

    public List<FuncionarioBeneficio> listarBeneficios(Funcionario f) {
        return new ArrayList<>(f.getBeneficios());
    }

    public Set<FuncionarioBeneficio> setBeneficiosUnicos(Funcionario f) {
        return new HashSet<>(f.getBeneficios());
    }

    public Map<String, BigDecimal> mapearBeneficiosPorNome(Funcionario f) {
        return f.getBeneficios().stream()
                .collect(Collectors.toMap(
                        b -> b.getBeneficio().getNome(),
                        FuncionarioBeneficio::getValor,
                        BigDecimal::add
                ));
    }

    public Map<String, Funcionario> mapearFuncionarioPorCargo(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.toMap(
                        Funcionario::getCargo,
                        func -> func,
                        (f1, f2) -> f1 // em caso de cargos repetidos, mantém o primeiro
                ));
    }

    public List<String> listarNomesFuncionarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getNome)
                .toList();
    }
}
