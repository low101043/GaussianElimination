package com.natlowis.gauss;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.NoSolutionException;

public interface GaussianEliminationInterface {

	public int GCD(int[] integers) throws GCDException;

	public Matrix GaussianElim(Matrix data) throws NoSolutionException;

	public String matrixToVariable(Matrix data);
}
