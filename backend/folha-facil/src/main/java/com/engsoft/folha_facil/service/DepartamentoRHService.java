package com.engsoft.folha_facil.service;
import com.engsoft.folha_facil.model.DepartamentoRH;
import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.model.Beneficio;
import com.engsoft.folha_facil.model.FolhaPagamento;
import com.engsoft.folha_facil.model.Imposto;
import com.engsoft.folha_facil.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public class DepartamentoRHService {
    private DepartamentoRH departamento;

    public DepartamentoRHService(){
        this.departamento = new DepartamentoRH();
    }

    //BENEFICIOS
    public void cadastrarBeneficio(Beneficio beneficio){
        if(beneficio != null){
            departamento.getBeneficios().add(beneficio);
        }
    }

    public void alterarBeneficio(Beneficio beneficioAtualizado){
        List<Beneficio> beneficio = departamento.getBeneficios();

        for(int i = 0; i < beneficio.size(); i++){
            if(beneficio.get(i).getId().equals(beneficioAtualizado.getId())){
                beneficio.set(i, beneficioAtualizado);
                return;
            }
        }
    }

    public void excluirBeneficio(long id){
        departamento.getBeneficios().removeIf(f -> f.getId().equals(id));
    }

    public List<Beneficio> listarBeneficio(){
        return new ArrayList<>(departamento.getBeneficios());
    }


    //FOLHA DE PAGAMENTO
    public void revisarFolhaPagamento(FolhaPagamento fp){
        departamento.setFolhaPagamento(fp); //só substitui a folha de pagamento
    }

    public void aprovarFolhaPagamento(){
        FolhaPagamento fp = departamento.getFolhaPagamento();

        if(fp != null){
            fp.setDataPagamento(new Date());

            fp.getHistoricoPagamentos().add(fp);

            double salarioBruto = fp.calcularSalarioBruto();
            double descontos = fp.calcularDescontos();
            double salarioLiquido = fp.calcularSalarioLiquido();

            fp.setSalarioBruto(salarioBruto);
            fp.setSalarioLiquido(salarioLiquido);

            System.out.println("Folha de pagamento aprovada e registrada com sucesso!!");
        }else{
            System.out.println("Nenhuma folha de pagamento cadastrada para aprovação.");
        }
    }

    public String gerarRelatorioConsolidado(){
        StringBuilder sb = new StringBuilder();

        sb.append("--> RELATORIO-(DEPARTAMENTO RH) <--");
        sb.append("Id do Departamento: ").append(departamento.getId() != null ? departamento.getId() : "N/A").append("\n\n");

        sb.append("Total de Funcionarios: ").append(departamento.getFuncionarios().size()).append("\n");
        
        if(departamento.getBeneficios().isEmpty()){
            sb.append("Benefícios: Nenhum Benefício cadastrado.\n");
        }else{
            sb.append("Benefícios:\n");
            for(int i = 0; i < departamento.getBeneficios().size(); i++){
                Beneficio b = departamento.getBeneficios().get(i);

                sb.append(" ").append(i+1).append(". ").append(b.getTipo());

                if(b.getValor() > 0){
                    sb.append(" (R$ ").append(String.format("%.2f", b.getValor())).append(")");
                }

                sb.append("\n");
            }
        }

        if(departamento.getImpostos().isEmpty()){
            sb.append("Imposto: Nenhum imposto cadastrado.\n");
        }else{
            sb.append("Impostos:\n");
            for(int i = 0; i < departamento.getImpostos().size(); i++){
                Imposto imposto = departamento.getImpostos().get(i);

                sb.append("   -INSS: R$ ").append(String.format("%.2f", imposto.getINSS())).append("\n");
                sb.append("   -FGTS: R$ ").append(String.format("%.2f", imposto.getFGTS())).append("\n");
                sb.append("   -IRRF: R$ ").append(String.format("%.2f", imposto.getIRRF())).append("\n");
                sb.append("   -Desconto Total: R$").append(String.format("%.2f", imposto.getDescontoTotal())).append("\n");
                sb.append("\n");
            }
        }

        FolhaPagamento fp = departamento.getFolhaPagamento();
        if(fp != null){
            sb.append("\n--- Ultima Folha de Pagamento ---\n");
            sb.append("Funcionário: ").append(fp.getFuncionario().getNome()).append("\n");
            sb.append("Salário Bruto: R$ ").append(String.format("%.2f", fp.getSalarioBruto())).append("\n");
            sb.append("Salário Líquido: R$ ").append(String.format("%.2f", fp.getSalarioLiquido())).append("\n");
            sb.append("Data Pagamento: ").append(fp.getDataPagamento()).append("\n");
        }else{
            sb.append("\nNenhuma folha de pagamento registrada.\n");
        }

        sb.append("---------------------------------");
        return sb.toString();
    }
}
