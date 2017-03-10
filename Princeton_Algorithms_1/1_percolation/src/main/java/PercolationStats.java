import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;

    private double mean;
    private double dev;
    private double confLo;
    private double confHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        double[] percStats = new double[trials];

        for (int i = 0; i < trials; i++) {
            percStats[i] = performTrial();
        }

        mean = StdStats.mean(percStats);
        dev = StdStats.stddev(percStats);
        confLo = mean - 1.96 * dev / Math.sqrt(trials);
        confHi = mean + 1.96 * dev / Math.sqrt(trials);
    }

    private double performTrial() {
        Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            percolation.open(randomRow, randomCol);
        }

        return percolation.numberOfOpenSites() / (n * n);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return dev;
    }

    public double confidenceLo() {
        return confLo;
    }

    public double confidenceHi() {
        return confHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trial);

        System.out.println("mean    = " + percolationStats.mean());
        System.out.println("stddev  = " + percolationStats.stddev());
        System.out.println("95% confidence interval  = [" +
                percolationStats.confidenceLo() +
                ", " + percolationStats.confidenceHi() + "]");

    }
}