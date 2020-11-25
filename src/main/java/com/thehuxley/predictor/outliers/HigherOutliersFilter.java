package com.thehuxley.predictor.outliers;

import java.util.List;

import com.thehuxley.predictor.PredictorTechniqueException;
import com.thehuxley.predictor.Student;
import com.thehuxley.predictor.Util;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.InterquartileRange;

/**
 * This class filters the outliers from the data. However, it removes only the higher outliers.
 * Lower outliers are kept.
 *
 * It implements the interface OutlierFilterable, which has a method to filter out the outliers.
 *
 * @author Marcio Ribeiro
 */
public class HigherOutliersFilter extends InterquartileRange implements OutlierFilterable {

    private static final long serialVersionUID = 1L;

    private Instances instances;
    private List<Student> students;

    private static final double HIGHEST_RANGE_TO_BE_OUTLIER = 0.5;

    public HigherOutliersFilter(Instances instances, List<Student> students) {
        this.instances = instances;
        this.students = students;
    }

    /**
     * Returns the instances without higher outliers. This function keeps the lower outliers, i.e.,
     * it keeps the students VERY likely to fail.
     *
     * The implementation of this function considers that high numbers of the parameters represent a student
     * likely to succeed. In this sense, in case lower numbers of a parameter represent a good characteristic
     * for the student (e.g., number of committed errors), this function will not work properly.
     *
     * @throws PredictorTechniqueException when filtering the outliers.
     *
     * @return the instances filtered without the higher outliers.
     */
    public Instances filterOutliers() throws PredictorTechniqueException {

        if (instances == null || instances.isEmpty()) {
            return null;
        }

        Instances filteredData = null;

        try {

            if (instances.size() < 4) {
                return instances;
            }

            setExtremeValuesAsOutliers(true);
            setInputFormat(instances);

            filteredData = Filter.useFilter(instances, this);

            if (Util.DEBUG) {
                System.out.println("\n====== Before filter ======");
                System.out.println(filteredData);
            }

            for (int i = 0; i < filteredData.numInstances(); i++) {
                Instance instance = filteredData.instance(i);

                if (isOutlier(instance) || isExtremeValue(instance)) {

                    int numberOfAttributes = instance.numAttributes();
                    boolean highOutlier = true;

                    for (int j = 0; j < numberOfAttributes; j++) {

                        if (instance.value(j) < HIGHEST_RANGE_TO_BE_OUTLIER) {
                            highOutlier = false;
                            break;
                        }
                    }

                    if (highOutlier) {

                        if (Util.DEBUG) {
                            System.out.println("\n====== Outlier (will be removed for clustering) ======");
                            System.out.println("Student ID = " + students.get(i).getId());
                        }

                        filteredData.delete(i);
                        students.remove(i);
                    }
                }
            }

            if (Util.DEBUG) {
                System.out.println("\n====== After filter ======");
                System.out.println(filteredData);
            }

        } catch (Exception e) {
            throw new PredictorTechniqueException("Failed to filter the outliers.", e);
        }

        return filteredData;
    }

}