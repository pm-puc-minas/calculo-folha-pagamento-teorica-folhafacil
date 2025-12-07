package com.folhafacil.folhafacil.infra.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataUtils  {
    private static final DateTimeFormatter FORMATTER_MMM_YYYY =
            DateTimeFormatter.ofPattern("MMM/yyyy", new Locale("pt", "BR"));

    private static final DateTimeFormatter FORMATTER_DD_MM_YYYY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter FORMATTER_DD_MM_YYYY_HH_MM =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));

    public static String formatarMesAno(LocalDate data) {
        if (data == null) return null;
        return data.format(FORMATTER_MMM_YYYY).toLowerCase();
    }

    public static String formatarBrasil(LocalDate data) {
        if (data == null) return null;
        return data.format(FORMATTER_DD_MM_YYYY).toLowerCase();
    }

    public static String fomatarDataHorario(LocalDateTime data){
        if (data == null) return null;
        return data.format(FORMATTER_DD_MM_YYYY_HH_MM);
    }
}
