package org.ogcs.util;

/**
 * <p>Version utility . </p>
 * <p/>
 * In java ,
 * <p>The long length is 64 bit (8 byte), </p>
 * <p>The short length is 16 bit (2 byte).</p>
 * <p>The char length is 16 bit (2 byte).</p>
 * Most time the short value interval <b>[0, 65535(2<sup>16</sup>-1)]</b> enough to show the version code.<br/>
 * So use shift to split long to 4 short.
 * Just like : <br/>
 * <strong>0xFFFFFFFFFFFFFFFFL => 0xFFFFL, 0xFFFFL, 0xFFFFL, 0xFFFFL<strong/>
 * <br/>
 * The long version code(like 98784968668 = 8 byte) is much smaller than string version code(like "23.10.65500" = 22 byte)
 *
 * @author TinyZ on 2015/5/18.
 */
public final class Version {

    /**
     * A constant holding the minimum value a {@code short} can have. The minimum value 0.
     */
    private static final int MIN_VALUE = 0;

    /**
     * A constant holding the maximum value a {@code Version} can have. The maximum value is 2<sup>16</sup>-1.
     */
    private static final int MAX_VALUE = 65535;

    private Version() {
        // np-op
    }

    /**
     * Convert version code string to long version code
     * <p>Example ("23.10.65500") convert to 98784968668</p>
     * @param codes The string version . example ["2", "23", "6500"]
     * @return The long version code
     */
    public static long version(String codes) {
        return version(codes.split("\\."));
    }

    /**
     * Convert version code string to long version code
     * <p>Example ["23", "10", "65500"] convert to 98784968668</p>
     * @param codes The string version .
     * @return The long version code
     */
    public static long version(String... codes) {
        long version = 0;
        int length = codes.length;
        for (int i = 0; i < length; i++) {
            int code = Integer.parseInt(codes[i]);
            if (code < MIN_VALUE || code > MAX_VALUE) {
                throw new IndexOutOfBoundsException(String.valueOf(codes[i]));
            }
            int i1 = (length - i - 1) * 16;
            version |= (code & 0xffffL) << i1;
        }
        return version;
    }

    /**
     * Convert version int array code to long version code
     * <p>Example [23, 10, 65500] convert to 98784968668</p>
     * @param codes The string version .
     * @return The long version code
     */
    public static long version(int... codes) {
        long version = 0;
        int length = codes.length;
        for (int i = 0; i < length; i++) {
            if (codes[i] < MIN_VALUE || codes[i] > MAX_VALUE) {
                throw new IndexOutOfBoundsException(String.valueOf(codes[i]));
            }
            int i1 = (length - i - 1) * 16;
            version |= (codes[i] & 0xffffL) << i1;
        }
        return version;
    }

    /**
     * Convert long to version string
     *
     * @param version The long value of version information . like
     * @return The version string like "11.2015.880".
     */
    public static String longToVersion(long version) {
        StringBuilder builder = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            long l = (version >> (i * 16)) & 0xffffL;
            if (builder.length() > 0 || l > 0) {
                builder.append(l);
                builder.append(".");
            }
        }
        builder.delete(builder.length() - 1, builder.length());
        return String.valueOf(builder);
    }
}
