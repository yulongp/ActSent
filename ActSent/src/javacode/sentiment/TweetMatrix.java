package javacode.sentiment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.la4j.matrix.Matrices;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.matrix.sparse.CCSMatrix;

public class TweetMatrix {
	private Matrix tweet;

	public TweetMatrix(String filename) {
		try {
			tweet = new CCSMatrix(
					Matrices.asMatrixMarketSource(new FileInputStream(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Matrix getTweet() {
		return tweet;
	}

	public Matrix getSubMatrix(List<Integer> list) {
		int size = list.size();
		double[][] subdata = new double[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				subdata[i][j] = tweet.get(list.get(i), list.get(j));
			}
		}
		Matrix sub = new Basic2DMatrix(subdata);
		return sub;
	}
}
