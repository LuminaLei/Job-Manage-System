package jms.gui;

public class Verify {
    public static boolean isInt(String str) {
        return str.matches("^[0-9]+$");
    }

    public static boolean isUrl(String str) {
        return str
                .matches("^[A-Za-z0-9:/_\\\\]+([.][A-Za-z0-9/_@=?'\\\\]+){2,}$");
    }

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
