package com.natlowis.gauss;

/**
 * This class will make an object which acts like a Matrix
 * @author low101043
 *
 */
public class Matrix {
	
	private int[][] matrix;  //This will contain the data from the matrix
	private int row;  //The number of rows
	private int col;  //The number of columns

	/**
	 * This Constructor makes a matrix of size row * col
	 * @param row The number of rows 
	 * @param col The number of cols
	 */
	public Matrix(int row, int col) {

		matrix = new int[row][col];
		this.row = row;
		this.col = col;

		// System.out.println(row);
		// System.out.println(col);

		// this.print();

	}

	/**
	 * This constructor makes a matrix from a 2D Array 
	 * @param data The 2D integer array which has the data in it 
	 */
	public Matrix(int[][] data) {
		this.matrix = data;
		this.row = data.length;
		this.col = data[0].length;

	}

	@Override
	public String toString() {

		String data = "";  //The data to return
		for (int i = 0; i < matrix.length; i++) {  //For each row 
			for (int j = 0; j < matrix[0].length; j++) {  //For each piece of data in the row

				data += matrix[i][j] + ", ";  //Add to data

			}
			data += "\n";  //After each row and new line
		}
		return data;  
	}

	/**
	 * This will add/replace data in a specific position
	 * @param row The row number 
	 * @param col The Column number
	 * @param data The data to add
	 */
	public void add(int row, int col, int data) {

		matrix[row][col] = data;
	}

	/**
	 * This will add/replace data for a row
	 * @param row The row to change
	 * @param rowData The data to add/replace
	 */
	public void add(int row, int[] rowData) {

		matrix[row] = rowData;
	}

	/**
	 * Returns a 2D Array representation of the matrix
	 * @return A 2D array representation of matrix
	 */
	public int[][] matrix() {

		return matrix;
	}

	/**
	 * The size of the matrix
	 * @return The size of the matrix
	 */
	public int size() {

		return row * col;
	}

	/**
	 * This will return a row of data 
	 * @param rowNumber The row to be returned
	 * @return An integer array which has the row of data
	 */
	public int[] row(int rowNumber) {

		int[] rowToBeReturned = matrix[rowNumber];
		// System.out.println(a);
		return rowToBeReturned;

	}

	/**
	 * This will return a column of data 
	 * @param colNumber The column to be returned
	 * @return An integer array which has the column of data
	 */
	public int[] col(int colNumber) {

		int[] colToBeReturned = new int[row]; //Make the integer array

		for (int i = 0; i < row; i++) {  //For each row
			colToBeReturned[i] = matrix[i][colNumber];  //Get the data and add
		}

		return colToBeReturned;
	}

	/**
	 * The number of columns in the {@code Matrix}
	 * @return The number of columns
	 */
	public int columns() {
		return col;
	}
	
	/**
	 * The number of rows in the {@code Matrix}
	 * @return The number of rows
	 */
	public int rows() {

		return row;
	}

	/**
	 * Returns a specific piece of data in the matrix 
	 * @param rowData The row position where the data is
	 * @param colData The column position where the data is
	 * @return The piece of data 
	 */
	public int data(int rowData, int colData) {

		return matrix[rowData][colData];
	}

	/**
	 * Makes a deep copy of the matrix
	 * @return Returns the {@code Matrix} which is the exact same
	 */
	public Matrix deepCopy() {

		Matrix deepCopy = new Matrix(row, col);  //The Matrix to return
		for (int i = 0; i < matrix.length; i++) {  //For each row in the matrix 
			int[] row = matrix[i];  //Get the row 

			deepCopy.add(i, row);  //Add the row
			
		}

		return deepCopy;
	}

}
