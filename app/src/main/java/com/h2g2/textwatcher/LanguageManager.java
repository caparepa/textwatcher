package com.h2g2.textwatcher;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by C. Serrano (cserrano@teravisiontech.com)
 * Teravision Technologies
 * Date: 2018/02/06
 */
public class LanguageManager {

    private static SharedPreferences mSharedData;
    private static String currentLanguage;
    private static Locale currentLocale;
    private static Configuration config;
    private static SharedPreferences.Editor mSharedDataEditor;
    private static DisplayMetrics display;
    public static final Locale defaultLocale = Locale.US;
    public static Map<String, Locale> availableLocales = new TreeMap<>();

    static {
        String[] languageCodes = new String[] {"en", "pt", "es", "ru", "fr", "tr", "uk", "hi",
                "id", "ja", "ko", "th", "vi", "zh_CN", "zh_TW", "ar"};
        Arrays.sort(languageCodes);
        for (String code : languageCodes) {
            Locale locale = parseLocale(code);
            availableLocales.put(getLanguageCode(locale), locale);
        }
    }

    public static void setAppLanguage(Activity context) {
        mSharedData = context.getSharedPreferences(Constants.PREFERENCE_FILE_NAME, 0);
        String defaultLanguage = getLanguageCode(defaultLocale);
        if (mSharedData.contains(Constants.APP_LANGUAGE_PREFERENCE)) {
            currentLanguage = mSharedData.getString(Constants.APP_LANGUAGE_PREFERENCE, defaultLanguage);
        } else {
            currentLanguage = defaultLanguage;
        }

        currentLocale = availableLocales.containsKey(currentLanguage) ? availableLocales.get(currentLanguage) : defaultLocale;

        // Updating Resources
        config = context.getResources().getConfiguration();
        int oldDirection = config.getLayoutDirection();
        config.setLayoutDirection(currentLocale);
        config.locale = currentLocale;
        display = context.getResources().getDisplayMetrics();
        context.getResources().updateConfiguration(config, display);
        context.onConfigurationChanged(config);
        if (config.getLayoutDirection() != oldDirection) {
            context.recreate();
        }
    }

    public static Locale getCurrentLocale(Activity currentActivity) {
        if (currentLocale == null) {
            setAppLanguage(currentActivity);
        }
        return currentLocale;
    }

    private static Locale parseLocale(String code) {
        String[] codeParts = code.split("[_\\-]");
        if (codeParts.length > 2) {
            return new Locale(codeParts[0], codeParts[1], codeParts[2]);
        } else if (codeParts.length > 1) {
            return new Locale(codeParts[0], codeParts[1]);
        } else if (codeParts.length > 0) {
            return new Locale(codeParts[0]);
        } else {
            throw new IllegalArgumentException("Invalid language code");
        }
    }

    public static String getLanguageCode(Locale locale) {
        StringBuilder sb = new StringBuilder();
        sb.append(locale.getLanguage());
        if (locale.getCountry() != null && !locale.getCountry().isEmpty()) {
            sb.append("_");
            sb.append(locale.getCountry());
        }
        return sb.toString();
    }

    public static void saveLanguage(String language, Activity context) {
        mSharedData = context.getSharedPreferences(Constants.PREFERENCE_FILE_NAME, 0);
        mSharedDataEditor = mSharedData.edit();
        mSharedDataEditor.putString(Constants.APP_LANGUAGE_PREFERENCE, language);
        mSharedDataEditor.commit();
        setAppLanguage(context);
    }
}
