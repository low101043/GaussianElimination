package com.natlowis.gauss;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.NoSolutionException;

/**
 * Interface which contains the methods needed for GaussianElimination
 * @author low101043
 *
 */
public interface GaussianEliminationInterface {
	
	/**
	 * This will Work out the highest common factor between the numbers given 
	 * @param integers The integers to find the HCF between
	 * @return The HCF
	 * @throws GCDException  If there is no HCF or a 0 in the integers
	 */
	public int GCD(int[] integers) throws GCDException;

	/**
	 * This will perform Gaussian Elimination
	 * @param data The {@code Matrix} which contains the data to perform Gaussian Elimination on
	 * @return The {@code Matrix} which is simplified
	 * @throws NoSolutionException If there is no solution for the Matrix
	 */
	public Matrix GaussianElim(Matrix data) throws NoSolutionException;

}
