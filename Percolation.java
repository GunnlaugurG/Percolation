package Percolation;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private boolean[][] grid;
	private int openSites;
	private WeightedQuickUnionUF quick;
	private WeightedQuickUnionUF backWash;
	private int top;
	private int bot;
	private int size;
	
	private int getIndexOfArray(int row, int col) {
		return (grid.length * col) + row +1;
	}
	public Percolation(int N) {
		// create N-by-N grid, with all sites initially blocked
		if(N <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		grid = new boolean[N][N];
		openSites = 0;
		quick = new WeightedQuickUnionUF((N*N)+2);
		backWash = new WeightedQuickUnionUF((N*N)+1);
		top = 0;
		bot = N*N+1;
		size = N;
	}	
	public void open(int row, int col) {
		// open the site (row, col) if it is not open already
		if(row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		if(grid[row][col] != true) {
			grid[row][col] = true;
			openSites++;
		}
		if(row == 0) {
			quick.union(getIndexOfArray(row, col), top);
			backWash.union(getIndexOfArray(row, col), top);
		}
		if(row == size - 1 && !percolates()) {
			quick.union(getIndexOfArray(row, col), bot);
		}
		if(col > 0 && isOpen(row, col - 1)) {
			quick.union(getIndexOfArray(row, col), getIndexOfArray(row, col - 1));
			backWash.union(getIndexOfArray(row, col), getIndexOfArray(row, col - 1));
		}
		if(col < size - 1 && isOpen(row, col + 1)) {
			quick.union(getIndexOfArray(row, col), getIndexOfArray(row, col + 1));
			backWash.union(getIndexOfArray(row, col), getIndexOfArray(row, col + 1));

		}
		if(row > 0 && isOpen(row - 1, col)) {
			quick.union(getIndexOfArray(row, col), getIndexOfArray(row - 1, col));
			backWash.union(getIndexOfArray(row, col), getIndexOfArray(row - 1, col));
		}
		if(row < size - 1 && isOpen(row + 1, col)) {
			quick.union(getIndexOfArray(row, col), getIndexOfArray(row + 1, col));
			backWash.union(getIndexOfArray(row, col), getIndexOfArray(row + 1, col));
		}	
	}
	public boolean isOpen(int row, int col) {
		// is the site (row, col) open?
		if(row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		return grid[row][col];
	}
	public boolean isFull(int row, int col) {
		// is the site (row, col) full?
		if(row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		if(backWash.connected(getIndexOfArray(row, col), top)) {
			return true;
		}
		return false;
	}
	public int numberOfOpenSites() {
		// number of open sites
		return openSites;
	}
	public boolean percolates() {
		if(quick.connected(top, bot)) {
			return true;
		}
		return false;
	} // does the system percolate?
	public static void main(String[] args) {
		// unit testing (required)
		PercolationStats test = new PercolationStats(320, 100);
		StdOut.println("ConH: " + test.confidenceHigh());
		StdOut.println("ConL: " + test.confidenceLow());
		StdOut.println("time: " + test.time());
		
	}
}