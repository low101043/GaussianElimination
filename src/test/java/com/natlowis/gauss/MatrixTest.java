package com.natlowis.gauss;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.natlowis.gauss.exceptions.GCDException;
import com.natlowis.gauss.exceptions.NoSolutionException;



public class MatrixTest {
	
	private static final Logger logger = Logger.getLogger(MatrixTest.class);

	@Test
	public void makeObject() {
		
		Matrix m = new Matrix(2, 2);
		assertSame(m, m);
		int[] arrayToUse = {0,0};
		for (int[] element: m.matrix()) {
			assertArrayEquals(element, arrayToUse);
		}
		
	}

	@Test
	public void change() {
		Matrix m = new Matrix(2,3);
		int[] arrayToUse = {0,0, 0};
		for (int[] element: m.matrix()) {
			assertArrayEquals(element, arrayToUse);
		}
		
		m.add(0, 2,3);
		int i =0;
		for (int[] element: m.matrix()) {
			for (int j: element) {
				logger.debug(j);
			}
			if (i ==0) {
				int[] a = {0,0,3};
				assertArrayEquals(element, a);
				
			}
			else {
			assertArrayEquals(element, arrayToUse);}
			i++;
		}
	}
	
	@Test
	public void sizeTest() {
		Matrix m = new Matrix(6,8);
		int size = m.size();
		assertEquals(48, size);
	}
	
	@Test
	public void gcd() throws GCDException {
		GaussianEliminationInterface a = new GaussianElimination();
		int[] data1 = {44, 65, 93};
		int[] data2 = {44, 62, 96};
		int[] data3 = {80, 65, 90, 85};
		int[] data4 = {1066, 1014, 12649, 52, 13, 10192};
		int[] data5 = {10, 5};
		int[] data6 = {-10, -20};
		
		assertEquals("The data", 1, a.GCD(data1));
		assertEquals(2, a.GCD(data2));
		assertEquals(5, a.GCD(data3));
		assertEquals(13, a.GCD(data4));
		assertEquals(5, a.GCD(data5));
		assertEquals(-10, a.GCD(data6));
	}
	
	@Test(expected=GCDException.class)
	public void gcd_error() throws GCDException {
		GaussianEliminationInterface a = new GaussianElimination();
		int[] data1 = {55,7,0};
		assertEquals(1, a.GCD(data1));
	}
	
	@Test
	public void guassianElim_normal_test() throws NoSolutionException {
		GaussianEliminationInterface elim = new GaussianElimination();
		
		int[][] data = {
						{1,5,-2,-11},
						{3,-2,7,5},
						{-2,-1,-1,0}
						};
		Matrix a = new Matrix(data);
		Matrix b = elim.GaussianElim(a);
		int[][] answer = {	
							{1, 0,0,2},
							{0,1,0,-3},
							{0,0,-1,1}
							};
		assertArrayEquals(answer, b.matrix());
		//////////////////////////////////////////////////////////////
		int[][] data2 = {
						{3,1,5,3},
						{-3,1,-2,-5},
						{3,-1,7,10}
						};
		Matrix c = new Matrix(data2);
		Matrix d = elim.GaussianElim(c);
		int[][] answer2 = {
							{6,0,0,1},
							{0,-2,0,5},
							{0,0,1,1}
							};
		
		assertArrayEquals(answer2, d.matrix());
		
		////////////////////////////////////////////////////////////////
		
		int[][] data3 = {
						{1,2,1,-1},
						{2,-1,1,1},
						{-2,1,-2,2}
						};	
		Matrix e = new Matrix(data3);
		Matrix f = elim.GaussianElim(e);
		
		int[][] answer3 = {
							{1,0,0,2},
							{0,1,0,0},
							{0,0,1,-3}
							};
		assertArrayEquals(answer3, f.matrix());
		/////////////////////////////////////////////////////////////////////
		
		int[][] data4 = {
						{1,-2,0,0,0,0},
						{2,-2,-3,0,0,0},
						{0,4,-3,-4,0,0},
						{0,0,6,-4,-5,0},
						{0,0,0,8,-5,1}
						};
		
		Matrix g = new Matrix(data4);
		Matrix h = elim.GaussianElim(g);
		
		int[][] answer4 = {	
							{1,0,0,0,0,1},
							{0,2,0,0,0,1},
							{0,0,3,0,0,1},
							{0,0,0,4,0,1},
							{0,0,0,0,5,1}
							};
		assertArrayEquals(answer4, h.matrix());
		///////////////////////////////////////////////////////////////
		
		int[][] data5 = {
						{1,1,2,1},
						{1,0,3,2},
						{3,4,4,1}
						};
		
		Matrix i = new Matrix(data5);
		Matrix j = elim.GaussianElim(i);
		
		int[][] answer5 = {
							{-1,0,0,1},
							{0,1,0,0},
							{0,0,1,1}
							};
		assertArrayEquals(answer5, j.matrix());
		////////////////////////////////////////////////////////////
		
		int[][] data6 = {
						{-2,1,0,0,0,1},
						{1,-2,1,0,0,0},
						{0,1,-2,1,0,0},
						{0,0,1,-2,1,0},
						{0,0,0,1,-2,0}
						};
		
		
		
		Matrix k = new Matrix(data6);
		Matrix l = elim.GaussianElim(k);
		
		int[][] answer6 = {
							{6,0,0,0,0,-5},
							{0,3,0,0,0,-2},
							{0,0,-2,0,0,1},
							{0,0,0,-3,0,1},
							{0,0,0,0,-6,1}
							};
		assertArrayEquals(answer6, l.matrix());
		
	}
	
