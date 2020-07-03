package com.natlowis.gauss;

public class DataToReturnFromGauss {

	private Matrix data;
	private boolean whetherHasIrrelevant;

	public DataToReturnFromGauss(Matrix data, boolean irrelevant) {
		this.data = data;
		this.whetherHasIrrelevant = irrelevant;
	}

	public Matrix returnMatrix() {
		return data;
	}

	public boolean returnBool() {
		return this.whetherHasIrrelevant;
	}
}
