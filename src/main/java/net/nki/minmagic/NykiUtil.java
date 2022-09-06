package net.nki.minmagic;

public class NykiUtil {
    public static Object thisOrDefault(Object a, Object def) {
        if (a==null) {
            return def;
        }
        return a;
    }
}
