package week4;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] blocks;

    private int hammingDist = -1;

    private int manhattanDist = -1;

    public Board(int[][] blocks) {
        this.blocks = copy(blocks);
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        if (hammingDist == -1) {
            int n = dimension();
            int dist = 0;
            for (int i = 0; i < n * n - 1; i++) {
                int row = i / n;
                int col = i % n;
                if (blocks[row][col] != i + 1)
                    dist += 1;
            }
            hammingDist = dist;
        }
        return hammingDist;
    }

    public int manhattan() {
        if (manhattanDist == -1) {
            int n = dimension();
            int dist = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int val = blocks[i][j];
                    if (val == 0) continue;
                    int row = (val - 1) / n;
                    int col = (val - 1) % n;
                    dist += Math.abs(i - row) + Math.abs(j - col);
                }
            }
            manhattanDist = dist;
        }
        return manhattanDist;
    }

    public boolean isGoal() {
        int n = dimension();
        for (int i = 0; i < n * n - 1; i++) {
            int row = i / n;
            int col = i % n;
            int val = blocks[row][col];
            if (val != i + 1)
                return false;
        }
        return blocks[n - 1][n - 1] == 0;
    }

    public Board twin() {
        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return new Board(exchange(i, j, i, j + 1));
                }
            }
        }
        return new Board(copy(blocks));
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        int row = -1, col = -1, n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (row > 0) {
            neighbors.add(new Board(exchange(row, col, row - 1, col)));
        }
        if (col > 0) {
            neighbors.add(new Board(exchange(row, col, row, col - 1)));
        }
        if (row < n - 1) {
            neighbors.add(new Board(exchange(row, col, row + 1, col)));
        }
        if (col < n - 1) {
            neighbors.add(new Board(exchange(row, col, row, col + 1)));
        }
        return neighbors;
    }

    private int[][] exchange(int rowI, int colI, int rowJ, int colJ) {
        int[][] clones = copy(blocks);
        int temp = clones[rowI][colI];
        clones[rowI][colI] = clones[rowJ][colJ];
        clones[rowJ][colJ] = temp;
        return clones;
    }

    private int[][] copy(int[][] blocks) {
        int n = blocks.length;
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = blocks[i][j];
            }
        }
        return clone;
    }

    public boolean equals(Object y) {
        if ((y == null) || !(y.getClass().equals(Board.class)))
            return false;
        Board yCast = (Board) y;
        if (yCast.dimension() != dimension())
            return false;
        int n = dimension();
        for (int i = 0; i < n * n; i++) {
            int row = i / n;
            int col = i % n;
            if (blocks[row][col] != yCast.blocks[row][col])
                return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = dimension();
        sb.append(dimension()).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append("\t").append(blocks[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }

}
