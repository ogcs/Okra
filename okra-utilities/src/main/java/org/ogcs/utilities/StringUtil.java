/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * String util.
 *
 * @author TinyZ on 2015/5/13.
 * @see java.lang.String
 * @since 1.0
 */
public final class StringUtil {

    /**
     * Concatenate multiple String by {@link StringBuilder}.
     *
     * @param params string parameter
     * @return Return the string, after append.
     */
    public static String concatenate(String... params) {
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param);
        }
        return builder.toString();
    }

    /**
     * Returns a formatted string using the specified format string and arguments.
     * <pre>
     *     format("aaa-{}-bbb", "yyy");                     =>  "aaa-yyy-bbb"
     *     format("aaa-{}{}-bbb", "yyy", "xxx");            =>  "aaa-yyyxxx-bbb"
     *     format("aaa-{}+{}-bbb{", "yyy", "xxx");          =>  "aaa-yyy+xxx-bbb{"
     *     format("aaa-{}+{1}-bbb{", "yyy", "xxx");         =>  "aaa-yyy+xxx-bbb{"
     *     format("aaa-{}+{0}-bbb{", "yyy", "xxx");         =>  "aaa-yyy+yyy-bbb{"
     *     format("aaa-{}+{x}-bbb{", "yyy", "xxx");         =>  "aaa-yyy+{x}-bbb{"
     *     format("aaa-{}-{x}-{}-bbb{", "yyy", "xxx");      =>  "aaa-yyy-{x}-xxx-bbb{"
     *     format("aaa-{}-{{x}-{{}-bbb{", "yyy", "xxx");    =>  "aaa-yyy-{{x}-{xxx-bbb{"
     *     format("aaa-{}-{{x}-{{-1}-bbb{", "yyy", "xxx");  =>  "aaa-yyy-{{x}-{{-1}-bbb{"
     *     format("aaa-{}-{{x}-{{3}-bbb{", "yyy", "xxx");   =>  "aaa-yyy-{{x}-{{3}-bbb{"
     *     format("aaa-{}-{{}-{{}-bbb{", "yyy", "xxx");     =>  "aaa-yyy-{xxx-{{}-bbb{"
     *     format("aaa-{}-{{0}-{{}-bbb-{0}", "yyy", "xxx"); =>  "aaa-yyy-{yyy-{{}-bbb-yyy"
     * </pre>
     *
     * @param str   The string
     * @param args  The arguments
     * @return  Returns formatted string
     */
    public static String format(final String str, Object... args) {
        if (isEmpty(str) || args.length <= 0) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int next = 0;
        int start = 0;
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == '{') {
                int var1 = i + 1;
                boolean hasRight = false;
                for (int k = var1; k < length; k++) {
                    if (str.charAt(k) == '}') {
                        hasRight = true;
                        if (var1 == k) {
                            if (next < 0 || next >= args.length) {
                                break;
                            }
                            sb.append(str.substring(start, var1 - 1));
                            sb.append(args[next]);
                        } else {
                            try {
                                int i1 = Integer.parseInt(str.substring(var1, k));
                                if (i1 < 0 || i1 >= args.length) {
                                    break;
                                }
                                sb.append(str.substring(start, var1 - 1));
                                sb.append(args[i1]);
                            } catch (NumberFormatException e) {
                                break;
                            }
                        }
                        next++;
                        start = k + 1;
                        i = k; // skip
                        break;
                    } else if (str.charAt(k) == '{') {
                        var1 = k + 1;
                    }
                }
                if (!hasRight) {
                    break;
                }
            }
        }
        if (start < str.length() - 1) {
            sb.append(str.substring(start, str.length()));
        }
        return sb.toString();
    }

    /**
     * Split string by special separator char.
     * <pre>
     *     split(",ab,cd,ef,gh", ',') => ["", "ab", "cd", "ef", "gh"]
     *     split(",ab,cd,ef,gh", '|') => [",ab,cd,ef,gh"]
     * </pre>
     *
     * @param str       the String to parse
     * @param separator the character used as the delimiter
     * @return an array of parsed Strings
     */
    public static String[] split(final String str, final char separator) {
        return split(str, separator, 0);
    }

    /**
     * Split string by special separator char.
     * <pre>
     *     split(",ab,cd,ef,gh", ',', 0) => ["", "ab", "cd", "ef", "gh"]
     *     split(",ab,cd,ef,gh", ',', 2) => ["", "ab,cd,ef,gh"]
     *     split("ab,cd,ef,gh", ',', 2) => ["ab", "cd,ef,gh"]
     *     split("ab,cd,ef,gh", ',', 3) => ["ab", "cd", "ef,gh"]
     * </pre>
     *
     * @param str       the String to parse
     * @param separator the character used as the delimiter
     * @param max       the maximum number of elements to include in the array. A zero or negative value implies no limit
     * @return an array of parsed Strings
     */
    public static String[] split(final String str, final char separator, final int max) {
        if (isEmpty(str)) {
            return null;
        }
        final List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                list.add(str.substring(start, i));
                if (max > 1 && list.size() >= max - 1) {
                    break;
                }
                start = i + 1;
            }
        }
        if (start < str.length()) {
            list.add(str.substring(start, str.length()));
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Split string by special separator char.
     * <pre>
     *     splitWithoutEmpty(",ab,cd,ef,gh", ',') => ["ab", "cd", "ef", "gh"]
     *     splitWithoutEmpty("|ab,cd,ef,gh", '|') => ["ab,cd,ef,gh"]
     *     splitWithoutEmpty("ab||cd,ef,gh", '|') => ["ab", "cd,ef,gh"]
     * </pre>
     *
     * @param str       the String to parse
     * @param separator the character used as the delimiter
     * @return an array of parsed Strings
     */
    public static String[] splitWithoutEmpty(final String str, final char separator) {
        return split(str, separator, 0, false);
    }

    /**
     * Split string by special separator char.
     * <pre>
     *     split(",ab,cd,ef,gh", ',', 0, true) => ["", "ab", "cd", "ef", "gh"]
     *     split(",ab,cd,ef,gh", ',', 0, false) => ["ab", "cd", "ef", "gh"]
     *     split(",ab,cd,ef,gh", ',', 2, true) => ["", "ab,cd,ef,gh"]
     *     split("ab,cd,ef,gh", ',', 2, true) => ["ab", "cd,ef,gh"]
     *     split("ab,cd,ef,gh", ',', 3, true) => ["ab", "cd", "ef,gh"]
     * </pre>
     *
     * @param str        the String to parse
     * @param separator  the character used as the delimiter
     * @param max        the maximum number of elements to include in the array. A zero or negative value implies no limit
     * @param allowEmpty is allow element is empty("").
     * @return an array of parsed Strings
     */
    public static String[] split(final String str, final char separator, final int max, final boolean allowEmpty) {
        if (isEmpty(str)) {
            return null;
        }
        final List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                if (!allowEmpty && i - start <= 0) {
                    start = i + 1;
                    continue;
                }
                list.add(str.substring(start, i));
                start = i + 1;
                if (max > 1 && list.size() >= max - 1) {
                    break;
                }
            }
        }
        if (start < str.length()) {
            list.add(str.substring(start, str.length()));
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Check if a CharSequence is null or empty (equals "").
     *
     * @param cs The checked {@link CharSequence}
     * @return Return true if the string is not null and !.equals("").
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * Return an random string
     * @param size array length
     * @param keys The key's datasource
     * @return Return an random string.
     */
    public static String randomString(int size, String... keys) {
        if (keys == null || size < 1) {
            throw new IllegalStateException("Params is wrong");
        }
        StringBuilder builder = new StringBuilder();
        int length = keys.length;
        for (int i = 0; i < size; i++) {
            builder.append(keys[((int) (Math.random() * length))]);
        }
        return builder.toString();
    }

    /**
     * Return an string array with unique string.
     *
     * @param size array length
     * @param prefix   The prefix string.
     * @param length   The completed string's length
     * @param keys     The key's datasource
     * @return Return an array with unique string.
     */
    public static String[] randomUniqueString(int size, String prefix, int length, String keys) {
        String[] data = new String[size];
        int keyLength = length - prefix.length();
        if (size >= Math.pow(keys.length(), keyLength)) {
            throw new IllegalStateException("The parameter keyCount is too large, The max count is " + Math.pow(keys.length(), keyLength));
        }
        Map<String, Integer> map = new HashMap<>();
        while (map.size() < size) {
            StringBuilder builder = new StringBuilder(prefix);
            for (int j = 0; j < keyLength; j++) {
                builder.append(keys.charAt((int) (Math.random() * keys.length())));
            }
            String string = builder.toString();
            if (!map.containsKey(string)) {
                data[map.size()] = string + "\r\n";
                map.put(string, 1);
            }
        }
        return data;
    }
}
