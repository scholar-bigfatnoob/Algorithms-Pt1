package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private int openSites;
    private boolean[][] grid;
    private WeightedQuickUnionUF ds, auxDs;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        size = n;
        grid = new boolean[n][n];
        ds = new WeightedQuickUnionUF(n*n+2); // +2 since one for start and one for end
        auxDs = new WeightedQuickUnionUF(n*n+1);
    }

    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) return;
        grid[row-1][col-1] = true;
        int dsIndex = getDSIndex(row, col);
        if (row == 1) {
            ds.union(dsIndex, 0);
            auxDs.union(dsIndex, 0);
        }
        if (row == size)
            ds.union(dsIndex, size*size + 1);
        if (row > 1 && isOpen(row - 1, col)) {
            ds.union(dsIndex, getDSIndex(row - 1, col));
            auxDs.union(dsIndex, getDSIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            ds.union(dsIndex, getDSIndex(row + 1, col));
            auxDs.union(dsIndex, getDSIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            ds.union(dsIndex, getDSIndex(row, col - 1));
            auxDs.union(dsIndex, getDSIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            ds.union(dsIndex, getDSIndex(row, col + 1));
            auxDs.union(dsIndex, getDSIndex(row, col + 1));
        }
        openSites++;
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException();
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException();
        int dsIndex = getDSIndex(row, col);
        return isOpen(row, col) && auxDs.connected(0, dsIndex);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return ds.connected(0, size*size + 1);
    }

    private int getDSIndex(int row, int col) {
        return (row - 1) * size + col;
    }
}