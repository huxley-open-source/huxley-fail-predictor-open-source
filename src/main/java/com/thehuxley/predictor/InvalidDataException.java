package com.thehuxley.predictor;

/**
 * Exception to represent invalid data (e.g., different number of parameters).
 * 
 * @author Marcio Ribeiro
 */
public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDataException(String message) {
		super(message);
	}

}