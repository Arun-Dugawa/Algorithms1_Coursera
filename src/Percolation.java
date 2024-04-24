import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private boolean [][] grid;
    private WeightedQuickUnionUF uFG;
    private WeightedQuickUnionUF uFF;
    private int n;
    private int top;
    private int bottom;
    private int openSites;
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Index out of bounds");
        this.n = n;
        grid = new boolean[n][n];
        uFG = new WeightedQuickUnionUF((n * n) + 2);
        uFF = new WeightedQuickUnionUF((n * n) + 1);
        top = n * n;
        bottom = top + 1;
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValid(row, col)) throw new IllegalArgumentException("Index out of bounds");
        int i = row - 1, j = col - 1;
        int curr = linearIndex(row, col);
        if (isOpen(row, col)) return;
        grid[i][j] = true;
//        top
        if (row == 1) {
            uFG.union(top, curr);
            uFF.union(top, curr);
        }
//        bottom
        if (row == n) {
            uFG.union(bottom, curr);
        }
//        for up
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            int up = linearIndex(row - 1, col);
            uFG.union(curr, up);
            uFF.union(curr, up);
        }
//        for down
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            int down = linearIndex(row + 1, col);
            uFG.union(curr, down);
            uFF.union(curr, down);
        }
//        for left
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            int left = linearIndex(row, col - 1);
            uFG.union(curr, left);
            uFF.union(curr, left);
        }
//        for right
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            int right = linearIndex(row, col + 1);
            uFG.union(curr, right);
            uFF.union(curr, right);
        }

        ++openSites;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(!isValid(row, col)) throw new IllegalArgumentException("Not valid args");
        int i = row - 1, j = col - 1;
        return grid[i][j];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(!isValid(row, col)) throw new IllegalArgumentException("Not valid args");
        int curr = linearIndex(row, col);
        return uFF.find(curr) == uFF.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uFG.find(top) == uFG.find(bottom);
    }
    private boolean isValid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) return false;
        return true;
    }
    private int linearIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }
    // test client (optional)
    public static void main(String[] args) {
//        Percolation p = new Percolation(5);
//        p.open(1, 1);
//        p.open(1, 2);
//        p.open(1, 4);
//        p.open(2, 4);
//        p.open(3, 2);
//        p.open(3, 4);
//        p.open(3, 5);
//        p.open(4, 1);
//        p.open(4, 3);
//        p.open(5, 1);
//        p.open(5, 2);
//        p.open(5, 4);
//        p.open(5, 5);
//        p.open(3, 3);
//        p.open(5, 3);
////        for (int[] e : p.grid) {
////            System.out.println(Arrays.toString(e));
////        }
//        System.out.println(p.percolates());
//        System.out.println(p.isFull(4, 1));
    }
}