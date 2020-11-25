package com.thehuxley.predictor;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thehuxley.predictor.FailingPredictable;
import com.thehuxley.predictor.PredictorTechniqueException;
import com.thehuxley.predictor.ClusteringPredictor;
import com.thehuxley.predictor.InvalidDataException;
import com.thehuxley.predictor.Parameter;
import com.thehuxley.predictor.Student;

public class PredictorTest {

	private List<Student> students = new ArrayList<Student>();
	
	@Before
	public void createStudents() {
		List<Parameter> parameters1 = new ArrayList<>();
		Parameter parameter1a = new Parameter("numberOfSubmissions", 18);
		Parameter parameter2a = new Parameter("numberOfCorrectSubmissions", 17);
		Parameter parameter3a = new Parameter("numberOfLogins", 30);
		parameters1.add(parameter1a);
		parameters1.add(parameter2a);
		parameters1.add(parameter3a);
		Student student1 = new Student(1, parameters1);
		
		List<Parameter> parameters2 = new ArrayList<>();
		Parameter parameter1b = new Parameter("numberOfSubmissions", 19);
		Parameter parameter2b = new Parameter("numberOfCorrectSubmissions", 17);
		Parameter parameter3b = new Parameter("numberOfLogins", 33);
		parameters2.add(parameter1b);
		parameters2.add(parameter2b);
		parameters2.add(parameter3b);
		Student student2 = new Student(2, parameters2);
		
		List<Parameter> parameters3 = new ArrayList<>();
		Parameter parameter1c = new Parameter("numberOfSubmissions", 14);
		Parameter parameter2c = new Parameter("numberOfCorrectSubmissions", 12);
		Parameter parameter3c = new Parameter("numberOfLogins", 25);
		parameters3.add(parameter1c);
		parameters3.add(parameter2c);
		parameters3.add(parameter3c);
		Student student3 = new Student(3, parameters3);
		
		List<Parameter> parameters4 = new ArrayList<>();
		Parameter parameter1d = new Parameter("numberOfSubmissions", 7);
		Parameter parameter2d = new Parameter("numberOfCorrectSubmissions", 5);
		Parameter parameter3d = new Parameter("numberOfLogins", 15);
		parameters4.add(parameter1d);
		parameters4.add(parameter2d);
		parameters4.add(parameter3d);
		Student student4 = new Student(4, parameters4);
		
		List<Parameter> parameters5 = new ArrayList<>();
		Parameter parameter1e = new Parameter("numberOfSubmissions", 6);
		Parameter parameter2e = new Parameter("numberOfCorrectSubmissions", 5);
		Parameter parameter3e = new Parameter("numberOfLogins", 14);
		parameters5.add(parameter1e);
		parameters5.add(parameter2e);
		parameters5.add(parameter3e);
		Student student5 = new Student(5, parameters5);
		
		List<Parameter> parameters6 = new ArrayList<>();
		Parameter parameter1f = new Parameter("numberOfSubmissions", 6);
		Parameter parameter2f = new Parameter("numberOfCorrectSubmissions", 6);
		Parameter parameter3f = new Parameter("numberOfLogins", 14);
		parameters6.add(parameter1f);
		parameters6.add(parameter2f);
		parameters6.add(parameter3f);
		Student student6 = new Student(6, parameters6);
		
		List<Parameter> parameters7 = new ArrayList<>();
		Parameter parameter1g = new Parameter("numberOfSubmissions", 0);
		Parameter parameter2g = new Parameter("numberOfCorrectSubmissions", 0);
		Parameter parameter3g = new Parameter("numberOfLogins", 5);
		parameters7.add(parameter1g);
		parameters7.add(parameter2g);
		parameters7.add(parameter3g);
		Student student7 = new Student(7, parameters7);
		
		List<Parameter> parameters8 = new ArrayList<>();
		Parameter parameter1h = new Parameter("numberOfSubmissions", 1);
		Parameter parameter2h = new Parameter("numberOfCorrectSubmissions", 0);
		Parameter parameter3h = new Parameter("numberOfLogins", 3);
		parameters8.add(parameter1h);
		parameters8.add(parameter2h);
		parameters8.add(parameter3h);
		Student student8 = new Student(8, parameters8);
		
		List<Parameter> parameters9 = new ArrayList<>();
		Parameter parameter1i = new Parameter("numberOfSubmissions", 2);
		Parameter parameter2i = new Parameter("numberOfCorrectSubmissions", 1);
		Parameter parameter3i = new Parameter("numberOfLogins", 4);
		parameters9.add(parameter1i);
		parameters9.add(parameter2i);
		parameters9.add(parameter3i);
		Student student9 = new Student(9, parameters9);
		
		List<Parameter> parameters10 = new ArrayList<>();
		Parameter parameter1j = new Parameter("numberOfSubmissions", 3);
		Parameter parameter2j = new Parameter("numberOfCorrectSubmissions", 0);
		Parameter parameter3j = new Parameter("numberOfLogins", 1);
		parameters10.add(parameter1j);
		parameters10.add(parameter2j);
		parameters10.add(parameter3j);
		Student student10 = new Student(10, parameters10);
		
		students.add(student1);
		students.add(student2);
		students.add(student3);
		students.add(student4);
		students.add(student5);
		students.add(student6);
		students.add(student7);
		students.add(student8);
		students.add(student9);
		students.add(student10);
	}
	
