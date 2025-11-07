package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.service.generico.ServiceGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FolhaPagamentoServiceImpl extends ServiceGenerico<FolhaPagamento, Long>
        implements FolhaPagamentoService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FolhaPagamentoRepository folhaPagamentoRepository;

    @Override
    public void gerarFolhaPagamento(Jwt token) throws RuntimeException {
        LocalDate dataAtual = LocalDate.now();

        //  Busca funcionários ativos com Stream e filtragem funcional
        List<Funcionario> funcionariosAtivos = funcionarioRepository.findAll().stream()
                .filter(f -> Funcionario.HABILITADO.equals(f.getStatus()))
                .collect(Collectors.toList());

        if (funcionariosAtivos.isEmpty()) {
            throw new RuntimeException("Nenhum funcionário ativo encontrado para gerar folha.");
        }

        //  Processamento funcional com Stream + forEach
        funcionariosAtivos.stream().forEach(func -> {
            FolhaPagamento folhaExistente =
                    folhaPagamentoRepository.findByIdFuncionarioIdAndData(
                            func.getId(), dataAtual.withDayOfMonth(1));

            if (folhaExistente != null) return; // já existe folha neste mês

            FolhaPagamento folha = new FolhaPagamento();
            folha.setIdFuncionario(func);
            folha.setData(dataAtual.withDayOfMonth(1));
            folha.setStatus(StatusFolhaPagamento.ABERTA);

            BigDecimal salarioBruto = func.getSalarioBase();
            BigDecimal inss = salarioBruto.multiply(BigDecimal.valueOf(0.11));
            BigDecimal fgts = salarioBruto.multiply(BigDecimal.valueOf(0.08));
            BigDecimal irrf = salarioBruto.multiply(BigDecimal.valueOf(0.075));

            //  Soma de benefícios usando Stream (exemplo: 100 por benefício)
            BigDecimal totalBeneficios = BigDecimal.ZERO;
            if (func.getBeneficios() != null && !func.getBeneficios().isEmpty()) {
                totalBeneficios = BigDecimal.valueOf(func.getBeneficios().stream()
                        .count() * 100);
            }

            BigDecimal totalHorasExtras = BigDecimal.ZERO;
            BigDecimal totalValorHorasExtras = BigDecimal.ZERO;

            BigDecimal totalImpostos = inss.add(irrf).add(fgts);

            BigDecimal salarioLiquido = salarioBruto
                    .add(totalValorHorasExtras)
                    .add(totalBeneficios)
                    .subtract(totalImpostos);

            if (func.getPensao() != null) {
                salarioLiquido = salarioLiquido.subtract(func.getPensao());
            }

            folha.setINSS(inss);
            folha.setFGTS(fgts);
            folha.setIRRF(irrf);
            folha.setTotalValorImposto(totalImpostos);
            folha.setTotalValorBeneficios(totalBeneficios);
            folha.setTotalHorasExtras(totalHorasExtras);
            folha.setTotalValorHorasExtras(totalValorHorasExtras);
            folha.setSalarioBruto(salarioBruto);
            folha.setSalarioLiquido(salarioLiquido);
            folha.setTotal(salarioLiquido);

            folhaPagamentoRepository.save(folha);
        });
    }

    // Método adicional opcional: exemplo de uso de Stream para filtragem avançada
    public List<FolhaPagamento> listarFolhasComSalarioLiquidoMaiorQue(BigDecimal valorMinimo) {
        return folhaPagamentoRepository.findAll().stream()
                .filter(f -> f.getSalarioLiquido().compareTo(valorMinimo) > 0)
                .collect(Collectors.toList());
    }
}
