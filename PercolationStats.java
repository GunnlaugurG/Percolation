package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private Percolation perc;
	private double stats[];
	private int numberOfRuns;
	private double timeStats[];

public PercolationStats(int N, int T) {
	// perform T independent experiments on an N-by-N grid
    if (N <= 0 || T <= 0) {
        throw new IllegalArgumentException("Given N <= 0 || T <= 0");
    }
    numberOfRuns = T;
    stats = new double[T];
    timeStats = new double[T];
    for(int i = 0; i < T; i++) {
    	Stopwatch SW = new Stopwatch();
    	perc = new Percolation(N);
    	double opened = 0;
    	while(!perc.percolates()) {
    		int row = StdRandom.uniform(0, N);
    		int col = StdRandom.uniform(0, N);
    		if(!perc.isOpen(row, col)) {    			
    			perc.open(row, col);
    			opened++;
    		}
    	}
    	stats[i] = opened / (N * N);
    	timeStats[i] = SW.elapsedTime();
    }
}
public double mean() {
	// sample mean of percolation threshold
	return StdStats.mean(stats);

}
public double stddev() {
	// sample standard deviation of percolation threshold
	
	return StdStats.stddev(timeStats);
}
public double confidenceLow() {
	// low endpoint of 95% confidence interval
	
	return StdStats.mean(timeStats) - ((1.96 * stddev()) / Math.sqrt(timeStats.length));
}
public double confidenceHigh() {
	// high endpoint of 95% confidence
	return StdStats.mean(timeStats) + ((1.96 * stddev()) / Math.sqrt(timeStats.length));
}
public double time() {
	return StdStats.mean(timeStats);
}
}