package fr.eyal.datalib.sample.netflix.fragment.model;

import java.util.ArrayList;
import java.util.Calendar;

public interface MovieItemResponse {

	/**
     * Get the movies
     * 
     * @return the movies list
     */
    public ArrayList<?> getItems();
    
    public Calendar getUpdatedAt();
    
    public int getTtl();
    
}
