package javacode.pagerank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PageRank {

	// This class implements the PageRank
	// parameters in this model
	private double alpha;
	// the time of iteration
	private int iter;
	// this array stores the final scores for each document
	private double[] p;
	// the size of documents in the data set
	private int size;

	public PageRank(double d, int t, int s) {
		this.alpha = d;
		this.iter = t;
		this.size = s;
		p = new double[size];
		for (int i = 0; i < size; i++) {
			p[i] = 1.0 / size;
		}
	}

	public double summation(double[] d) {
		double s = 0.0;
		for (int i = 0; i < d.length; i++) {
			s += d[i];
		}
		return s;
	}

	public void normalization(double[] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += d[i];
		}
		for (int i = 0; i < d.length; i++) {
			d[i] = d[i] / sum;
		}
	}

	public double[] calPageRank(SparseMatrix transition) {
		double[] tmp = new double[size];
		for (int i = 0; i < size; i++)
			tmp[i] = p[i];

		double p0 = alpha / size;
		Map<Integer, Column> matrix = transition.getMatrix();
		for (int i = 0; i < iter; i++) {
			for (int j = 1; j <= size; j++) {
				if (matrix.containsKey(j)) {
					double sum = 0.0;
					List<Integer> ids = matrix.get(j).getIds();
					List<Double> values = matrix.get(j).getValues();
					for (int t = 0; t < ids.size(); t++) {
						sum += (1 - alpha) * values.get(t)
								* tmp[ids.get(t) - 1];
						/*System.out.println("--------------------");
						System.out.println(alpha + " " + values.get(t) + " "
						 + " " + ids.get(t) + " " + tmp[ids.get(t) - 1]);
						System.out.println("--------------------");*/
					}
					for (Integer m: PageRankMain.misVar) {
						//System.out.println("++++" + m);
						sum += (1 - alpha) * tmp[m-1] / size;
					}
					p[j-1] = sum + p0;
				} else {
					// p[j] = 1.0 / size;
					double sum = 0.0;
					for (Integer m: PageRankMain.misVar) {
						//System.out.println("++++" + m);
						sum += (1 - alpha) * tmp[m-1] / size;
					}
					p[j-1] = sum + p0;
				}
				// System.out.println(p[j]);
			}
			// normalization(p);
			for (int j = 0; j < size; j++) {
				tmp[j] = p[j];
				//System.out.println(p[j]);
			}
			System.out.println("This is the " + (i + 1) + "th iteration...");
		}
		// normalization(p);
		return p;
	}

	// store the PageRank scores into file
	public void storePRScore(String filename) throws IOException {
		double sum = 0.0;
		// String outFile = "$yulongp-GPR-" + iter + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				filename)));
		for (int i = 0; i < size; i++) {
			// int docid = i + 1;
			writer.write(p[i] + "\n");
			sum += p[i];
		}
		writer.close();
		System.out.println(sum);
	}
}
