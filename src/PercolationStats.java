import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
//    Percolation p;
    private int trials;
    private double[] trialsFrac;
    private double confidence = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
//        p = new Percolation(n);
        this.trials = trials;
        trialsFrac = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                p.open(row, col);
            }
            trialsFrac[i] = (double) (p.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialsFrac);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialsFrac);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (confidence * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (confidence * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 200;
        int trails = 100;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trails = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(n, trails);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() +"," + ps.confidenceHi()+ "]");
    }

}