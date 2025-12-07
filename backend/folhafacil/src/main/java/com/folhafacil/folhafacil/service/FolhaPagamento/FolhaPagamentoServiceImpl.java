package com.folhafacil.folhafacil.service.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.*;
import com.folhafacil.folhafacil.entity.*;
import com.folhafacil.folhafacil.infra.utils.CollectionUtils;
import com.folhafacil.folhafacil.infra.utils.DataUtils;
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
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public byte[] exportar(String type, Boolean horasExtras, Boolean beneficios, List<Long> ids) throws IOException{
        if(type.equals("excel")){
            return gerarExcel(folhaPagamentoCustomRepository.buscar(ids), horasExtras, beneficios);
        }
        return gerarPDF(folhaPagamentoCustomRepository.buscar(ids), horasExtras, beneficios);
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


    private byte[] gerarExcel(List<FolhaPagamentoResponseDTO> folhas, Boolean horasExtras, Boolean beneficios) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Folhas");

        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(60, 121, 170), null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont headerFont = workbook.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.cloneStyleFrom(headerStyle);

        XSSFCellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(workbook.createDataFormat().getFormat("R$ #,##0.00"));

        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));

        int rowNum = 0;

        if(!horasExtras && !beneficios){
            String[] headers = {
                    "Usuário", "Status", "Data",
                    "INSS", "FGTS", "IRRF", "Total Imposto",
                    "Total Benefícios", "Total Horas Extras", "Total Valor Horas Extras",
                    "Salário Bruto", "Salário Líquido"
            };

            Row headerRow = sheet.createRow(rowNum++);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
        }

        for (FolhaPagamentoResponseDTO f : folhas) {

            if(horasExtras || beneficios){
                String[] headers = {
                        "Usuário", "Status", "Data",
                        "INSS", "FGTS", "IRRF", "Total Imposto",
                        "Total Benefícios", "Total Horas Extras", "Total Valor Horas Extras",
                        "Salário Bruto", "Salário Líquido"
                };

                Row headerRow = sheet.createRow(rowNum++);

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                }
            }

            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < 12; i++) row.createCell(i);

            int col = 0;

            row.getCell(col++).setCellValue(f.getUsuarioFuncionario());
            row.getCell(col++).setCellValue(f.getStatus().name());

            Cell dataCell = row.getCell(col++);
            dataCell.setCellValue(java.sql.Date.valueOf(f.getData()));
            dataCell.setCellStyle(dateStyle);

            row.getCell(col++).setCellValue(f.getINSS().doubleValue());
            row.getCell(col++).setCellValue(f.getFGTS().doubleValue());
            row.getCell(col++).setCellValue(f.getIRRF().doubleValue());
            row.getCell(col++).setCellValue(f.getTotalValorImposto().doubleValue());
            row.getCell(col++).setCellValue(f.getTotalValorBeneficios().doubleValue());
            row.getCell(col++).setCellValue(f.getTotalHorasExtras().doubleValue());
            row.getCell(col++).setCellValue(f.getTotalValorHorasExtras().doubleValue());
            row.getCell(col++).setCellValue(f.getSalarioBruto().doubleValue());
            row.getCell(col++).setCellValue(f.getSalarioLiquido().doubleValue());

            for (int i = 3; i <= 11; i++) {
                row.getCell(i).setCellStyle(moneyStyle);
            }

            if (horasExtras) {
                List<FolhaPagamentoHoraExtraResponseDTO> hes = buscarHorasExtras(f.getId());

                if (!hes.isEmpty()) {

                    Row titleRow = sheet.createRow(rowNum++);
                    for (int i = 0; i < 12; i++) titleRow.createCell(i);

                    Cell titleCell = titleRow.getCell(1);
                    titleCell.setCellValue("Horas Extras");
                    titleCell.setCellStyle(titleStyle);

                    sheet.addMergedRegion(new CellRangeAddress(
                            titleRow.getRowNum(), titleRow.getRowNum(),
                            0, 11
                    ));

                    Row heHeader = sheet.createRow(rowNum++);
                    for (int i = 0; i < 12; i++) heHeader.createCell(i);

                    heHeader.getCell(1).setCellValue("Início");
                    heHeader.getCell(4).setCellValue("Fim");
                    heHeader.getCell(7).setCellValue("Horas");
                    heHeader.getCell(10).setCellValue("Valor");

                    heHeader.getCell(1).setCellStyle(headerStyle);
                    heHeader.getCell(4).setCellStyle(headerStyle);
                    heHeader.getCell(7).setCellStyle(headerStyle);
                    heHeader.getCell(10).setCellStyle(headerStyle);

                    sheet.addMergedRegion(new CellRangeAddress(heHeader.getRowNum(), heHeader.getRowNum(), 0, 2));
                    sheet.addMergedRegion(new CellRangeAddress(heHeader.getRowNum(), heHeader.getRowNum(), 3, 5));
                    sheet.addMergedRegion(new CellRangeAddress(heHeader.getRowNum(), heHeader.getRowNum(), 6, 8));
                    sheet.addMergedRegion(new CellRangeAddress(heHeader.getRowNum(), heHeader.getRowNum(), 9, 11));

                    for (FolhaPagamentoHoraExtraResponseDTO he : hes) {
                        Row heRow = sheet.createRow(rowNum++);
                        for (int i = 0; i < 12; i++) heRow.createCell(i);

                        heRow.getCell(0).setCellValue(DataUtils.fomatarDataHorario(he.getDataInicio()));
                        heRow.getCell(3).setCellValue(DataUtils.fomatarDataHorario(he.getDataFim()));
                        heRow.getCell(6).setCellValue(diffHorasMinutos(he.getDataInicio(), he.getDataFim()));

                        Cell valor = heRow.getCell(9);
                        valor.setCellValue(getValorHe(he.getDataInicio(), he.getDataFim(), he.getValorHora()).doubleValue());
                        valor.setCellStyle(moneyStyle);

                        sheet.addMergedRegion(new CellRangeAddress(heRow.getRowNum(), heRow.getRowNum(), 0, 2));
                        sheet.addMergedRegion(new CellRangeAddress(heRow.getRowNum(), heRow.getRowNum(), 3, 5));
                        sheet.addMergedRegion(new CellRangeAddress(heRow.getRowNum(), heRow.getRowNum(), 6, 8));
                        sheet.addMergedRegion(new CellRangeAddress(heRow.getRowNum(), heRow.getRowNum(), 9, 11));
                    }
                }
            }


            if (beneficios) {
                List<FolhaPagamentoBeneficioResponseDTO> bs = buscarBeneficios(f.getId());

                if (!bs.isEmpty()) {

                    Row titulo = sheet.createRow(rowNum++);
                    for (int i = 0; i < 12; i++) titulo.createCell(i);

                    Cell tCell = titulo.getCell(1);
                    tCell.setCellValue("Benefícios");
                    tCell.setCellStyle(titleStyle);

                    sheet.addMergedRegion(new CellRangeAddress(
                            titulo.getRowNum(), titulo.getRowNum(),
                            0, 11

                    ));

                    Row benHeader = sheet.createRow(rowNum++);
                    for (int i = 0; i < 12; i++) benHeader.createCell(i);

                    benHeader.getCell(0).setCellValue("Nome");
                    benHeader.getCell(6).setCellValue("Valor");

                    benHeader.getCell(0).setCellStyle(headerStyle);
                    benHeader.getCell(6).setCellStyle(headerStyle);

                    sheet.addMergedRegion(new CellRangeAddress(benHeader.getRowNum(), benHeader.getRowNum(), 0, 5));
                    sheet.addMergedRegion(new CellRangeAddress(benHeader.getRowNum(), benHeader.getRowNum(), 6, 11));

                    for (FolhaPagamentoBeneficioResponseDTO b : bs) {
                        Row benRow = sheet.createRow(rowNum++);
                        for (int i = 0; i < 12; i++) benRow.createCell(i);

                        benRow.getCell(1).setCellValue(b.getNomeBeneficio());

                        Cell v = benRow.getCell(7);
                        v.setCellValue(b.getValorBeneficio().doubleValue());
                        v.setCellStyle(moneyStyle);

                        sheet.addMergedRegion(new CellRangeAddress(benRow.getRowNum(), benRow.getRowNum(), 0, 5));
                        sheet.addMergedRegion(new CellRangeAddress(benRow.getRowNum(), benRow.getRowNum(), 6, 11));
                    }
                }
            }
        }

        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }


    private byte[] gerarPDF(List<FolhaPagamentoResponseDTO> folhas, Boolean horasExtras, Boolean beneficios) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, baos);
        document.open();

        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
        Font normalFont = new Font(Font.HELVETICA, 9);

        PdfPTable table = new PdfPTable(12);
        table.setWidthPercentage(100);

        if(!beneficios && !horasExtras){
            String[] headers = {
                    "Usuário", "Status", "Data",
                    "INSS", "FGTS", "IRRF", "Total Imposto",
                    "Total Benefícios", "Total Horas Extras",
                    "Total Valor Horas Extras", "Salário Bruto", "Salário Líquido"
            };

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(new Color(60,121,170));
                cell.setPadding(5);
                table.addCell(cell);
            }
        }

        for (FolhaPagamentoResponseDTO f : folhas) {
            if(horasExtras || beneficios){
                String[] headers = {
                        "Usuário", "Status", "Data",
                        "INSS", "FGTS", "IRRF", "Total Imposto",
                        "Total Benefícios", "Total Horas Extras",
                        "Total Valor Horas Extras", "Salário Bruto", "Salário Líquido"
                };

                for (String h : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                    cell.setBackgroundColor(new Color(60,121,170));
                    cell.setPadding(5);
                    table.addCell(cell);
                }
            }

            table.addCell(new PdfPCell(new Phrase(f.getUsuarioFuncionario(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(f.getStatus().name(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(DataUtils.formatarBrasil(f.getData()), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getINSS().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getFGTS().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getIRRF().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getTotalValorImposto().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getTotalValorBeneficios().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getTotalHorasExtras().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getTotalValorHorasExtras().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getSalarioBruto().toPlainString(), normalFont)));
            table.addCell(new PdfPCell(new Phrase("R$" + f.getSalarioLiquido().toPlainString(), normalFont)));

            document.add(table);

            if (horasExtras) {
                List<FolhaPagamentoHoraExtraResponseDTO> hes = buscarHorasExtras(f.getId());

                if(!hes.isEmpty()) {

                    PdfPTable heTable = new PdfPTable(4);
                    heTable.setWidthPercentage(100);

                    PdfPCell tituloCell = new PdfPCell(new Phrase("Horas Extras", headerFont));
                    tituloCell.setColspan(4);
                    tituloCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tituloCell.setBackgroundColor(new Color(60, 121, 170));
                    tituloCell.setPadding(5);
                    heTable.addCell(tituloCell);

                    String[] heHeaders = {"Início", "Fim", "Horas", "Valor"};

                    for (String h : heHeaders) {
                        PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                        cell.setBackgroundColor(new Color(60,121,170));
                        cell.setPadding(5);
                        heTable.addCell(cell);
                    }

                    for (FolhaPagamentoHoraExtraResponseDTO he : hes) {
                        heTable.addCell(new Phrase(DataUtils.fomatarDataHorario(he.getDataInicio()), normalFont));
                        heTable.addCell(new Phrase(DataUtils.fomatarDataHorario(he.getDataFim()), normalFont));
                        heTable.addCell(new Phrase(diffHorasMinutos(he.getDataInicio(), he.getDataFim()), normalFont));
                        heTable.addCell(new Phrase("R$" + getValorHe(he.getDataInicio(), he.getDataFim(), he.getValorHora()).toPlainString(), normalFont));
                    }

                    document.add(heTable);
                }
            }


            if (beneficios) {
                List<FolhaPagamentoBeneficioResponseDTO> bs = buscarBeneficios(f.getId());

                if(!bs.isEmpty()) {
                    PdfPTable benTable = new PdfPTable(2);
                    benTable.setWidthPercentage(100);

                    PdfPCell tituloCell = new PdfPCell(new Phrase("Benefícios", headerFont));
                    tituloCell.setColspan(2);
                    tituloCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tituloCell.setBackgroundColor(new Color(60, 121, 170));
                    tituloCell.setPadding(5);
                    benTable.addCell(tituloCell);

                    String[] benHeaders = {"Nome", "Valor"};
                    for (String h : benHeaders) {
                        PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                        cell.setBackgroundColor(new Color(60,121,170));
                        cell.setPadding(5);
                        benTable.addCell(cell);
                    }

                    for (FolhaPagamentoBeneficioResponseDTO b : bs) {
                        benTable.addCell(new Phrase(b.getNomeBeneficio(), normalFont));
                        benTable.addCell(new Phrase("R$" + b.getValorBeneficio().toPlainString(), normalFont));
                    }

                    document.add(benTable);
                }
            }

            table = new PdfPTable(12);
            table.setWidthPercentage(100);
        }

        document.close();
        return baos.toByteArray();
    }



    private BigDecimal diffHoras(LocalDateTime inicio, LocalDateTime fim) {
        Duration d = Duration.between(inicio, fim);

        long segundos = d.getSeconds();

        return BigDecimal.valueOf(segundos)
                .divide(BigDecimal.valueOf(3600), 2, BigDecimal.ROUND_HALF_UP);
    }


    private String diffHorasMinutos(LocalDateTime inicio, LocalDateTime fim) {
        Duration d = Duration.between(inicio, fim).abs();

        long totalMinutos = d.toMinutes();
        long horas = totalMinutos / 60;
        long minutos = totalMinutos % 60;

        return String.format("%dh%02dm", horas, minutos);
    }

    private BigDecimal getValorHe(LocalDateTime inicio, LocalDateTime fim, BigDecimal valorHora){
        return diffHoras(inicio, fim).multiply(valorHora).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
