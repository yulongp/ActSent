package javacode.pagerank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SparseMatrix {

	private Map<Integer, Column> matrix;
	private Map<Integer, Column> mat_trans;

	public SparseMatrix(Map<Integer, List<Integer>> nodes) {
		matrix = new HashMap<Integer, Column>();
		for (Entry<Integer, List<Integer>> entry : nodes.entrySet()) {
			int len = entry.getValue().size();
			double value = 1.0 / len;
			int[] ids = new int[len];
			double[] values = new double[len];
			for (int i = 0; i < len; i++) {
				ids[i] = entry.getValue().get(i);
				values[i] = value;
			}
			Column col = new Column(ids, values);
			matrix.put(entry.getKey(), col);
		}

		mat_trans = new HashMap<Integer, Column>();
		for (Entry<Integer, Column> entry : matrix.entrySet()) {
			List<Integer> ids = entry.getValue().getIds();
			List<Double> values = entry.getValue().getValues();
			for (int i = 0; i < ids.size(); i++) {
				if (mat_trans.containsKey(ids.get(i))) {
					mat_trans.get(ids.get(i)).addId(entry.getKey());
					mat_trans.get(ids.get(i)).addVal(values.get(i));
				} else {
					List<Integer> il = new ArrayList<Integer>();
					List<Double> dl = new ArrayList<Double>();
					il.add(entry.getKey());
					dl.add(values.get(i));
					mat_trans.put(ids.get(i), new Column(il, dl));
				}
			}
		}
	}

	public Map<Integer, Column> getMatrix() {
		return mat_trans;
	}

	public Map<Integer, Column> getOrgMatrix() {
		return matrix;
	}
	
	public boolean misNum(int key) {
		if (this.matrix.containsKey(key)) {
			return false;
		}
		else {
			return true;
		}
	}
}
