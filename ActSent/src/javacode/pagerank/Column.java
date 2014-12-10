package javacode.pagerank;

import java.util.ArrayList;
import java.util.List;

class Column {
	private List<Integer> ids;
	private List<Double> values;

	public Column(int[] ids, double[] values) {
		this.ids = new ArrayList<Integer>();
		this.values = new ArrayList<Double>();
		for (int i=0;i<ids.length;i++) {
			this.ids.add(ids[i]);
			this.values.add(values[i]);
		}
	}
	
	public Column(List<Integer> ids, List<Double> values) {
		this.ids = new ArrayList<Integer>();
		this.values = new ArrayList<Double>();
		for (int i=0;i<ids.size();i++) {
			this.ids.add(ids.get(i));
			this.values.add(values.get(i));
		}
	}

	public List<Integer> getIds() {
		return ids;
	}

	public List<Double> getValues() {
		return values;
	}

	public int getNum(){
		return this.ids.size();
	}

	public void addId(int id) {
		ids.add(id);
	}
	
	public void addVal(double v){
		values.add(v);
	}
}
