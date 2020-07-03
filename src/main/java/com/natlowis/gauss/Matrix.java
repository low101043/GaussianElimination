package com.natlowis.gauss;

public class Matrix {
	private int[][] matrix;
	private int row;
	private int col;

	public Matrix(int row, int col) {

		matrix = new int[row][col];
		this.row = row;
		this.col = col;

		// System.out.println(row);
		// System.out.println(col);

		// this.print();

	}

	public Matrix(int[][] data) {
		this.matrix = data;
		this.row = data.length;
		this.col = data[0].length;

	}

	public void print() {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {

				System.out.print(matrix[i][j] + ", ");

			}
			System.out.println("");
		}
	}

	public void add(int row, int col, int data) {

		matrix[row][col] = data;
	}

	public void add(int row, int[] rowData) {

		matrix[row] = rowData;
	}

	public int[][] matrix() {

		return matrix;
	}

	public int size() {

		return row * col;
	}

	public int[] row(int rowNumber) {

		int[] rowToBeReturned = matrix[rowNumber];
		// System.out.println(a);
		return rowToBeReturned;

	}

	public int[] col(int colNumber) {

		int[] colToBeReturned = new int[row];

		for (int i = 0; i < row; i++) {
			colToBeReturned[i] = matrix[i][colNumber];
		}

		return colToBeReturned;
	}

	public int columns() {
		return col;
	}

	public int rows() {

		return row;
	}

	public int data(int rowData, int colData) {

		return matrix[rowData][colData];
	}

	public Matrix deepCopy() {

		Matrix deepCopy = new Matrix(row, col);
		for (int i = 0; i < matrix.length; i++) {
			int[] row = matrix[i];

			for (int j = 0; j < row.length; j++) {
				int data = matrix[i][j];
				deepCopy.add(i, j, data);
			}
		}

		return deepCopy;
	}

}
