package org.ogcs.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TinyZ on 2015/5/13.
 */
public final class StringUtil {

    public static final String lowercaseKeys = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static final String allKeys = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomLowercaseSize6String() {
        return randomString(6, lowercaseKeys);
    }

    /**
     * Generate an unique string
     *
     * @param keyCount Generate CD-key count
     * @param prefix   The CD-key prefix
     * @param length   The completed CD-key's length
     * @param keys     The key's datasource
     * @return An unique CD-key
     */
    public static String[] randomUniqueString(int keyCount, String prefix, int length, String keys) {
        String[] data = new String[keyCount];
        int keyLength = length - prefix.length();
        if (keyCount >= Math.pow(keys.length(), keyLength)) {
            throw new IllegalStateException("The parameter keyCount is too large, The max count is " + Math.pow(keys.length(), keyLength));
        }
        Map<String, Integer> map = new HashMap<>();
        while (map.size() < keyCount) {
            StringBuilder builder = new StringBuilder(prefix);
            for (int j = 0; j < keyLength; j++) {
                builder.append(keys.charAt((int) (Math.random() * keys.length())));
            }
            if (!map.containsKey(builder.toString())) {
                data[map.size()] = builder.append("\r\n").toString();
                map.put(builder.toString(), 1);
            }
        }
        return data;
    }

    public static String randomString(int size, String... keys) {
        if (keys == null || size < 1) {
            throw new IllegalStateException("Params is wrong");
        }
        StringBuilder builder = new StringBuilder();
        int length = keys.length;
        for (int i = 0; i < size; i++) {
            builder.append(keys[((int) (Math.random() * length))]);
        }
        return String.valueOf(builder);
    }

    /**
     * Use {@link StringBuilder} to append string
     *
     * @param params string parameter
     * @return Return the string, after append.
     */
    public static String build(String... params) {
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param);
        }
        return String.valueOf(builder);
    }

    /**
     * Is string is valid string data
     *
     * @param string The string will checked
     * @return Return true if the string is not null and !.equals("").
     */
    public static boolean isValidString(String string) {
        return null != string && string.length() > 0;
    }
}
