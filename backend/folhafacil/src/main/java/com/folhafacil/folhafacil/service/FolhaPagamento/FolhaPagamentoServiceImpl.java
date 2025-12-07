package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.*;
import com.folhafacil.folhafacil.entity.*;
import com.folhafacil.folhafacil.infra.utils.CollectionUtils;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoBenficioMapper;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoHoraExtraMapper;
import com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoCustomRepository;
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
import java.util.*;

@Service
public class FolhaPagamentoServiceImpl extends ServiceGenerico<FolhaPagamento, Long> implements FolhaPagamentoService {

    private final FuncionarioServiceImpl funcionarioServiceImpl;
    private final HoraExtraServiceImpl horaExtraServiceImpl;
    private final LogFolhaPagamentoServiceImpl logFolhaPagamentoServiceImpl;
    private final KeycloakService keycloakService;
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final FolhaPagamentoCustomRepository  folhaPagamentoCustomRepository;
    private final LogSubFolhaPagamentoServiceImpl logSubFolhaPagamentoServiceImpl;

    public FolhaPagamentoServiceImpl(
            FuncionarioServiceImpl funcionarioServiceImpl,
            HoraExtraServiceImpl horaExtraServiceImpl,
            LogFolhaPagamentoServiceImpl logFolhaPagamentoServiceImpl,
            KeycloakService keycloakService,
            FolhaPagamentoRepository folhaPagamentoRepository,
            FolhaPagamentoCustomRepository folhaPagamentoCustomRepository,
            LogSubFolhaPagamentoServiceImpl logSubFolhaPagamentoServiceImpl
    ) {
        super(folhaPagamentoRepository);
        this.funcionarioServiceImpl = funcionarioServiceImpl;
        this.horaExtraServiceImpl = horaExtraServiceImpl;
        this.logFolhaPagamentoServiceImpl = logFolhaPagamentoServiceImpl;
        this.keycloakService = keycloakService;
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.folhaPagamentoCustomRepository = folhaPagamentoCustomRepository;
        this.logSubFolhaPagamentoServiceImpl = logSubFolhaPagamentoServiceImpl;
    }

    @Override
    public void gerarFolhaPagamento(Jwt token) throws RuntimeException {
        try {
            List<Funcionario> fs = funcionarioServiceImpl.findByStatus(Funcionario.HABILITADO);

            LocalDate dataInicio = LocalDate.now().withDayOfMonth(1);

            LogFolhaPagamento lfp = logFolhaPagamentoServiceImpl.gerarLogGeradaAtualizada(keycloakService.recuperarUID(token), dataInicio);

            for(Funcionario f : fs) {
                FolhaPagamento fp = folhaPagamentoRepository.findByIdFuncionarioIdAndData(f.getId(), dataInicio);
                FolhaPagamento newFP = new  FolhaPagamento();

                if(fp == null) {
                    newFP = gerarPorFuncionario(f, dataInicio, new FolhaPagamento());
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogGerado(lfp.getId(), newFP);
                }else if(fp.getStatus().equals(StatusFolhaPagamento.PENDENTE)){
                    newFP = gerarPorFuncionario(f, dataInicio, fp);
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogAtualizado(lfp.getId(), newFP);
                }else if(fp.getStatus().equals(StatusFolhaPagamento.PAGO)){
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogErro(lfp.getId(), fp);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void pagarFolhaPagamento(List<Long> ids, Jwt token) throws RuntimeException{
        try {
            int size = ids.size();
            LogFolhaPagamento lfp = logFolhaPagamentoServiceImpl.gerarLogPagamento(keycloakService.recuperarUID(token),size);
            for(Long id : ids){
                FolhaPagamento fp = folhaPagamentoRepository.findById(id).get();
                if(fp.getStatus().equals(StatusFolhaPagamento.PAGO)){
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogErro(lfp.getId(), fp);
                }else if(fp.getStatus().equals(StatusFolhaPagamento.PENDENTE)){
                    fp.setStatus(StatusFolhaPagamento.PAGO);
                    folhaPagamentoRepository.save(fp);
                    LogSubFolhaPagamento lsfp = logSubFolhaPagamentoServiceImpl.gerarLogPago(lfp.getId(), fp);
                }
            }
        }catch (RuntimeException e){
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

            BigDecimal fgst = funcionarioServiceImpl.getFGTS(f);
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
                    totalValorHorasExtras = totalHorasExtras.multiply(funcionarioServiceImpl.calcularValorHoraExtra(f));
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

            return folhaPagamentoRepository.save(e);
        }catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<FolhaPagamentoResponseDTO> buscar(FolhaPagamentoFilterDTO f){
        return folhaPagamentoCustomRepository.buscar(f);
    }

    @Override
    public List<FolhaPagamentoBeneficioResponseDTO> buscarBeneficios(Long idFolha){
        return folhaPagamentoCustomRepository.buscarBeneficios(idFolha);
    }

    @Override
    public List<FolhaPagamentoHoraExtraResponseDTO> buscarHorasExtras(Long idFolha){
        return folhaPagamentoCustomRepository.buscarHorasExtras(idFolha);
    }

    @Override
    public List<FolhaPagamentoResponseDTO> meusBeneficios(Jwt t){
        FolhaPagamentoFilterDTO f = new FolhaPagamentoFilterDTO();
        List<String> listUIds = new ArrayList<>();
        listUIds.add(keycloakService.recuperarUID(t));
        f.setFuncionarios(listUIds);

        return folhaPagamentoCustomRepository.buscar(f);
    }
}
