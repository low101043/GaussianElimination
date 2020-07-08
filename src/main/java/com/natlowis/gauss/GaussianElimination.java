package com.natlowis.gauss;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.IrrelevantEquationException;
import com.natlowis.gauss.exceptions.NoSolutionException;

/**
 * This will perform Gaussian Elimination
 * @author low101043
 *
 */
public class GaussianElimination implements GaussianEliminationInterface {

	private static final Logger logger = Logger.getLogger(GaussianElimination.class);
	private boolean anyIrrelevant; 	//This variable is used to say if there is any irrelevant equations in the Gaussian ELim

	/**
	 * This is the Constructor.  It does not need to set anything up.
	 */
	public GaussianElimination() {
		;
	}
	
	@Override
	public int GCD(int[] integers) throws GCDException {

		//If there is only 1 integer left then return that
		if (integers.length == 1) {
			return integers[0];
			
		//If there is more than 1 integer left
		} else {
			int[] subArray = Arrays.copyOfRange(integers, 1, integers.length); //Make a subarray without the first integer
			int data = integers[0]; //Take the first one
			return gcd(data, GCD(subArray));  //Use recursion to get hcf with subarray
		}

	}

	@Override
	public Matrix GaussianElim(Matrix data) throws NoSolutionException {

		this.anyIrrelevant = false;  //Sets this to false
		Matrix goneDown;
		Matrix dataCopy = data.deepCopy();  //Makes a copy of the matrix
		try {
			goneDown = GaussianElimDown(data, 0, 0);  //This will perform the first half of Gaussian Elimination 
			return GaussianElimUp(goneDown, goneDown.rows() - 1, goneDown.columns() - 2);  //Will perform the second half
		} catch (NoSolutionException e) {  //If there is no solution throw it up

			throw e;
		} catch (IrrelevantEquationException e) {  //If there is an irrelevant equation
			
			//This will create a new matrix without the irrelevant equation
			int[][] newData = dataCopy.matrix();  
			int[][] newDataForMatrix = new int[newData.length - 1][newData[0].length];
			for (int i = 0; i < newData.length - 1; i++) {
				for (int j = 0; j < newData[0].length; j++) {
					int dataToMove = newData[i][j];
					newDataForMatrix[i][j] = dataToMove;
				}
			}
			Matrix newMatrix = new Matrix(newDataForMatrix);
			
			try {
				goneDown = GaussianElimDown(newMatrix, 0, 0);  //This will perform the first half of Gaussian Elimination 
				return GaussianElimUp(goneDown, goneDown.rows() - 1, goneDown.columns() - 3);  //Will perform the second half
			} catch (NoSolutionException e1) {

				throw e1;
			} catch (IrrelevantEquationException e1) {

				e1.printStackTrace();
			}
		}
		return null;  //ALlows it to be compiled

	}

	/**
	 * This will go down the Matrix in Gaussian Elimination
	 * @param data The {@code Matrix} which contains the data
	 * @param line The line which contains the data to sub
	 * @param col The column number which should be looked at 
	 * @return A {@code Matrix} which has done Gaussian Elimination
	 * @throws NoSolutionException When there is no solution
	 * @throws IrrelevantEquationException When there is an irrelevant equation
	 */
	private Matrix GaussianElimDown(Matrix data, int line, int col)
			throws NoSolutionException, IrrelevantEquationException {
		
		if (line + 1 == data.rows()) {  //If you are at the last row
			int[] dataForRow = data.row(line);  //Get row
			
			int b = dataForRow[dataForRow.length - 1];
			if (b == 0) {  //This will check if  b == 0
				
				Boolean allZero = true; 
				
				for (int i = 0; i < dataForRow.length - 1; i++) {
					if (dataForRow[i] != 0) {
						allZero = false;
					}
				}
				if (allZero) {  //Checks for irrelevant equation
					this.anyIrrelevant = true;
					throw new IrrelevantEquationException();
				} else {
					return data;
				}
			} else {  //This will check if there is no solution
				Boolean allZero = true;
				for (int i = 0; i < dataForRow.length - 1; i++) {
					if (dataForRow[i] != 0) {
						allZero = false;
					}
				}
				if (allZero) {
					throw new NoSolutionException();
				} else {
					return data;
				}
			}
		} else {
			int initialData = data.data(line, col);  //Gets the initial data
			// logger.trace(initialData);

			if (initialData == 0) {  //If the initial data == 0
				int[] colOfData = Arrays.copyOfRange(data.col(col), line, data.columns() - 1);  //Get the column of data below the point we are at

				//This will check if there is any other one with 0
				int posistion = -1;   
				for (int i = 0; i < colOfData.length; i++) {
					logger.trace(colOfData[i]);
					if (colOfData[i] != 0) {
						posistion = i + line;
					}
				}
				if (posistion == -1) {  //If there isn't col increases
					col = col + 1;
				} else {  //If there is move the rows around
					int[] dataToMove = data.row(posistion);
					data.add(posistion, data.row(line));
					data.add(line, dataToMove);
				}
			}
			initialData = data.data(line, col);  //Get the initial data atm
			int colOfData = Arrays.copyOfRange(data.col(col), line, data.columns() - 1).length;  //Get the length

			for (int i = 1; i < colOfData; i++) {  //For each row underneath
				int indexOfRow = i + line;  //Get its index
				try {
					int dataBelow = data.data(indexOfRow, col);  //Get the data below
					int[] rowOfData = new int[data.row(indexOfRow).length - col];  //This will hold the row data at end
					for (int j = col; j < data.row(indexOfRow).length; j++) {  //For each piece of data  
						int dataToChange = data.data(indexOfRow, j);  //Get the data from below
						int dataToSub = data.data(line, j);   //Get data from above
						int newData = (initialData * dataToChange) - (dataBelow * dataToSub);  //Get the new data
						rowOfData[j - col] = newData;  //Add it to rowOfDara
						// data.add(indexOfRow, j, newData);

					}
					int hcf = 0;  //This will try and get the hcf
					try {
						hcf = GCD(rowOfData);
					} catch (GCDException e) {  //If no hcf hcf = 1
						hcf = 1;
					}
					//This will use hcf 
					for (int k = 0; k < rowOfData.length; k++) {  
						int dataToChange = rowOfData[k];
						int dataToAdd = dataToChange / hcf;
						data.add(indexOfRow, col + k, dataToAdd);
					}
				}

				catch (ArrayIndexOutOfBoundsException e) {
					;
				}

			}
			line++;  //Increase line and col and call GaussianElimDown
			col++;
			return GaussianElimDown(data, line, col);
		}
	}

