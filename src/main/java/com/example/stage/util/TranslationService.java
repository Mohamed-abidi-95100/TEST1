package com.example.stage.util;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.io.InputStreamReader;
import java.io.IOException;

public class TranslationService {

    /**
     * Charge le fichier de traduction pour une langue spécifique.
     *
     * @param baseName  Le nom de base du fichier d'internationalisation, sans suffixe.
     * @param locale    La locale ciblée (ex : fr, en, de).
     * @return          Le ResourceBundle contenant les traductions.
     */
    public static ResourceBundle loadBundle(String baseName, Locale locale) {
        String resourceName = baseName + "_" + locale.getLanguage() + ".properties";
        try (InputStreamReader reader = new InputStreamReader(
                TranslationService.class.getResourceAsStream("/" + resourceName), StandardCharsets.UTF_8)) {
            return new PropertyResourceBundle(reader);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Impossible de charger le fichier de ressources : " + resourceName, e);
        }
    }

    /**
     * Récupère une traduction basée sur une clé et une locale.
     *
     * @param key       La clé de traduction, telle que définie dans le fichier.
     * @param baseName  Le nom de base du fichier de ressources (ex : "messages").
     * @param locale    La locale ciblée (Langue à utiliser).
     * @return          La traduction associée à la clé ou un texte par défaut si non trouvée.
     */
    public static String getTranslation(String baseName, String key, Locale locale) {
        try {
            ResourceBundle bundle = loadBundle(baseName, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            System.err.println("Erreur de traduction pour la clé : " + key);
            return "[" + key + "]";
        }
    }
}