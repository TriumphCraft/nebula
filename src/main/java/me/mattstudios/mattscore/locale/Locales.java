package me.mattstudios.mattscore.locale;

public enum Locales {

    EN_EN("en_EN.yml"), PT_BR("pt_BR.yml");

    private String fileName;

    Locales(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
