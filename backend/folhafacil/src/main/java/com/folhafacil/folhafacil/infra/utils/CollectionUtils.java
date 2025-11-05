package com.folhafacil.folhafacil.infra.utils;

import java.util.List;

public class CollectionUtils {
    public static <T> void replaceCollection(List<T> target, List<T> source) {
        target.clear();
        if (source != null) target.addAll(source);
    }
}
