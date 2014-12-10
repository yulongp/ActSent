package javacode.calcmatrix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.la4j.matrix.Matrices;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.matrix.sparse.CCSMatrix;

public class ComputeLMatrix {

	private Matrix L;
	private Matrix A;

	public ComputeLMatrix() {
		try {
			A = new CCSMatrix(
					Matrices.asMatrixMarketSource(new FileInputStream(
							"data/Aall.mm")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Matrix getLMatrix() {
		return L;
	}

	public void calLMatrix() {
		int row = A.rows();
		int col = A.columns();
		double[] diag = new double[row];
		double[][] lValues = new double[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				diag[i] += A.get(i, j);
			}
		}
		
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (i == j) {
					lValues[i][j] = diag[i] - A.get(i, j);
				} else {
					lValues[i][j] = -A.get(i, j);
				}
			}
		}
		L = new Basic2DMatrix(lValues);
	}

	public static void main(String[] args) {
		ComputeLMatrix clm = new ComputeLMatrix();
		clm.calLMatrix();
	}
}
