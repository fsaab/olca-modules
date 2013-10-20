package org.openlca.core.math;

import java.io.File;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openlca.jblas.Library;

public class PrecisionBenchmarkTest {

	static {
		Library.loadFromDir(new File(System.getProperty("java.io.tmpdir")));
	}

	private final int MAX_SIZE = 7500;
	private final int STEP = 500;
	private final float FACTOR = 1e-6f;
	private final boolean WITH_LOOPS = true;

	@Test
	@Ignore
	public void testDoublePrecision() {
		System.out.println("\nRun benchmark for double precision");
		System.out.println("Matrix size\t Time (ms) \t Success");
		for (int i = STEP; i <= MAX_SIZE; i += STEP) {
			IMatrix matrix = new BlasMatrix(i, i);
			runAndLog(i, matrix);
		}
	}

	@Test
	@Ignore
	public void testSinglePrecision() {
		System.out.println("\nRun benchmark for single precision");
		System.out.println("Matrix size\t Time (ms) \t Success");
		for (int i = STEP; i <= MAX_SIZE; i += STEP) {
			IMatrix matrix = new BlasFloatMatrix(i, i);
			runAndLog(i, matrix);
		}
	}

	private void runAndLog(int i, IMatrix matrix) {
		long start = System.currentTimeMillis();
		double s = runCalculation(i, matrix);
		long end = System.currentTimeMillis();
		boolean success = s == (double) i;
		System.out.printf("%s \t %s \t %s \n", i, end - start, success);
		Assert.assertTrue(success);
	}

	private double runCalculation(int i, IMatrix matrix) {
		for (int row = 0; row < i; row++) {
			for (int col = 0; col < i; col++) {
				if (row == col)
					matrix.setEntry(row, col, 1);
				else if (col < row)
					matrix.setEntry(row, col, -1 * Math.random() * FACTOR);
				else if (WITH_LOOPS)
					matrix.setEntry(row, col, -1 * Math.random() * FACTOR
							* FACTOR);
			}
		}
		IMatrix inverse = matrix.getInverse();
		IMatrix eye = matrix.multiply(inverse);
		double s = 0;
		for (int k = 0; k < i; k++) {
			s += eye.getEntry(k, k);
		}
		return s;
	}

}