package com.natlowis.gauss;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.IrrelevantEquationException;
import com.natlowis.gauss.exceptions.NoSolutionException;

public class GaussianElimination implements GaussianEliminationInterface {

	private static final Logger logger = Logger.getLogger(GaussianElimination.class);
	private boolean anyIrrelevant;

	@Override
	public int GCD(int[] integers) throws GCDException {

		if (integers.length == 1) {
			return integers[0];
		} else {
			int[] subArray = Arrays.copyOfRange(integers, 1, integers.length);
			int data = integers[0];
			return gcd(data, GCD(subArray));
		}

	}

	@Override
	public Matrix GaussianElim(Matrix data) throws NoSolutionException {

		this.anyIrrelevant = false;
		Matrix goneDown;
		Matrix dataCopy = data.deepCopy();
		try {
			goneDown = GaussianElimDown(data, 0, 0);
			return GaussianElimUp(goneDown, goneDown.rows() - 1, goneDown.columns() - 2);
		} catch (NoSolutionException e) {

			throw e;
		} catch (IrrelevantEquationException e) {
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
				goneDown = GaussianElimDown(newMatrix, 0, 0);
				return GaussianElimUp(goneDown, goneDown.rows() - 1, goneDown.columns() - 3);
			} catch (NoSolutionException e1) {

				throw e1;
			} catch (IrrelevantEquationException e1) {

				e1.printStackTrace();
			}
		}
		return null;

	}

	public Matrix GaussianElimDown(Matrix data, int line, int col)
			throws NoSolutionException, IrrelevantEquationException {
		if (line + 1 == data.rows()) {
			int[] dataForRow = data.row(line);
			int b = dataForRow[dataForRow.length - 1];
			if (b == 0) {
				// TODO This is for Special CaSE 2
				Boolean allZero = true;
				for (int i = 0; i < dataForRow.length - 1; i++) {
					if (dataForRow[i] != 0) {
						allZero = false;
					}
				}
				if (allZero) {
					this.anyIrrelevant = true;
					throw new IrrelevantEquationException();
				} else {
					return data;
				}
			} else {
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
					col = col + 1;
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
				try {
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

				catch (ArrayIndexOutOfBoundsException e) {
					;
				}

			}
			line++;
			col++;
			return GaussianElimDown(data, line, col);
		}
	}

	public Matrix GaussianElimUp(Matrix data, int line, int col) {
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
			int initialData = 0;
			if (this.anyIrrelevant) {
				int[] row = data.row(line);
				for (int i = 1; i < row.length - 1; i++) {
					if (row[i] > 0 && row[i - 1] == 0) {
						initialData = row[i];
						col = i;
					}
				}
				this.anyIrrelevant = false;
			} else {
				int[] row = data.row(line);
				while (row[col - 1] != 0) {
					col--;
				}
				initialData = data.data(line, col);
				
			}

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
			return GaussianElimUp(data, line, col);
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
