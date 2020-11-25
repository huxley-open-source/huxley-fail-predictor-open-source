package com.thehuxley.predictor.outliers;


import com.thehuxley.predictor.PredictorTechniqueException;
import weka.core.Instances;

/**
 * The clients of this interface can have outliers filtered out from their data.
 *
 * @author Marcio Ribeiro
 */
public interface OutlierFilterable {

    /**
     * Returns the instances without outliers.
     *
     * @throws PredictorTechniqueException when filtering the outliers.
     *
     * @return the instances filtered without the outliers.
     */
    Instances filterOutliers() throws PredictorTechniqueException;

}