package me.mattstudios.mattscore.utils;

public final class NumberUtils {

    /**
     * @param str String to check if it is a number or not
     * @return Returns true if it is a number false if it is a string or contains any non numeric character
     */
    public static boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    /**
     * @param str String to check if it is a double number or not
     * @return Returns true if it is a number false if it is a string or contains any non numeric character
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
