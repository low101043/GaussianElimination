package com.natlowis.gauss;

import com.natlowis.gauss.exceptions.GCDException;

public interface GuassianEliminationInterface {

	public int GCD(int[] integers) throws GCDException;

	public Matrix GuassianElim(Matrix data);

	public String matrixToVariable(Matrix data);
}
