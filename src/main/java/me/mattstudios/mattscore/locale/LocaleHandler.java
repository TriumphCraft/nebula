package me.mattstudios.mattscore.locale;

import java.lang.reflect.InvocationTargetException;

public class LocaleHandler {

    private String localeMethod;

    public LocaleHandler() {
        this.localeMethod = "getEnglish";
    }

    public void setLocale(Locales locale) {
        switch (locale) {
            case PT_BR:
                localeMethod = "getPortuguese";
                break;

            default:
                localeMethod = "getEnglish";
                break;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public String getLocaleMessage(Enum enums) {
        try {
            return String.valueOf(enums.getClass().getDeclaredMethod(localeMethod).invoke(enums));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

}
