//package com.thehuxley.predictor;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import weka.clusterers.SimpleKMeans;
//import weka.core.Attribute;
//import weka.core.FastVector;
//import weka.core.Instance;
//import weka.core.Instances;
//
//public class Preliminars {
//
//	public static void main(String[] args) throws Throwable {
//		Attribute numberOfSubmissions = new Attribute("numberOfSubmissions");
//		Attribute numberOfCorrectSubmissions = new Attribute("numberOfCorrectSubmissions");
//		
//		FastVector attributes = new FastVector(2);
//		attributes.addElement(numberOfSubmissions);
//		attributes.addElement(numberOfCorrectSubmissions);
//		
//		Instances data = new Instances("Students Data", attributes, 0);
//		
//		Double[] numberOfCorrectSubmissionsData = {0.67,0.61,0.61,0.56,0.00,0.50,0.28,0.67,0.56,0.50,0.67,1.00,0.83,0.39,0.61,0.61,0.50,0.78,0.44,0.56,0.00,1.00,0.61,0.44,0.44,0.28,0.94,0.39,0.44,0.00,0.00,0.22,0.00,0.83,0.72};
//		List<Double> numberOfCorrectSubmissionsList = new ArrayList<Double>();
//		numberOfCorrectSubmissionsList.addAll(Arrays.asList(numberOfCorrectSubmissionsData));
//
//		Double[] numberOfSubmissionsData = {0.377,0.298,0.474,0.219,0.079,0.596,0.184,0.333,0.307,0.649,0.904,0.482,0.333,0.193,0.781,0.237,0.298,0.632,1.000,0.404,0.000,0.535,0.246,0.175,0.175,0.132,0.228,0.404,0.281,0.053,0.123,0.167,0.079,0.561,0.272};
//		List<Double> numberOfSubmissionsList = new ArrayList<Double>();
//		numberOfSubmissionsList.addAll(Arrays.asList(numberOfSubmissionsData));
//		
//		for (int i = 0; i < numberOfSubmissionsList.size(); i++) {
//			Instance instance = new Instance(2);
//			instance.setValue(numberOfCorrectSubmissions, numberOfCorrectSubmissionsList.get(i));
//			instance.setValue(numberOfSubmissions, numberOfSubmissionsList.get(i));
//			data.add(instance);
//		}
//
//		SimpleKMeans kMeans = new SimpleKMeans();
//		kMeans.setPreserveInstancesOrder(true);
//		kMeans.setNumClusters(3);
//		kMeans.buildClusterer(data);
//		
//		for (int i = 0; i < data.numInstances(); i++) {
//			System.out.println(data.instance(i) + " is in cluster "
//					+ kMeans.clusterInstance(data.instance(i)) );
//		}
//	}
//
//}