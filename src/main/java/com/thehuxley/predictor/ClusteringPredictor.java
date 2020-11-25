package com.thehuxley.predictor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thehuxley.predictor.outliers.HigherOutliersFilter;
import com.thehuxley.predictor.outliers.OutlierFilterable;
import weka.clusterers.RandomizableClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.*;

/**
 * Clustering predictor that uses the k-means algorithm. It implements the interface
 * FailingPredictable, which has a method to filter out the students not likely to fail. 
 * 
 * @see FailingPredictable
 * 
 * @author Marcio Ribeiro
 */
public class ClusteringPredictor implements FailingPredictable {

	private static final boolean DEBUG = true;
	
	public ClusteringPredictor() {
	}
	
	/**
	 * Creates the instances for clustering. This method also normalizes all parameters contained
	 * in the list of students.
	 * 
	 * @param students the students data.
	 * 
	 * @throws InvalidDataException if the number of parameters of two students is different.
	 * 
	 * @return the instances prepared for clustering.
	 */
	private Instances createInstances(List<Student> students) throws InvalidDataException {

		Map<String, List<Double>> studentsData = prepareStudentsData(students);

		int numberOfParameters = studentsData.keySet().size();
		
		Attribute attribute;
		FastVector attributes = new FastVector(numberOfParameters);
		
		for (Map.Entry<String, List<Double>> entrySet : studentsData.entrySet()) {
			attribute = new Attribute(entrySet.getKey());
			attributes.addElement(attribute);
			
			List<Double> listOfValues = entrySet.getValue();
			
			double min = Collections.min(listOfValues);
			double max = Collections.max(listOfValues);
			double range = max - min;
			
			List<Double> normalizedParameterValues = new ArrayList<>();
			
			for (Double value : listOfValues) {
				double normalizedValue = (value - min) / range; 
				normalizedParameterValues.add(normalizedValue);
			}
			
			studentsData.put(entrySet.getKey(), normalizedParameterValues);
		}

		Collection<List<Double>> values = studentsData.values();
		List<List<Double>> parametersToTranspose = new ArrayList<>(values);
		
		List<List<Double>> transposedParameters = Util.transpose(parametersToTranspose);
		
		Instances data = new Instances("Students Data", attributes, 0);
		
		for (int i = 0; i < transposedParameters.size(); i++) {
		
			List<Double> parametersPerStudent = transposedParameters.get(i);
			Instance instance = new DenseInstance(numberOfParameters);

			for (int j = 0; j < parametersPerStudent.size(); j++) {

				Double value = parametersPerStudent.get(j);
				instance.setValue((Attribute) attributes.elementAt(j), value);
			}
			
			data.add(instance);
		}

		if (Util.OUTLIER && data != null && !data.isEmpty()) {
			OutlierFilterable filteredData = new HigherOutliersFilter(data, students);
			return filteredData.filterOutliers();
		} else {
			return data;
		}
	}
	
	/**
	 * Prepares the data from a list of students to perform normalization. The result consists of
	 * a hash grouped by parameter.
	 * 
	 * Here is an example (ID = student id; P1 and P2 = parameters):
	 * 
	 * ID, P1, P2
	 * 1, 10, 20
	 * 2, 30, 40
	 * 3, 50, 60
	 * 
	 * To normalize the data, it is necessary to group by parameter, not by student. So, the created
	 * hash looks like the following:
	 * 
	 * K = P1; V = {10, 30, 50}
	 * K = P2; V = {20, 40, 60}
	 * 
	 * Now the data is ready for normalization, since it is easy to determine the MAX and MIN values
	 * for each parameter (e.g., max(P1) = 50).
	 * 
	 * @param students the list of students.
	 * 
	 * @throws InvalidDataException if the number of parameters of two students is different.
	 * 
	 * @return a hash grouping the data per parameter (the parameter string is the hash key).
	 */
	private Map<String, List<Double>> prepareStudentsData(List<Student> students)
			throws InvalidDataException {

		int numberOfParameters = students.get(0).getListOfParameters().size();
		
		Map<String, List<Double>> parametersHash = new HashMap<>();
		
		for (Student student : students) {
			List<Parameter> listOfParameters = student.getListOfParameters();
			
			if (numberOfParameters != listOfParameters.size()) {
				throw new InvalidDataException(
						"Two Student objects have a different number of parameters: " +
								numberOfParameters + " != " + listOfParameters.size());
			}
			
			for (Parameter parameter : listOfParameters) {
				List<Double> values;
				
				if (parametersHash.get(parameter.getName()) == null) {
					values = new ArrayList<>();
				} else {
					values = parametersHash.get(parameter.getName());	
				}
				
				values.add(parameter.getValue());
				parametersHash.put(parameter.getName(), values);
			}
		}
		
		return parametersHash;
	}

	/**
	 * Gets the clusters according to the provided data. This method uses the k-means algorithm,
	 * but also uses the SimpleKMeans superclass to facilitate different clustering implementations.
	 * 
	 * @param data the data to be clustered.
	 * 
	 * @throws PredictorTechniqueException when the clusters building raises a problem.
	 * 
	 * @return the clusters.
	 */
	private RandomizableClusterer getClusters(Instances data) throws PredictorTechniqueException {
		SimpleKMeans kMeans = new SimpleKMeans();
		
		try {
			kMeans.setPreserveInstancesOrder(true);
			kMeans.setNumClusters(3);
			kMeans.buildClusterer(data);
			
			if (DEBUG) {
				for (int i = 0; i < data.numInstances(); i++) {
					System.out.println(data.instance(i) + " is in cluster "
							+ kMeans.clusterInstance(data.instance(i)));
				}
			}
		} catch (Exception e) {
			throw new PredictorTechniqueException("Problem during the clustering building", e);
		}
		
		return kMeans;
	}
	
	@Override
	public List<Student> filterStudentsLikelyToFail(List<Student> students) throws InvalidDataException, PredictorTechniqueException {
		Instances data = createInstances(students);

		if (data == null) {
			return new ArrayList<>();
		}

		SimpleKMeans clusterer = (SimpleKMeans) getClusters(data);

		double maxCentroidValue = 1;
		int failingStudentsCluster = Integer.MAX_VALUE;
		
		Instances clusterCentroids = clusterer.getClusterCentroids();

		for (int i = 0; i < clusterCentroids.numInstances(); i++) {
		    Instance centroid = clusterCentroids.instance(i);
		    double value = centroid.value(0);
		    
		    if (value < maxCentroidValue) {
		    	maxCentroidValue = value;
		    	failingStudentsCluster = i;
		    }
		    
		    if (DEBUG) {
		    	System.out.println( "Value for centroid " + i + ": " + value );
		    }
		}
		
		if (DEBUG) {
			System.out.println("Failing student cluster = " + failingStudentsCluster);
		}
		
		try {
			int i = 0;
			Iterator<Student> iterator = students.iterator();

			while (iterator.hasNext()) {
				iterator.next();
				if (clusterer.clusterInstance(data.instance(i)) != failingStudentsCluster) {
					iterator.remove();
				}
				i++;
			}
		} catch (Exception e) {
			throw new PredictorTechniqueException("Problem when accessing the cluster instances", e);
		}
		
		return students;
	}

}