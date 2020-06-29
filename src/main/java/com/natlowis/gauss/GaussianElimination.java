package com.natlowis.gauss;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.NoSolutionException;

public class GaussianElimination implements GaussianEliminationInterface {

	private static final Logger logger = Logger.getLogger(GaussianElimination.class);

	@Override
	public int GCD(int[] integers) throws GCDException {
		// TODO Auto-generated method stub
		if (integers.length == 1) {
			return integers[0];
		} else {
			int[] subArray = Arrays.copyOfRange(integers, 1, integers.length);
			int data = integers[0];
			return gcd(data, GCD(subArray));
		}

	}

	@Override
	public Matrix GuassianElim(Matrix data) throws NoSolutionException {
		// TODO Auto-generated method stub
		Matrix goneDown = GuassianElimDown(data, 0, 0);
		return GuassianElimUp(goneDown, goneDown.rows() - 1, goneDown.columns() - 2);
	}

	public Matrix GuassianElimDown(Matrix data, int line, int col) throws NoSolutionException {
		// TODO Auto-generated method stub
		if (line + 1 == data.rows()) {
			int[] dataForRow = data.row(line);
			int b = dataForRow[dataForRow.length - 1];
			if (b == 0) {
				//TODO This is for Special CaSE 2
				return data;}
			else {
				Boolean allZero = true; 
				for (int i = 0; i < dataForRow.length - 1; i++) {
					if (dataForRow[i] != 0) {
						allZero = false;
					}
				}
				if (allZero) {
					throw new NoSolutionException();
				}
				else {
					return data;
				}
			}
		} else {
			int initialData = data.data(line, col);
			// logger.trace(initialData);

			if (initialData == 0) {
				int[] colOfData = Arrays.copyOfRange(data.col(col), line, data.columns() - 1);

				int posistion = -1;
				for (int i = 0; i < colOfData.length; i++) {
					logger.trace(colOfData[i]);
					if (colOfData[i] != 0) {
						posistion = i + line;
					}
				}
				if (posistion == -1) {
					return null;
				} else {
					int[] dataToMove = data.row(posistion);
					data.add(posistion, data.row(line));
					data.add(line, dataToMove);
				}
			}
			initialData = data.data(line, col);
			int colOfData = Arrays.copyOfRange(data.col(col), line, data.columns() - 1).length;

			for (int i = 1; i < colOfData; i++) {
				int indexOfRow = i + line;
				int dataBelow = data.data(indexOfRow, col);
				int[] rowOfData = new int[data.row(indexOfRow).length - col];
				for (int j = col; j < data.row(indexOfRow).length; j++) {
					int dataToChange = data.data(indexOfRow, j);
					int dataToSub = data.data(line, j);
					int newData = (initialData * dataToChange) - (dataBelow * dataToSub);
					rowOfData[j - col] = newData;
					// data.add(indexOfRow, j, newData);
				}

				int hcf = 0;
				try {
					hcf = GCD(rowOfData);
				} catch (GCDException e) {
					hcf = 1;
				}
				for (int k = 0; k < rowOfData.length; k++) {
					int dataToChange = rowOfData[k];
					int dataToAdd = dataToChange / hcf;
					data.add(indexOfRow, col + k, dataToAdd);
				}

			}
			line++;
			col++;
			return GuassianElimDown(data, line, col);
		}
	}

	public Matrix GuassianElimUp(Matrix data, int line, int col) {
		if (line == 0) {

			for (int i = 0; i < data.rows(); i++) {
				ArrayList<Integer> dataAsRow = new ArrayList<Integer>();
				for (int j = 0; j < data.columns(); j++) {
					int dataToCheck = data.data(i, j);
					if (dataToCheck != 0) {
						dataAsRow.add(dataToCheck);
					}
				}
				int[] dataAsArray = new int[dataAsRow.size()];
				for (int k = 0; k < dataAsArray.length; k++) {
					dataAsArray[k] = dataAsRow.get(k);
				}
				int hcf = 0;
				try {
					hcf = GCD(dataAsArray);
				} catch (GCDException e) {
					hcf = 1;
				}
				for (int j = 0; j < data.columns(); j++) {
					int dataToCheck = data.data(i, j);
					int newData = dataToCheck / hcf;
					data.add(i, j, newData);

				}

			}
			return data;
		} else {
			int initialData = data.data(line, col);
			
			for (int i = line - 1; i >= 0; i--) {
				ArrayList<Integer> dataToUseForHCF = new ArrayList<Integer>();
				int dataAbove = data.data(i, col);
				for (int j = data.columns() - 1; j > -1; j--) {
					int dataToChange = data.data(i, j);
					int dataToSub = data.data(line, j);
					int newData = (initialData * dataToChange) - (dataAbove * dataToSub);
					data.add(i, j, newData);
					dataToUseForHCF.add(newData);
				}
				int[] dataAsArray = new int[dataToUseForHCF.size()];
				for (int k = 0; k < dataAsArray.length; k++) {
					dataAsArray[k] = dataToUseForHCF.get(k);
				}
				int hcf = 0;
				try {
					hcf = GCD(dataAsArray);
				} catch (GCDException e) {
					hcf = 1;
				}
				
				for (int j = data.columns() - 1; j > -1; j--) {
					int dataToChange = data.data(i, j);

					int newData = dataToChange / hcf;
					data.add(i, j, newData);
				}

			}

			line--;
			col--;
			return GuassianElimUp(data, line, col);
		}
		// TODO Need to go up the thing
	}

	@Override
	public String matrixToVariable(Matrix data) {
		// TODO Auto-generated method stub
		return null;
	}

	private int gcd(int a, int b) throws GCDException {
		if (b == 0) {
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
