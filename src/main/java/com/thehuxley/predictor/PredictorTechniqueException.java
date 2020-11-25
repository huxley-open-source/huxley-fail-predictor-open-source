package com.thehuxley.predictor;

/**
 * Exception to represent a technique execution problem (e.g., building or accessing a cluster,
 * in case the technique used is clustering.
 * 
 * @author Marcio Ribeiro
 */
public class PredictorTechniqueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PredictorTechniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public PredictorTechniqueException(String message) {
		super(message);
	}

}