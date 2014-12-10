package javacode.calcmatrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.la4j.matrix.Matrices;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CCSMatrix;

public class ComputeAMatrix {
	
	private Matrix P;
	private Matrix F;
	
	public ComputeAMatrix() {
		try {
			P = new CCSMatrix(
					Matrices.asMatrixMarketSource(new FileInputStream(
							"data/Pall.mm")));
			System.out.println(P.columns() + " " + P.rows());
			F = new CCSMatrix(
					Matrices.asMatrixMarketSource(new FileInputStream(
							"data/Fall.mm")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		ComputeAMatrix cam = new ComputeAMatrix();
		Matrix Af = cam.P.transpose().multiply(cam.F).multiply(cam.P);
		Matrix Ap = cam.P.transpose().multiply(cam.P);
		
		Matrix A = Ap.add(Af);
		List<String> AMatrix = new ArrayList<String>();
		int row = A.rows();
		int col = A.columns();
		for (int t = 0; t < row; t++) {
			for (int j = 0; j < col; j++) {
				if (A.get(t, j) != 0.0) {
					AMatrix.add((t+1) + " " + (j+1) + " " + A.get(t, j) + "\n");
				}
			}
			//writer.write("\n");
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					"data/Aall.mm")));
			writer.write("%%MatrixMarket matrix coordinate real general\n");
			writer.write("% rows columns non-zero-values\n");
			writer.write(1878 + " " + 1878 + " " + AMatrix.size() + "\n");
			writer.write("% row column value\n");
			for (String s : AMatrix) {
				writer.write(s);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