	/**
	 * This will go up the Matrix in Gaussian Elimination
	 * @param data The {@code Matrix} which contains the data
	 * @param line The line which contains the data to sub
	 * @param col The column number which should be looked at 
	 * @return A {@code Matrix} which has done Gaussian Elimination
	 */
	private Matrix GaussianElimUp(Matrix data, int line, int col) {
		
		if (line == 0) {  //If at the top row

			for (int i = 0; i < data.rows(); i++) {  //Got through each row
				ArrayList<Integer> dataAsRow = new ArrayList<Integer>();  //Make a arraylist
				
				for (int j = 0; j < data.columns(); j++) {  //Go throug each piece of data and if it not 0 add it to arraylist
					int dataToCheck = data.data(i, j);
					if (dataToCheck != 0) {
						dataAsRow.add(dataToCheck);
					}
				}
				int[] dataAsArray = new int[dataAsRow.size()];  //Make a integer array and add everything from dataAsRow
				for (int k = 0; k < dataAsArray.length; k++) {
					dataAsArray[k] = dataAsRow.get(k);
				}
				
				//Work out HCF
				int hcf = 0;
				try {
					hcf = GCD(dataAsArray);
				} catch (GCDException e) {
					hcf = 1;
				}
				
				//Go through each piece of data in row and divide by hcf
				for (int j = 0; j < data.columns(); j++) {
					int dataToCheck = data.data(i, j);
					int newData = dataToCheck / hcf;
					data.add(i, j, newData);

				}

			}
			return data;
			
		} else {  //If not last row
			
			int initialData = 0;  //Fund the initialData
			if (this.anyIrrelevant) {  //If there is an irrelevant equation
				
				//Find the correct position to start at
				int[] row = data.row(line);
				for (int i = 1; i < row.length - 1; i++) {
					if (row[i] > 0 && row[i - 1] == 0) {
						initialData = row[i];
						col = i;
					}
				}
				this.anyIrrelevant = false;  //Set irrelevant Equation to false so not done again
			} else {
				int[] row = data.row(line);  //Just get to the right starting posistion
				while (row[col - 1] != 0) {
					col--;
				}
				initialData = data.data(line, col);  //Set intitialData
				
			}

			for (int i = line - 1; i >= 0; i--) {  //Go through each row above
				
				ArrayList<Integer> dataToUseForHCF = new ArrayList<Integer>();  //This is the data which can be used by hcf
				int dataAbove = data.data(i, col); //Get the data above
				for (int j = data.columns() - 1; j > -1; j--) {  //Go through each piece of data in row and change it and add to dataToUseForHCF
					int dataToChange = data.data(i, j);
					int dataToSub = data.data(line, j);
					int newData = (initialData * dataToChange) - (dataAbove * dataToSub);
					data.add(i, j, newData);
					dataToUseForHCF.add(newData);
				}
				int[] dataAsArray = new int[dataToUseForHCF.size()];  //Move from dataToUseForHCF to dataAsArray
				for (int k = 0; k < dataAsArray.length; k++) {
					dataAsArray[k] = dataToUseForHCF.get(k);
				}
				
				//Get HCF
				int hcf = 0;
				try {
					hcf = GCD(dataAsArray);
				} catch (GCDException e) {
					hcf = 1;
				}

				//Go through each piece of data in row and divide by hcf
				for (int j = data.columns() - 1; j > -1; j--) {
					int dataToChange = data.data(i, j);

					int newData = dataToChange / hcf;
					data.add(i, j, newData);
				}

			}

			line--; //Go down line and col and then call GaussianElimUp
			col--;
			return GaussianElimUp(data, line, col);
		}
	}


	/**
	 * Will perform Euclid's Algorithm
	 * @param a The first number
	 * @param b The second number
	 * @return The HCF
	 * @throws GCDException If b == 0
	 */
	private int gcd(int a, int b) throws GCDException {
		
		if (b == 0) {  //If b == 0 throw GCDException
			throw new GCDException();
		}
		
		int x = a; 
		int y = b;

		while (y != 0) {
			int r = x % y;
			x = y;
			y = r;
		}
		return x;
	}
}
