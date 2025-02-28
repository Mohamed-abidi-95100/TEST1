package com.example.stage.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TranslationUtils {
    public static String getSafeTranslation(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("Translation key not found: " + key + " for locale " + bundle.getLocale());
            return "[" + key + "]";
        }
    }
}