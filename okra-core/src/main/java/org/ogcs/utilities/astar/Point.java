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

package org.ogcs.utilities.astar;

/**
 * Two-dimensional coordinate point.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/24
 * @since 1.0
 */
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Point that) {
        return distance(that.getX(), that.getY());
    }

    public double distance(int x2, int y2) {
        return distance(getX(), getY(), x2, y2);
    }

    /**
     * This equals method is important for A* Algorithm. {@link AStarAlgorithm#find(Node, Node, int[][], boolean)}
     * <p>
     * {@link Point} are equal if the values of their x and y.
     *
     * @param obj object
     * @return Return true if the obj is equal this. otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point point = (Point) obj;
            return point.getX() == this.getX() && point.getY() == this.getY();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
//        return ((x & 0xFFFF ) << 16) | (y & 0xFFFF); // mine implement
        long bits = Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public String toString() {
        return getClass().getName() + "Point[" + x + ", " + y + "]";
    }

    // static method

    public static double distance(double x1, double y1, double x2, double y2) {
        x2 -= x1;
        y2 -= y1;
        return Math.sqrt(x2 * x2 + y2 * y2);
    }
}
