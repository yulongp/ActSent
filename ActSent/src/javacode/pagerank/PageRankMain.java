package javacode.pagerank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PageRankMain {
	public static int numOfNode = 1878;
	public static int numOfLine;
	public static Map<Integer, List<Integer>> nodeByCol;
	public static double alpha = 0.15;
	public static Set<Integer> misVar;
	public static int iter;

	public static void main(String[] args) throws IOException {
		// Pagerank related data
		nodeByCol = new HashMap<Integer, List<Integer>>();
		numOfLine = 0;

		// read into the transition matrix
		Scanner scan = new Scanner(new File("data/transition.txt"));
		// transition format is #col row value#
		String line = null;
		do {
			numOfLine += 1;
			line = scan.nextLine();
			String[] nums = line.split(" ");

			if (nodeByCol.containsKey(Integer.valueOf(nums[0].trim()))) {
				nodeByCol.get(Integer.valueOf(nums[0].trim())).add(
						Integer.valueOf(nums[1].trim()));
			} else {
				List<Integer> list = new ArrayList<Integer>();
				list.add(Integer.valueOf(nums[1].trim()));
				nodeByCol.put(Integer.valueOf(nums[0].trim()), list);
			}
		} while (scan.hasNext());
		scan.close();

		misVar = new HashSet<Integer>();
		for (int i = 1; i <= PageRankMain.numOfNode; i++) {
			if (!nodeByCol.containsKey(i))
				misVar.add(i);
		}
		System.out.println(misVar.size());

		SparseMatrix transition = new SparseMatrix(nodeByCol);

		double[] p = new double[PageRankMain.numOfNode];
		for (int i = 0; i < PageRankMain.numOfNode; i++) {
			p[i] = 1.0 / PageRankMain.numOfNode;
		}

		long startTime = System.currentTimeMillis();
		// Calculate PageRank
		PageRank pr = new PageRank(alpha, 200, numOfNode);
		pr.calPageRank(transition);
		pr.storePRScore("data/PageRank.txt");
		long endTime = System.currentTimeMillis();
		System.out
				.println("PageRank Run Time: " + (endTime - startTime) + "ms");

	}
}
