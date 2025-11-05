package com.folhafacil.folhafacil.infra.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataUtils  {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMM/yyyy", new Locale("pt", "BR"));

    public static String formatarMesAno(LocalDate data) {
        if (data == null) return null;
        return data.format(FORMATTER).toLowerCase();
    }
}
