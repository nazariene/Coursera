import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
*  To avoid backwash: http://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html
*/
public class Percolation {

    private boolean[][] grid;
    private int openedCount;
    private int n;
    private WeightedQuickUnionUF unionUF;
    private int top = 0;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        bottom = n * n + 1;
        openedCount = 0;
        unionUF = new WeightedQuickUnionUF(this.n * this.n + 2);
        grid = new boolean[this.n][this.n];
    }

    public void open(int row, int col) {
        checkArguments(row, col);

        if (isOpen(row, col)) {
            return;
        }

        if (row == 1) {
            unionUF.union(top, getIndex(row, col));
        }
        if (row == n) {
            unionUF.union(bottom, getIndex(row, col));
        }

        openedCount++;
        grid[row - 1][col - 1] = true;
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            unionUF.union(getIndex(row, col - 1), getIndex(row, col));
        }
        if (col + 1 <= n && isOpen(row, col + 1)) {
            unionUF.union(getIndex(row, col + 1), getIndex(row, col));
        }
        if (row + 1 <= n && isOpen(row + 1, col)) {
            unionUF.union(getIndex(row + 1, col), getIndex(row, col));
        }
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            unionUF.union(getIndex(row - 1, col), getIndex(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkArguments(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        return unionUF.connected(top, getIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openedCount;
    }

    public boolean percolates() {
        return unionUF.connected(top, bottom);
    }

    private void checkArguments(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IndexOutOfBoundsException();
        }
    }


    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    public static void main(String[] args) {


    }
}
