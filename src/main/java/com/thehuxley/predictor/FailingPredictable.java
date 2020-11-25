package com.thehuxley.predictor;

import java.util.List;

/**
 * The clients of this interface can request a list of students likely to fail.
 * 
 * @author Marcio Ribeiro
 */
public interface FailingPredictable {

	/**
	 * Given a list of students, this method changes the list by filtering out the students not
	 * likely to fail the course. Therefore, the returned list will contain only the students
	 * likely to fail.
	 * 
	 * @param students the list of students to be filtered
	 * 
	 * @throws InvalidDataException if the students data is invalid.
	 * @throws PredictorTechniqueException in case the predictor technique execution failed.
	 * 
	 * @return the same list, but only with the students likely to fail.
	 */
	List<Student> filterStudentsLikelyToFail(List<Student> students) throws InvalidDataException, PredictorTechniqueException;
	
}