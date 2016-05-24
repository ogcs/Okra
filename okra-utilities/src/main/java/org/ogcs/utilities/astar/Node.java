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
 * A * pathfinding algorithm Node.
 */
public class Node extends Point {

    /**
     * current node 's parent node.
     */
    private Node parent;
    /**
     * the cost of move from start point to current point.
     */
    private int g;
    /**
     * the cost of move from current point to end point. h(n) = |x - endX| + |y - endY|
     * 本算法中曼哈顿距离简化为  (忽略障碍物). 效率高于计算两点间距离{@link Point#distance(double, double, double, double)}
     */
    private int h;
    /**
     * f(n) = g(n) + h(n)
     */
    private int f;

    public Node(int x, int y, Node parent) {
        super(x, y);
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
