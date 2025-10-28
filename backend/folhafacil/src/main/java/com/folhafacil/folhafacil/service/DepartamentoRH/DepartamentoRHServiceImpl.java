package com.folhafacil.folhafacil.service.DepartamentoRH;

public class DepartamentoRHServiceImpl {
/*
    public Map<String, Object> gerarRelatorioFuncionario(Long funcionarioId, YearMonth competencia) {
        Funcionario f = funcionarioService.buscarFuncionarioPorId(funcionarioId);
        if (f == null) {
            throw new IllegalArgumentException("Funcionário não encontrado!");
        }

        // Consultar folha de pagamento do período
        FolhaPagamento fp = folhaPagamentoService.consutarPorPeriodo(
                        java.sql.Date.valueOf(competencia.atDay(1)),
                        java.sql.Date.valueOf(competencia.atEndOfMonth())
                ).stream().filter(folha -> folha.getFuncionario().getId().equals(funcionarioId))
                .findFirst().orElse(null);

        if (fp == null) {
            // Se ainda não existir folha, gera uma nova
            fp = folhaPagamentoService.gerarFolha(f, competencia.getMonthValue(), competencia.getYear());
        }

        // Benefícios que entram no salário líquido
        double totalBeneficios = f.getPlanoBeneficios().stream()
                .filter(b -> b.getTipo() != BeneficioTipo.VALE_TRANSPORTE && b.getTipo() != BeneficioTipo.VALE_ALIMENTACAO)
                .mapToDouble(Beneficio::getValor)
                .sum();

        // Monta mapa do relatório para envio ao front
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("nome", f.getNome());
        relatorio.put("cpf", f.getCpf());
        relatorio.put("cargo", f.getCargo());
        relatorio.put("dataAdmissao", f.getDataAdmissao());
        relatorio.put("salarioBase", f.getSalarioBase());
        relatorio.put("salarioBruto", fp.getSalarioBruto());
        relatorio.put("salarioLiquido", fp.getSalarioLiquido());
        relatorio.put("horasExtras", fp.getHoraExtra());
        relatorio.put("diasFaltados", fp.getDiasFaltados());
        relatorio.put("beneficios", f.getPlanoBeneficios());
        relatorio.put("totalBeneficios", totalBeneficios);
        relatorio.put("impostos", fp.getImposto());

        return relatorio;
    }

    */
/**
     * Gera relatório completo do colaborador para a competência informada.
     * Retorna um Map com campos solicitados:
     * - nome, dataAdmissao, competencia, cargo, salarioBase
     * - proventos (lista de {descricao, valor}), descontos (lista de {descricao, valor})
     * - salarioBruto, salarioHora, salarioLiquido
     * - baseINSS, baseFGTS, baseIRRF
     * - dataCriacao (data do relatório)
     *//*

    public Map<String, Object> gerarRelatorioCompleto(Long funcionarioId, YearMonth competencia) {
        Funcionario f = funcionarioService.buscarFuncionarioPorId(funcionarioId);
        if (f == null) throw new IllegalArgumentException("Funcionário não encontrado");

        // Buscar folha do período (reaproveita lógica já existente)
        FolhaPagamento fp = folhaPagamentoService.consutarPorPeriodo(
                        java.sql.Date.valueOf(competencia.atDay(1)),
                        java.sql.Date.valueOf(competencia.atEndOfMonth())
                ).stream()
                .filter(p -> p.getFuncionario() != null && p.getFuncionario().getId() != null && p.getFuncionario().getId().equals(funcionarioId))
                .findFirst()
                .orElseGet(() -> {
                    // gera folha se não existir (método pode variar conforme sua implementação)
                    try {
                        return folhaPagamentoService.gerarFolha(f, competencia.getMonthValue(), competencia.getYear());
                    } catch (Throwable t) {
                        return null;
                    }
                });

        // Preparar listas de proventos e descontos
        List<Map<String, Object>> proventos = new ArrayList<>();
        List<Map<String, Object>> descontos = new ArrayList<>();

        // Provento: salário base
        proventos.add(Map.of("descricao", "Salário base", "valor", f.getSalarioBase()));

        // Benefícios que são proventos (ex.: periculosidade, insalubridade, comissões etc.)
        if (f.getPlanoBeneficios() != null) {
            for (Beneficio b : f.getPlanoBeneficios()) {
                if (b == null) continue;
                // considere VA/VT como desconto/benefício não integrável ao bruto conforme regra
                if (b.getTipo() == BeneficioTipo.PERICULOSIDADE || b.getTipo() == BeneficioTipo.INSALUBRIDADE
                        || b.getTipo().name().contains("COMISSAO") || b.getTipo().name().contains("COMISS")) {
                    proventos.add(Map.of("descricao", b.getTipo().name(), "valor", b.getValor()));
                } else {
                    // demais benefícios que geram desconto (ex.: VT tem desconto calculado)
                    if (b.getDesconto() > 0) {
                        descontos.add(Map.of("descricao", "Desconto " + b.getTipo().name(), "valor", b.getDesconto()));
                    }
                    // se o benefício for VA/VR e for considerado provento, você pode movê-lo pra proventos
                    if (b.getTipo() == BeneficioTipo.VALE_ALIMENTACAO) {
                        proventos.add(Map.of("descricao", b.getTipo().name(), "valor", b.getValor()));
                    }
                }
            }
        }

        // Horas extras (se presente na folha)
        double salarioHora = 0.0;
        if (f.getHorasSemanais() > 0) {
            double horasMensais = f.getHorasSemanais() * 4.333333; // média semanas por mês
            salarioHora = f.getSalarioBase() / horasMensais;
        }
        if (fp != null && fp.getHoraExtra() > 0) {
            double horasExtras = fp.getHoraExtra();
            double valorHorasExtras = salarioHora * 1.5 * horasExtras; // 50% adicional padrão
            proventos.add(Map.of("descricao", "Horas extras", "valor", round2(valorHorasExtras)));
        }

        // Faltas (desconto aproximado por dias faltados)
        if (fp != null && fp.getDiasFaltados() > 0) {
            int diasFaltados = fp.getDiasFaltados();
            double valorPorDia = f.getSalarioBase() / 22.0; // aproximação: 22 dias úteis/mês
            double descontoFaltas = diasFaltados * valorPorDia;
            descontos.add(Map.of("descricao", "Faltas (" + diasFaltados + " dias)", "valor", round2(descontoFaltas)));
        }

        // Impostos e bases (da folha, se existir)
        double salarioBruto = (fp != null ? fp.getSalarioBruto() : f.getSalarioBase());
        double salarioLiquido = (fp != null ? fp.getSalarioLiquido() : f.getSalarioBase());
        Imposto imposto = (fp != null ? fp.getImposto() : null);

        double inssVal = imposto != null ? safeGet(() -> imposto.getINSS()) : 0.0;
        double irrfVal = imposto != null ? safeGet(() -> imposto.getIRRF()) : 0.0;
        double fgtsVal = imposto != null ? safeGet(() -> imposto.getFGTS()) : 0.0;

        if (inssVal > 0) descontos.add(Map.of("descricao", "INSS", "valor", inssVal));
        if (irrfVal > 0) descontos.add(Map.of("descricao", "IRRF", "valor", irrfVal));
        if (fgtsVal > 0) {
            // FGTS normalmente é obrigação da empresa (provento/encargo), mas usuário pediu listar em descontos/opcional
            descontos.add(Map.of("descricao", "FGTS (retenção/encargo)", "valor", fgtsVal));
        }

        // Pensão alimentícia
        if (f.getPensaoAlimenticia() > 0) {
            descontos.add(Map.of("descricao", "Pensão alimentícia", "valor", f.getPensaoAlimenticia()));
        }

        // Bases de cálculo
        double baseINSS = salarioBruto;
        double baseFGTS = salarioBruto;
        double deducaoDependentes = f.getNumDependentes() * 189.59;
        double baseIRRF = salarioBruto - inssVal - deducaoDependentes;

        // Monta relatório final
        Map<String, Object> relatorio = new LinkedHashMap<>();
        relatorio.put("nome", f.getNome());
        relatorio.put("dataAdmissao", f.getDataAdmissao());
        relatorio.put("competencia", competencia);
        relatorio.put("cargo", f.getCargo());
        relatorio.put("salarioBase", f.getSalarioBase());
        relatorio.put("proventos", proventos);
        relatorio.put("descontos", descontos);
        relatorio.put("salarioBruto", round2(salarioBruto));
        relatorio.put("salarioHora", round2(salarioHora));
        relatorio.put("salarioLiquido", round2(salarioLiquido));
        relatorio.put("baseINSS", round2(baseINSS));
        relatorio.put("baseFGTS", round2(baseFGTS));
        relatorio.put("baseIRRF", round2(baseIRRF));
        relatorio.put("dataCriacaoRelatorio", java.time.LocalDate.now());

        return relatorio;
    }

    // helpers locais
    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private static double safeGet(java.util.function.Supplier<Double> s) {
        try {
            Double v = s.get();
            return v != null ? v : 0.0;
        } catch (Throwable t) {
            return 0.0;
        }
    }
*/
}
