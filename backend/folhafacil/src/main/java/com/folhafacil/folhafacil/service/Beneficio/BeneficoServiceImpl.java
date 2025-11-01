package com.folhafacil.folhafacil.service.Beneficio;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.mapper.BeneficioMapper;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioCustomRepository;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficoServiceImpl implements BeneficioService {
    private final BeneficioRepository beneficioRepository;
    private final BeneficioCustomRepository beneficioCustomRepository;

    public BeneficoServiceImpl(
            BeneficioRepository beneficioRepository,
            BeneficioCustomRepository beneficioCustomRepository
    ) {
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

    /*public Beneficio adicionarBeneficio(Funcionario funcionario, BeneficioTipo tipo, double valor, double desconto){
        if (funcionario == null) throw new IllegalArgumentException("Funcionario nulo");

        Beneficio beneficio = new Beneficio(tipo, valor, desconto);
        funcionario.getPlanoBeneficios().add(beneficio);
        return beneficio;
    }

    public void removerBeneficio(Funcionario funcionario, BeneficioTipo tipo){
        if(funcionario == null) throw new IllegalArgumentException("Funcionário nuo");
        if(tipo == null) throw new IllegalArgumentException("Tipo de benefício nulo");
        if(tipo == BeneficioTipo.VALE_TRANSPORTE) {
            throw new IllegalArgumentException("Vale transporte é um benefício obrigatório");
        }

        boolean removed = funcionario.getPlanoBeneficios()
                .removeIf(b -> b.getTipo() == tipo);

        if(!removed){
            throw new IllegalArgumentException("Beneficio " + tipo + " Não encontrado!");
        }
    }

    public Beneficio calcularPericulosidade(Funcionario funcionario) {
        if(funcionario.getSalarioBase() <= 0)
            throw new IllegalArgumentException("O funcionário precisa de um salario!");

        double adicional = funcionario.getSalarioBase() * 0.3;

        return adicionarBeneficio(funcionario, BeneficioTipo.PERICULOSIDADE, adicional, 0.0);
    }

    public Beneficio calcularInsalubridade(int grausInsalubridade, Funcionario funcionario) {
        if(grausInsalubridade != 10 && grausInsalubridade != 20 && grausInsalubridade != 40 )
            throw new IllegalArgumentException("Valor de grau de insalibridade inválido, deve ser 10,20 ou 40.");
        double adicional = funcionario.getSalarioBase() * (grausInsalubridade/100.0);
        return adicionarBeneficio(funcionario, BeneficioTipo.INSALUBRIDADE, adicional, 0.0);
    }

    public String grausInsalubridade(int grausInsalubridade) {
        return switch (grausInsalubridade) {
            case 10 -> "Baixo";
            case 20 -> "Médio";
            case 40 -> "Alto";
            default -> "Inválido";
        };
    }

    public Beneficio calcularValeTransporte(Funcionario funcionario) {

        double salario = funcionario.getSalarioBase();
        double valorVT = funcionario.getValeTransporte().getValor();
        double limite = salario * 0.06;

        double descontoVT = (valorVT > limite) ? valorVT:limite;


        funcionario.getValeTransporte().setDesconto(descontoVT);

        return adicionarBeneficio(funcionario, BeneficioTipo.VALE_TRANSPORTE, valorVT, descontoVT);
    }

    public Beneficio calcularValeAlimentacao(Funcionario funcionario,
                                             int mes, int ano, boolean trabalhaSabado, double valorDiario) {
        int diasUteis = funcionarioService.contarDiasUteis(mes, ano, trabalhaSabado);

        double valorTotal = diasUteis * valorDiario;

        return adicionarBeneficio(funcionario, BeneficioTipo.VALE_ALIMENTACAO, valorTotal, 0.0);
    }*/
}
