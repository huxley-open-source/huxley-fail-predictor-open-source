package com.thehuxley.predictor;

/**
 * To identify the potential failing students, some data must be analyzed. This class should
 * be used to wrap this data. For each data, clients must provide a name and its respective value.
 * 
 * @author Marcio Ribeiro
 */
public class Parameter {

	private String name;
	
	private double value;
	
	public Parameter(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}
	
}