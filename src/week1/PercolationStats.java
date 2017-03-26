package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private int n;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        thresholds = new double[this.trials];
        double size = this.n*this.n;
        for (int t = 0; t < this.trials; t++) {
            Percolation perc = new Percolation(this.n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, this.n+1);
                int col = StdRandom.uniform(1, this.n+1);
                perc.open(row, col);
            }
            thresholds[t] = ((float) perc.numberOfOpenSites())/size;
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / (Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / (Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.printf("%24s = %f\n", "mean", stats.mean());
        System.out.printf("%24s = %f\n", "stddev", stats.stddev());
        System.out.printf("%24s = [%f, %f]\n", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());
    }

}
