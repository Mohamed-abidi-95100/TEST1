package com.example.stage.util;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ResourceManager {
    private static final Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());
    private static final String BUNDLE_BASE_NAME = "com.example.stage.i18n.messages";
    private static ResourceBundle currentBundle;
    private static Locale currentLocale;
    private static final Map<String, Locale> availableLocales;
    private static final List<LocaleChangeListener> listeners = new ArrayList<>();

    // Initialize available locales
    static {
        availableLocales = new LinkedHashMap<>();
        availableLocales.put("en", new Locale("en")); // English
        availableLocales.put("fr", new Locale("fr")); // French
        availableLocales.put("de", new Locale("de")); // German
        availableLocales.put("ar", new Locale("ar")); // Arabic

        // Initialize with default locale (English)
        setLocale(Locale.ENGLISH);
    }

    // Listener interface for locale changes
    public interface LocaleChangeListener {
        void onLocaleChanged(Locale newLocale);
    }

    /**
     * Sets the locale for the application and loads the corresponding resource bundle
     * @param locale The locale to set
     * @return true if successful, false if the resource bundle couldn't be loaded
     */
    public static boolean setLocale(Locale locale) {
        try {
            ResourceBundle newBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
            currentBundle = newBundle;
            currentLocale = locale;
            notifyListeners();
            LOGGER.info("Successfully loaded resource bundle for locale: " + locale);
            return true;
        } catch (MissingResourceException e) {
            LOGGER.log(Level.WARNING, "Failed to load resource bundle for locale: " + locale
                    + ". Falling back to English.", e);
            // Fallback to English if the requested locale is not available
            if (!locale.equals(Locale.ENGLISH)) {
                return setLocale(Locale.ENGLISH);
            }
            return false;
        }
    }

    /**
     * Sets the locale using language code
     * @param languageCode The language code (e.g., "en", "fr", "de", "ar")
     * @return true if successful, false if the language is not supported
     */
    public static boolean setLocaleByLanguage(String languageCode) {
        Locale locale = availableLocales.get(languageCode.toLowerCase());
        if (locale != null) {
            return setLocale(locale);
        }
        LOGGER.warning("Unsupported language code: " + languageCode);
        return false;
    }

    /**
     * Gets a string from the current resource bundle
     * @param key The key to look up
     * @return The localized string
     */
    public static String getString(String key) {
        try {
            return currentBundle.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warning("Missing resource key: " + key);
            return "!" + key + "!";  // Make missing keys more visible
        }
    }

    /**
     * Gets a formatted string from the current resource bundle
     * @param key The key to look up
     * @param args The arguments to format the string with
     * @return The formatted localized string
     */
    public static String getFormattedString(String key, Object... args) {
        try {
            String pattern = currentBundle.getString(key);
            return String.format(currentLocale, pattern, args);
        } catch (MissingResourceException e) {
            LOGGER.warning("Missing resource key: " + key);
            return "!" + key + "!";
        }
    }

    /**
     * Gets the current locale
     * @return The current locale
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Gets all available locales
     * @return Map of language codes to Locale objects
     */
    public static Map<String, Locale> getAvailableLocales() {
        return Collections.unmodifiableMap(availableLocales);
    }

    /**
     * Checks if a key exists in the current resource bundle
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public static boolean hasKey(String key) {
        return currentBundle.containsKey(key);
    }

    /**
     * Add a listener for locale changes
     * @param listener The listener to add
     */
    public static void addLocaleChangeListener(LocaleChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a locale change listener
     * @param listener The listener to remove
     */
    public static void removeLocaleChangeListener(LocaleChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners of a locale change
     */
    private static void notifyListeners() {
        for (LocaleChangeListener listener : listeners) {
            listener.onLocaleChanged(currentLocale);
        }
    }

    /**
     * Check if the current locale is RTL (Right-to-Left)
     * @return true if the current locale is RTL
     */
    public static boolean isCurrentLocaleRTL() {
        return currentLocale != null &&
                ComponentOrientation.getOrientation(currentLocale).isHorizontal();
    }

    /**
     * Gets the direction (RTL or LTR) CSS class for the current locale
     * @return "rtl" for RTL languages, "ltr" for LTR languages
     */
    public static String getDirectionClass() {
        return isCurrentLocaleRTL() ? "rtl" : "ltr";
    }
}