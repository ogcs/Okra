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

package org.ogcs.utilities.math;

/**
 * Math utilities.
 */
public final class MathUtil {

    /**
     * Calculate two number's greatest common divisor. Use The Euclidean Algorithm
     *
     * @param x number x
     * @param y number y
     * @return the greatest common divisor
     */
    public static int gcd(int x, int y) {
        // Method 1 :
//        if(b == 0)
//            return a;
//        return gcd(b, a % b);
        // Method 2 :
        int temp;
        while (y != 0) {
            temp = x % y;
            x = y;
            y = temp;
        }
        return x;
    }

    /**
     * Return the greatest common divisor of multiple number. Use The Euclidean Algorithm
     *
     * @param ary An integer array
     * @return the greatest common divisor
     */
    public static int gcd(int... ary) {
        if (ary == null || ary.length < 2) {
            throw new IllegalArgumentException("The param ary length must more than 2");
        }
        int y = ary[0];
        for (int i = 1, length = ary.length; i < length; i++) {
            y = gcd(y, ary[i]);
        }
        return y;
    }

    /**
     * Is number A approximate number B.
     *
     * @param a       The number a
     * @param b       The number b
     * @param percent The similar percent
     * @return Return true if the number a is approximate number b. otherwise false.
     */
    public static boolean approximate(double a, double b, double percent) {
        if (percent < 0 || percent > 1) {
            throw new IllegalStateException("The param percent is error");
        }
        return (a * percent <= b) && (a * (2 - percent) <= b);
    }

}
