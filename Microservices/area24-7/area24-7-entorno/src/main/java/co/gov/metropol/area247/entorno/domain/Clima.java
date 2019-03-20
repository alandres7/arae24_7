package co.gov.metropol.area247.entorno.domain;

import java.io.Serializable;
import java.util.HashMap;

public class Clima implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, String> values;

	public Clima() {
		this.values = new HashMap<>();
	}

	public HashMap<String, String> getValues() {
		return values;
	}



	public void setValues(HashMap<String, String> values) {
		this.values = values;
	}



	public void addValue(String name, String value) {
		this.values.put(name, value);
	}
	
	public String getValue(String name){
		return values.get(name);
	}

}