	@Test
	public void failingStudents() throws Exception {
		FailingPredictable clusteringPredictor = new ClusteringPredictor();
		
		List<Student> failingStudents = clusteringPredictor.filterStudentsLikelyToFail(students);
		
		assertEquals(4, failingStudents.size());
		
		List<Long> idsList = new ArrayList<>();
		
		for (int i = 0; i < failingStudents.size(); i++) {
			idsList.add(failingStudents.get(i).getId());
		}

		assertThat(idsList, not(hasItem(1L)));
		assertThat(idsList, not(hasItem(2L)));
		assertThat(idsList, not(hasItem(3L)));
		assertThat(idsList, not(hasItem(4L)));
		assertThat(idsList, not(hasItem(5L)));
		assertThat(idsList, not(hasItem(6L)));
		assertThat(idsList, hasItem(7L));
		assertThat(idsList, hasItem(8L));
		assertThat(idsList, hasItem(9L));
		assertThat(idsList, hasItem(10L));
	}

	@Test
	public void differentNumberOfParameters() {
		List<Parameter> parameters1 = new ArrayList<>();
		Parameter parameter1a = new Parameter("numberOfSubmissions", 18);
		Parameter parameter2a = new Parameter("numberOfCorrectSubmissions", 17);
		parameters1.add(parameter1a);
		parameters1.add(parameter2a);
		Student student1 = new Student(1, parameters1);
		
		List<Parameter> parameters2 = new ArrayList<>();
		Parameter parameter1b = new Parameter("numberOfSubmissions", 19);
		Parameter parameter2b = new Parameter("numberOfCorrectSubmissions", 17);
		Parameter parameter3b = new Parameter("numberOfLogins", 33);
		parameters2.add(parameter1b);
		parameters2.add(parameter2b);
		parameters2.add(parameter3b);
		Student student2 = new Student(2, parameters2);

		List<Student> listOfStudents = new ArrayList<>();
		listOfStudents.add(student1);
		listOfStudents.add(student2);
		
		try {
			ClusteringPredictor clusteringPredictor = new ClusteringPredictor();
			clusteringPredictor.filterStudentsLikelyToFail(listOfStudents);
			fail("Different number of parameters. InvalidDataException expected.");
		} catch (PredictorTechniqueException e) {
			fail("PredictorTechniqueException not expected.");
		} catch (InvalidDataException e) {
		}
	}

	@Test
	public void negativeParameters() {
		List<Parameter> parameters = new ArrayList<>();
		Parameter parameter1 = new Parameter("numberOfSubmissions", -1);
		Parameter parameter2 = new Parameter("numberOfCorrectSubmissions", 10);
		parameters.add(parameter1);
		parameters.add(parameter2);
		try {
			Student student = new Student(1, parameters);
			assertEquals(2, student.getListOfParameters().size());
			fail("Negative parameter. IllegalArgumentException expected.");
		} catch (IllegalArgumentException e) {
		}
	}

}