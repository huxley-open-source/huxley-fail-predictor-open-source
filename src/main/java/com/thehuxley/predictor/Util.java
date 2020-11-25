package com.thehuxley.predictor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains useful methods.
 * 
 * @author Marcio Ribeiro
 */
public class Util {

    public static final boolean DEBUG = false;
    public static final boolean OUTLIER = true;

	/**
	 * Transposes a list of lists.
	 * 
	 * @param table the list of lists to be transposed.
	 * 
	 * @return the transposed list of lists.
	 */
	public static <T> List<List<T>> transpose(List<List<T>> table) {
        List<List<T>> transposed = new ArrayList<>();
        final int n = table.get(0).size();
        
        for (int i = 0; i < n; i++) {
            List<T> column = new ArrayList<>();
            
            for (List<T> row : table) {
            	column.add(row.get(i));
            }
            transposed.add(column);
        }
        return transposed;
    }
	
}