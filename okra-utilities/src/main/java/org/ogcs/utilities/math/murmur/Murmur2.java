/**
 * murmurhash - Pure Java implementation of the Murmur Hash algorithms.
 * Copyright (c) 2014, Sandeep Gupta
 * <p>
 * http://sangupta.com/projects/murmur
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.utilities.math.murmur;

import java.nio.charset.Charset;

/**
 * A pure Java implementation of the Murmur 2 hashing algorithm as presented
 * at <a href="https://sites.google.com/site/murmurhash/">Murmur Project</a>
 *
 * Code is ported from original C++ source at 
 * <a href="https://sites.google.com/site/murmurhash/MurmurHash2.cpp?attredirects=0">
 * MurmurHash2.cpp</a>
 *
 * and the 64-bit version at
 * <a href="https://sites.google.com/site/murmurhash/MurmurHash2_64.cpp?attredirects=0">
 * MurmurHash2_64.cpp</a>
 *
 * @author sangupta
 * @since 1.0
 * @author TinyZ
 */
public class Murmur2 implements MurmurConstants {

    public static long hash(final String data) {
        return hash(data, Charset.forName("utf-8"), DEFAULT_SEED);
    }

    public static long hash(final String data, Charset charset, long seed) {
        byte[] bytes = data.getBytes(charset);
        return hash(bytes, bytes.length, seed);
    }

    /**
     * Compute the Murmur2 hash as described in the original source code.
     *
     * @param data
     *            the data that needs to be hashed
     *
     * @param length
     *            the length of the data that needs to be hashed
     *
     * @param seed
     *            the seed to use to compute the hash
     *
     * @return the computed hash value
     */
    public static long hash(final byte[] data, int length, long seed) {
        final long m = 0x5bd1e995l;
        final int r = 24;

        // Initialize the hash to a 'random' value
        long hash = ((seed ^ length) & UINT_MASK);

        // Mix 4 bytes at a time into the hash
        int length4 = length >>> 2;

        for (int i = 0; i < length4; i++) {
            final int i4 = i << 2;

            long k = (data[i4] & UNSIGNED_MASK);
            k |= (data[i4 + 1] & UNSIGNED_MASK) << 8;
            k |= (data[i4 + 2] & UNSIGNED_MASK) << 16;
            k |= (data[i4 + 3] & UNSIGNED_MASK) << 24;

            k = ((k * m) & UINT_MASK);
            k ^= ((k >>> r) & UINT_MASK);
            k = ((k * m) & UINT_MASK);

            hash = ((hash * m) & UINT_MASK);
            hash = ((hash ^ k) & UINT_MASK);
        }

        // Handle the last few bytes of the input array
        int offset = length4 << 2;
        switch (length & 3) {
            case 3:
                hash ^= ((data[offset + 2] << 16) & UINT_MASK);

            case 2:
                hash ^= ((data[offset + 1] << 8) & UINT_MASK);

            case 1:
                hash ^= (data[offset] & UINT_MASK);
                hash = ((hash * m) & UINT_MASK);
        }

        hash ^= ((hash >>> 13) & UINT_MASK);
        hash = ((hash * m) & UINT_MASK);
        hash ^= hash >>> 15;

        return hash;
    }

    public static long hash64(final String data) {
        return hash64(data, Charset.forName("utf-8"), DEFAULT_SEED);
    }

    public static long hash64(final String data, Charset charset, long seed) {
        byte[] bytes = data.getBytes(charset);
        return hash64(bytes, bytes.length, seed);
    }

    /**
     * Compute the Murmur2 hash (64-bit version) as described in the original source code.
     *
     * @param data
     *            the data that needs to be hashed
     *
     * @param length
     *            the length of the data that needs to be hashed
     *
     * @param seed
     *            the seed to use to compute the hash
     *
     * @return the computed hash value
     */
    public static long hash64(final byte[] data, int length, long seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed & UINT_MASK) ^ (length * m);

        int length8 = length >> 3;

        for (int i = 0; i < length8; i++) {
            final int i8 = i << 3;

            long k = ((long) data[i8] & 0xff) + (((long) data[i8 + 1] & 0xff) << 8)
                    + (((long) data[i8 + 2] & 0xff) << 16) + (((long) data[i8 + 3] & 0xff) << 24)
                    + (((long) data[i8 + 4] & 0xff) << 32) + (((long) data[i8 + 5] & 0xff) << 40)
                    + (((long) data[i8 + 6] & 0xff) << 48) + (((long) data[i8 + 7] & 0xff) << 56);

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        switch (length & 7) {
            case 7:
                h ^= (long) (data[(length & ~7) + 6] & 0xff) << 48;

            case 6:
                h ^= (long) (data[(length & ~7) + 5] & 0xff) << 40;

            case 5:
                h ^= (long) (data[(length & ~7) + 4] & 0xff) << 32;

            case 4:
                h ^= (long) (data[(length & ~7) + 3] & 0xff) << 24;

            case 3:
                h ^= (long) (data[(length & ~7) + 2] & 0xff) << 16;

            case 2:
                h ^= (long) (data[(length & ~7) + 1] & 0xff) << 8;

            case 1:
                h ^= (long) (data[length & ~7] & 0xff);
                h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }

}
