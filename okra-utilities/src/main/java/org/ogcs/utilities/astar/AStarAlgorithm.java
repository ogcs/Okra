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

import java.util.*;

/**
 * A* 算法工具
 * 曼哈顿权值算法中,同一节点的H值为固定的
 * F=H+G => G越小，路径越短
 *
 * @author TinyZ
 * @since 1.0
 */
public final class AStarAlgorithm {

    /**
     * vertical and parallel path estimate cost
     */
    private static final int COST_STRAIGHT = 10;
    /**
     * diagonal path estimate cost
     */
    private static final int COST_DIAGONAL = 14;

    /**
     * A* search algorithm
     *
     * @param x1     start point x coordinate
     * @param y1     start point y coordinate
     * @param x2     end point x coordinate
     * @param y2     end point y coordinate
     * @param blocks the tiled map blocks info.
     * @return Return the shortest path from start point to end point. otherwise return empty list.
     */
    public static LinkedList<Point> find(int x1, int y1, int x2, int y2, int[][] blocks) {
        return find(new Node(x1, y1, null), new Node(x2, y2, null), blocks, true);
    }

    /**
     * A* search algorithm
     *
     * @param x1     start point x coordinate
     * @param y1     start point y coordinate
     * @param x2     end point x coordinate
     * @param y2     end point y coordinate
     * @param blocks the tiled map blocks info.
     * @param allowDiagonals is allow diagonal.
     * @return Return the shortest path from start point to end point. otherwise return empty list.
     */
    public static LinkedList<Point> find(int x1, int y1, int x2, int y2, int[][] blocks, boolean allowDiagonals) {
        if (blocks == null) {
            throw new NullPointerException("blocks");
        }
        if (isOutOfBounds(x1, y1, blocks)) {
            throw new IndexOutOfBoundsException("sNode");
        }
        if (isOutOfBounds(x2, y2, blocks)) {
            throw new IndexOutOfBoundsException("eNode");
        }
        return find(new Node(x1, y1, null), new Node(x2, y2, null), blocks, allowDiagonals);
    }

    /**
     * A* search algorithm
     * <p>
     * <strong>Dependency {@link Point#equals} method<strong/>
     */
    private static LinkedList<Point> find(Node sNode, Node eNode, int[][] blocks, boolean allowDiagonals) {
        List<Node> openList = new ArrayList<>();
        Set<Point> checked = new HashSet<>();
        openList.add(sNode);
        LinkedList<Point> paths = new LinkedList<>();
        while (!openList.isEmpty()) {
            Node minNode = openList.remove(0);
            if (minNode.equals(eNode)) {
                while (minNode.getParent() != null) {
                    paths.addFirst(new Point(minNode.getX(), minNode.getY()));
                    minNode = minNode.getParent();
                }
                break;
            }
            List<Node> rounds = rounds(minNode, blocks, allowDiagonals);
            for (Node round : rounds) {
                Node parent = round.getParent();
                Node current = new Node(round.getX(), round.getY(), parent);
                if (blocks[round.getX()][round.getY()] == 0) {
                    checked.add(current);
                    continue;
                } else if (checked.contains(round)) {
                    continue;
                }
                int cost = (parent.getX() == current.getX() || parent.getY() == current.getY()) ? COST_STRAIGHT : COST_DIAGONAL;
                int index;
                if ((index = openList.indexOf(round)) != -1) {
                    if ((parent.getG() + cost) < openList.get(index).getG()) {
                        current.setParent(parent);
                        gn(current, cost);
                        fn(current);
                        openList.set(index, current);
                    }
                } else {
                    current.setParent(parent);
                    gn(current, cost);
                    hn(current, eNode);
                    fn(current);
                    openList.add(current);
                }
            }
            checked.add(minNode);
            Collections.sort(openList, (o1, o2) -> o1.getF() - o2.getF());
        }
        return paths;
    }

    /**
     * @return Return all node which surround the center point.
     */
    private static List<Node> rounds(final Node center, final int[][] blocks, boolean allowDiagonals) {
        List<Node> result = new ArrayList<>();
        if (blocks == null || blocks.length <= 0) {
            return result;
        }
        int centerX = center.getX();
        int centerY = center.getY();
        if (allowDiagonals) { // 8 direction
            for (int i = centerX - 1; i <= centerX + 1; i++) {//    row
                for (int j = centerY - 1; j <= centerY + 1; j++) {//    column
                    if ((centerX == i && centerY == j)
                            || (i < 0 || i >= blocks.length || j < 0 || j >= blocks[0].length)) {
                        continue;
                    }
                    result.add(new Node(i, j, center));
                }
            }
        } else {
            int[][] points = new int[][]{
                    {centerX, centerY - 1},//  up
                    {centerX, centerY + 1},//  down
                    {centerX - 1, centerY},//  left
                    {centerX + 1, centerY},//  right
            };
            for (int[] point : points) {
                if (isOutOfBounds(point[0], point[1], blocks)) {
                    continue;
                }
                result.add(new Node(point[0], point[1], center));
            }
        }
        return result;
    }

    /**
     * Calculate G value.
     * g(n) is the cost of the path from the start node to n.
     *
     * @param node last node
     * @param cost the cost of one step.
     */
    private static void gn(Node node, int cost) {
        node.setG((node.getParent() == null ? 0 : node.getParent().getG()) + cost);
    }

    /**
     * Calculate manhattan distance(H).
     * <p>
     * h(n) is a heuristic that estimates the cost of the cheapest path from n to the goal.
     *
     * @param node  current node.
     * @param eNode the end node.
     */
    private static void hn(Node node, Node eNode) {
        node.setH(Math.abs(node.getX() - eNode.getX()) + Math.abs(node.getY() - eNode.getY()));
    }

    /**
     * Calculate F value.
     * f(n) = g(n) + h(n)
     */
    private static void fn(Node node) {
        node.setF(node.getG() + node.getH());
    }

    /**
     * Is point's position (x,y) out of tiled map's bounds.
     *
     * @return Return true if position out of bounds. otherwise false.
     */
    private static boolean isOutOfBounds(int x, int y, int[][] blocks) {
        return (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length);
    }
}
