package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import com.folhafacil.folhafacil.entity.*;
import com.folhafacil.folhafacil.infra.utils.CollectionUtils;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoBenficioMapper;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoHoraExtraMapper;
import com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.ServiceGenerico;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class FolhaPagamentoServiceImpl extends ServiceGenerico<FolhaPagamento, Long> implements FolhaPagamentoService {

    private final FuncionarioServiceImpl funcionarioServiceImpl;
    private final HoraExtraServiceImpl horaExtraServiceImpl;
    private final LogFolhaPagamentoServiceImpl logFolhaPagamentoServiceImpl;
    private final KeycloakService keycloakService;
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final LogSubFolhaPagamentoServiceImpl logSubFolhaPagamentoServiceImpl;

    public FolhaPagamentoServiceImpl(
            FuncionarioServiceImpl funcionarioServiceImpl,
            HoraExtraServiceImpl horaExtraServiceImpl,
            LogFolhaPagamentoServiceImpl logFolhaPagamentoServiceImpl,
            KeycloakService keycloakService,
            FolhaPagamentoRepository folhaPagamentoRepository,
            LogSubFolhaPagamentoServiceImpl logSubFolhaPagamentoServiceImpl
    ) {
        super(folhaPagamentoRepository);
        this.funcionarioServiceImpl = funcionarioServiceImpl;
        this.horaExtraServiceImpl = horaExtraServiceImpl;
        this.logFolhaPagamentoServiceImpl = logFolhaPagamentoServiceImpl;
        this.keycloakService = keycloakService;
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.logSubFolhaPagamentoServiceImpl = logSubFolhaPagamentoServiceImpl;
    }

    @Override
    public void gerarFolhaPagamento(Jwt token) throws RuntimeException {
        try {
            List<Funcionario> fs = funcionarioServiceImpl.findByStatus(Funcionario.HABILITADO);

            LocalDate dataInicio = LocalDate.now().withDayOfMonth(1);

            LogFolhaPagamento lfp = logFolhaPagamentoServiceImpl.gerarLogGeradaAtualizada(keycloakService.recuperarUID(token), dataInicio);

            //list por nome
            fs.sort(Comparator.comparing(Funcionario::getNome));

            //armazena cargo unico
            Set<String> cargosUnicos = new HashSet<>();
            fs.forEach(f -> cargosUnicos.add(f.getCargo()));
            System.out.println("Cargos únicos: " + cargosUnicos);

            Map<Funcionario, BigDecimal> mapSalarioLiquido = new HashMap<>();

            for(Funcionario f : fs) {
                FolhaPagamento fp = folhaPagamentoRepository.findByIdFuncionarioIdAndData(f.getId(), dataInicio);

                if(fp == null) {
                    FolhaPagamento newFP = gerarPorFuncionario(f, dataInicio, new FolhaPagamento());
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogGerado(lfp.getId(), newFP);
                }else if(fp.getStatus().equals(StatusFolhaPagamento.PENDENTE)){
                    FolhaPagamento newFP = gerarPorFuncionario(f, dataInicio, fp);
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogAtualizado(lfp.getId(), newFP);
                }else if(fp.getStatus().equals(StatusFolhaPagamento.PAGO)){
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogErro(lfp.getId(), fp);
                }

                mapSalarioLiquido.put(f, newFP.getSalarioLiquido());
            }

            mapSalarioLiquido.forEach((func, salario) -> System.out.println(func.getNome() + "- Salário Liquido R$" + salario));

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public FolhaPagamento gerarPorFuncionario(Funcionario f, LocalDate data, FolhaPagamento e) throws RuntimeException {
        try{
            e.setIdFuncionario(f);
            e.setStatus(StatusFolhaPagamento.PENDENTE);
            e.setData(data);

            BigDecimal inss = funcionarioServiceImpl.getINSS(f);
            e.setINSS(inss);

            BigDecimal fgst = funcionarioServiceImpl.getFGST(f);
            e.setFGTS(fgst);

            BigDecimal irrf = funcionarioServiceImpl.getIRRF(f);
            e.setIRRF(irrf);

            BigDecimal totalValorImposto = inss.add(fgst).add(irrf);
            e.setTotalValorImposto(totalValorImposto);

            BigDecimal totalValorBeneficios = funcionarioServiceImpl.getTotalValorBeneficios(f);
            e.setTotalValorBeneficios(totalValorBeneficios);

            List<FolhaPagamentoBeneficio> novosBeneficios =
                    FolhaPagamentoBenficioMapper.toList(f.getBeneficios(), e);
            CollectionUtils.replaceCollection(e.getBeneficios(), novosBeneficios);

            BigDecimal totalHorasExtras = BigDecimal.ZERO;
            BigDecimal totalValorHorasExtras = BigDecimal.ZERO;

            if(!f.getCargo().equals("ESTAGIARIO")){
                totalHorasExtras = horaExtraServiceImpl.totalHorasNoMes(f.getId(), data);

                if(!(totalHorasExtras.compareTo(BigDecimal.ZERO) <= 0)){
                    totalValorHorasExtras = totalHorasExtras.multiply(funcionarioServiceImpl.valorHoraExtra(f));
                }
            }
            List<FolhaPagamentoHoraExtra> novasHorasExtras =
                    FolhaPagamentoHoraExtraMapper.toList(horaExtraServiceImpl.findByFuncionarioAndMesAno(f.getId(), data), e);
            CollectionUtils.replaceCollection(e.getHorasExtras(), novasHorasExtras);
            e.setTotalHorasExtras(totalHorasExtras);
            e.setTotalValorHorasExtras(totalValorHorasExtras);

            e.setSalarioBruto(f.getSalarioBase());
            e.setSalarioLiquido(
                    f.getSalarioBase()
                            .subtract(totalValorImposto)
                            .add(totalValorBeneficios)
                            .add(totalValorHorasExtras)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
            );

            e.setTotal(f.getSalarioBase().add(totalValorHorasExtras).add(totalValorBeneficios).subtract(totalValorImposto));

            return folhaPagamentoRepository.save(e);
        }catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
