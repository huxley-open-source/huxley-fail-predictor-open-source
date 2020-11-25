package com.thehuxley.predictor;

import java.util.List;

/**
 * This class represents the student. A student has an ID and a list of parameters.
 * 
 * No parameter value can be negative. An IllegalArgumentException is thrown in case this happens.
 * 
 * @author Marcio Ribeiro
 */
public class Student {

	private long id;
	
	private List<Parameter> listOfParameters;
	
	public Student(long id, List<Parameter> listOfParameters) {
		this.id = id;
		for (Parameter parameter : listOfParameters) {
			if (parameter.getValue() < 0) {
				throw new IllegalArgumentException("Student parameters cannot have negative values.");
			}
		}
		this.listOfParameters = listOfParameters;
	}

	public long getId() {
		return id;
	}
	
	public List<Parameter> getListOfParameters() {
		return listOfParameters;
	}

}