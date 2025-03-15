package com.example.stage.util;

import java.util.Locale;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

public final class LocaleConstants {
    public static final Locale ARABIC = Locale.forLanguageTag("ar");

    private static final Map<String, Locale> SUPPORTED_LOCALES;

    static {
        Map<String, Locale> locales = new HashMap<>();
        locales.put("English", Locale.ENGLISH);
        locales.put("French", Locale.FRENCH);
        locales.put("German", Locale.GERMAN);
        locales.put("Arabic", ARABIC);
        SUPPORTED_LOCALES = Collections.unmodifiableMap(locales);
    }

    public static Map<String, Locale> getSupportedLocales() {
        return SUPPORTED_LOCALES;
    }

    private LocaleConstants() {
        // Empêcher l'instanciation
    }
}