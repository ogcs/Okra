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


import java.util.Arrays;

/**
 * The Weighted Round-Robin Scheduling Algorithm
 * <br/>
 * <br/>Supposing that there is a server set S = {S0, S1,.., Sn-1};
 * <br/>W(Si) indicates the weight of Si;
 * <br/>i indicates the server selected last time, and i is initialized with -1;
 * <br/>cw is the current weight in scheduling, and cw is initialized with zero;
 * <br/>max(S) is the maximum weight of all the servers in S;
 * <br/>gcd(S) is the greatest common divisor of all server weights in S;
 * <br/>
 * For org.ogcs.okra.example, the real servers, A, B and C, have the weights, 4, 3, 2 respectively, a scheduling sequence will be AABABCABC in a scheduling period
 * (mod sum(Wi)).
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2015/12/25
 * @see <a href="http://kb.linuxvirtualserver.org/wiki/Weighted_Round-Robin_Scheduling">LVS-WRR Algorithm</a>
 */
public final class WeightRoundRobinAlgorithm<T> {

    private int i = -1;
    private int cw = 0;
    private int gcd;
    private int max;
    private int n;

    private int[] weights;
    private T[] servers;

    public WeightRoundRobinAlgorithm(T[] servers, int[] weights) {
        if (servers == null) {
            throw new NullPointerException("servers");
        }
        if (weights == null) {
            throw new NullPointerException("servers");
        }
        this.servers = servers;
        n = servers.length;
        this.weights = weights;
        gcd = MathUtil.gcd(weights);
        max = Arrays.stream(weights).max().getAsInt();
    }

    public T next() {
        while (true) {
            i = (i + 1) % n;
            if (i == 0) {
                cw = cw - gcd;
                if (cw <= 0) {
                    cw = max;
                    if (cw == 0) {
                        return null;
                    }
                }
            }
            if (weights[i] >= cw)
                return servers[i];
        }
    }

    public void release() {
        synchronized (this) {
            servers = null;
            weights = null;
        }
    }
}
