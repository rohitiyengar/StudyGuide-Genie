package visualization.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Student {
	
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

}
