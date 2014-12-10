package javacode.sentiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javacode.calcmatrix.ComputeLMatrix;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.Vector;
import org.la4j.vector.sparse.CompressedVector;

public class ActiveFramework {

	private List<Integer> allTweetNum;
	private Matrix trainX;
	private Vector trainY;
	private List<List<Integer>> tweets;
	private int numOfFeats;
	private int numOfMessage;
	private int[] labels;
	private double[] scores;

	private Vector w1;
	private Vector w2;

	public ActiveFramework(String contentFile, String labelFile, int n) {
		numOfFeats = n;
		tweets = new ArrayList<List<Integer>>();
		Scanner scan = null;
		try {
			scan = new Scanner(new File(contentFile));
			String line = null;
			do {
				line = scan.nextLine();
				String[] feats = line.split(" ");
				List<Integer> list = new ArrayList<Integer>();
				for (String s : feats) {
					int i = Integer.valueOf(s);
					list.add(i);
				}
				Collections.sort(list);
				tweets.add(list);
			} while (scan.hasNext());
			scan.close();
			numOfMessage = tweets.size();

			labels = new int[numOfMessage];
			scan = new Scanner(new File(labelFile));
			line = null;
			int index = 0;
			do {
				line = scan.nextLine();
				labels[index] = Integer.valueOf(line) - 1;
				index++;
			} while (scan.hasNext());
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		allTweetNum = new ArrayList<Integer>();
		for (int i = 1; i <= numOfMessage; ++i) {
			allTweetNum.add(i);
		}
		// System.out.println(numOfMessage);
	}

	public List<List<Integer>> getTweets() {
		return tweets;
	}
	
	public Vector getW1() {
		return w1;
	}

	public Vector getW2() {
		return w2;
	}

	public void constuctTrain(int[] rows) {
		double[][] data = new double[numOfMessage][numOfFeats];
		double[] label = new double[numOfMessage];
		for (int i = 0; i < numOfMessage; ++i) {
			label[i] = 0.0;
			for (int j = 0; j < numOfFeats; ++j) {
				data[i][j] = 0.0;
			}
		}
		trainX = new CRSMatrix(data);
		trainY = new CompressedVector(label);

		int num = rows.length;
		for (int i = 0; i < num; ++i) {
			List<Integer> tmp = tweets.get(rows[i]);
			for (Integer in : tmp) {
				trainX.set(rows[i], in, 1.0);
			}
			trainY.set(rows[i], labels[rows[i]]);
		}
	}

	public void addOneInstance(int index) {
		List<Integer> tmp = tweets.get(index);
		for (Integer in : tmp) {
			trainX.set(index, in, 1.0);
		}
		trainY.set(index, labels[index]);
	}

	public void LSRidge(double lambda, int iter) {
		// Matrix tmp = trainX.multiply(trainX.transpose()).add(lambda);
		// MatrixInverter inverter =
		// tmp.withInverter(LinearAlgebra.GAUSS_JORDAN);
		// Matrix inv = inverter.inverse(LinearAlgebra.DENSE_FACTORY);
		// Vector w = inv.multiply(trainX.transpose()).multiply(trainY);
		// return w;
		for (int i = 0; i < iter; ++i) {
			Vector tmp1 = trainX.multiply(trainX.transpose()).multiply(w1);
			Vector tmp2 = trainX.transpose().multiply(trainY);
			Vector tmp3 = w1.multiply(lambda);
			w1 = tmp1.subtract(tmp2).add(tmp3);
		}
	}

	public void LSLap(Matrix L, double lambdaR, double lambdaL, int iter) {
		// Matrix tmp1 = trainX.multiply(trainX.transpose());
		// Matrix tmp2 = trainX.multiply(L).multiply(trainX.transpose())
		// .multiply(lambdaL);
		// Matrix tmp = tmp1.add(tmp2).add(lambdaR);
		// MatrixInverter inverter =
		// tmp.withInverter(LinearAlgebra.GAUSS_JORDAN);
		// Matrix inv = inverter.inverse(LinearAlgebra.DENSE_FACTORY);
		// Vector w = inv.multiply(trainX.transpose()).multiply(trainY);
		// return w;
		for (int i = 0; i < iter; ++i) {
			Vector tmp1 = trainX.multiply(trainX.transpose()).multiply(w2);
			Vector tmp2 = trainX.transpose().multiply(trainY);
			Vector tmp3 = w2.multiply(lambdaR);
			Vector tmp4 = trainX.multiply(L).multiply(trainX.transpose()).multiply(w2);
			w1 = tmp1.subtract(tmp2).add(tmp3).add(tmp4);
		}
	}

	public int[] genRandomNum(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	private int findLargest() {
		double large = 0.0;
		int position = 0;
		for (int i = 0; i < scores.length; ++i) {
			if (scores[i] > large) {
				large = scores[i];
				position = i;
			}
		}
		scores[position] = 0.0;
		return position;
	}

	private void preprocess(int[] init) {
		for (int i = 0; i < init.length; ++i) {
			scores[init[i]] = 0.0;
		}
	}

	public int[] selectScore(String pagerankFile, int[] init, int num) {
		preprocess(init);
		int[] ranking = new int[num];
		scores = new double[numOfMessage];
		Scanner scan = null;
		try {
			scan = new Scanner(new File(pagerankFile));
			String line = null;
			int index = 0;
			do {
				line = scan.nextLine();
				if (line.length() > 0) {
					scores[index] = Double.valueOf(line);
					index++;
				}
			} while (scan.hasNext());
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ranking index is from 0, so it can be used directly to extract tweets
		for (int i = 0; i < num; ++i) {
			ranking[i] = findLargest();
		}

		return ranking;
	}

	public int predict(Vector w, List<Integer> list) {
		int pred = 0;
		double value = 0.0;
		for (Integer i : list) {
			value += w.get(i - 1);
		}
		if (value >= 0.5) {
			pred = 1;
		}
		return pred;
	}

	public static void main(String[] args) {
		ComputeLMatrix clm = new ComputeLMatrix();
		clm.calLMatrix();
		Matrix L = clm.getLMatrix();

		ActiveFramework aframe = new ActiveFramework("data/tweet.txt",
				"data/label.txt", 3289);

		int[] init = aframe.genRandomNum(0, 1878, 78);
		Arrays.sort(init);
		aframe.constuctTrain(init);
		aframe.LSRidge(0.01, 100);
		aframe.LSLap(L, 0.005, 0.01, 100);
		System.out.println(aframe.getW1().length() + " " + aframe.getW2().length());
		int[] ranking = aframe.selectScore("data/PageRank.txt", init, 1200);
		System.out.println(ranking.length);

		List<Integer> instances = new ArrayList<Integer>();
		for (int i : init) {
			instances.add(i);
		}
		int index = 0;
		while (instances.size() < 578 && index < ranking.length) {

			int l1 = aframe.predict(aframe.getW1(), aframe.getTweets().get(ranking[index]));
			int l2 = aframe.predict(aframe.getW2(), aframe.getTweets().get(ranking[index]));
			if (l1 != l2) {
				aframe.addOneInstance(ranking[index]);
				instances.add(ranking[index]);
			}
			index++;
		}
		aframe.LSLap(L, 0.005, 0.01, 100);
		System.out.println("--------------------------");
		for (int i = 0; i < aframe.getW2().length(); ++i) {
			System.out.println(aframe.getW2().get(i));
		}
		System.out.println("--------------------------");
		for (int i : init) {
			System.out.println(i);
		}
		for (int i : ranking) {
			System.out.println(i);
		}
	}
}