	//TODO Need to add test which moves lines around
	
	@Test(expected=NoSolutionException.class)
	public void no_solution_test() throws NoSolutionException {
		GaussianEliminationInterface elim = new GaussianElimination();
		int[][] data1 = {
						{2,-1,-1,3},
						{1,2,2,-1},
						{3,1,1,3}
						};
		
		Matrix a = new Matrix(data1);
		Matrix b = elim.GaussianElim(a);
		int[][] answer6 = {
				{6,0,0,0,0,-5},
				{0,3,0,0,0,-2},
				{0,0,-2,0,0,1},
				{0,0,0,-3,0,1},
				{0,0,0,0,-6,1}
				};
		assertArrayEquals(answer6, b.matrix());
	}

	@Test(expected=NoSolutionException.class)
	public void no_solution_test_2() throws NoSolutionException {
		GaussianEliminationInterface elim = new GaussianElimination();
		
		int[][] data1 = {
						{2,-2,-1,5},
						{-4,-1,-1,-3},
						{2,3,2,-1}
						};
		Matrix a = new Matrix(data1);
		Matrix b = elim.GaussianElim(a);
		int[][] answer6 = {
				{6,0,0,0,0,-5},
				{0,3,0,0,0,-2},
				{0,0,-2,0,0,1},
				{0,0,0,-3,0,1},
				{0,0,0,0,-6,1}
				};
		assertArrayEquals(answer6, b.matrix());
	}
	
	@Test 
	public void freely_choose_test() throws NoSolutionException {
		GaussianEliminationInterface elim = new GaussianElimination();
		
		int[][] data1 = {
						{-2,2,-1,4},
						{3,2,2,-1},
						{-1,-4,-1,-3}
						};	
		
		Matrix a = new Matrix(data1);
		Matrix b = elim.GaussianElim(a);
		int[][] answer1 = {
							{5,0,3,-5},
							{0,10,1,10},
							//{0,0,0,0}
						 	};
		
		assertArrayEquals(answer1, b.matrix());
		///////////////////////////////////////////////////////////
		
		int[][] data2 = {
						{2,2,1,-1},
						{1,1,3,2},
						{-1,-1,2,3}
						};
		
		Matrix c = new Matrix(data2);
		Matrix d = elim.GaussianElim(c);
		int[][] answer2 = {
							{-1,-1,0,1},
							{0,0,1,1},
							//{0,0,0,0}
							};
		
		assertArrayEquals(answer2, d.matrix());
		/////////////////////////////////////////////////////////////
		
		int[][] data3 = {
						{1,-1,0,2,3},
						{2,0,2,1,4},
						{3,-1,2,4,9}
						};
		Matrix e = new Matrix(data3);
		Matrix f = elim.GaussianElim(e);
		int[][] answer3 = {
							{1,0,1,0,1},
							{0,1,1,0,2},
							{0,0,0,1,2}
							};
		
		assertArrayEquals(answer3, f.matrix());
	}
}
