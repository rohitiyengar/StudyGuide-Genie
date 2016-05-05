package visualization.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Student implements Comparable<Student>{
	
	
	//name of Student
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public  int getOriginalityscore() {
		return originalityscore;
	}
	public void setOriginalityscore(int originalityscore) {
		this.originalityscore = originalityscore;
	}
	public Map<String, String> getValues() {
		if(values == null)
		{
			values = new LinkedHashMap<String, String>();
		}
		
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}
	private String name;
	int originalityscore;
	private Map<String, String> values;
	
	
	@Override
	public int compareTo(Student o) {
		return new Integer(this.getOriginalityscore()).compareTo(new Integer(o.getOriginalityscore()));
	}

}
