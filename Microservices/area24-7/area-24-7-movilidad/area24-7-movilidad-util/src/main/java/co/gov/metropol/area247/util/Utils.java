package co.gov.metropol.area247.util;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public static final int RANDOM_PASS = 8;

    public static final char[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '@', '.',};

    public static boolean isNull(Object object) {
        if (object != null) {
            if ((object instanceof String) && "".equals(object)) {
                return true;
            } else if ((object instanceof Object[]) && ((Object[]) object).length <= 0) {
                return true;
            } else if ((object instanceof Collection) && ((Collection<?>) object).isEmpty()) {
                return true;
            }
            return false;
        }

        return true;
    }

    public static Object ifNull(Object parameter, Object result) {
        if (isNull(parameter)) {
            return result;
        }
        return parameter;
    }

    public static boolean isNotEmpty(Collection<? extends Object> data) {
        if (!isNull(data) && data.size() > 0) {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(Collection<? extends Object> data) {
        return !isNotEmpty(data);
    }

    public static String uuid() {
        return uuid(null, null);
    }

    public static String uuid(String prefix) {
        return uuid(prefix, null);
    }

    public static String uuid(String prefix, String suffix) {
        return (ifNull(prefix, "")) + UUID.randomUUID().toString() + (ifNull(suffix, ""));
    }

    public static String randomPass() {
        return randomPass(RANDOM_PASS);
    }

    public static String randomPass(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }
    
    public static long longValue(int firstvValue, int secondValue){
    		return Long.parseLong(Integer.toString(firstvValue + secondValue));
    }
}
