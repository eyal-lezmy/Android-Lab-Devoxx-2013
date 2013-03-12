package fr.eyal.datalib.sample.netflix.fragment;

import java.lang.ref.SoftReference;

import fr.eyal.datalib.sample.netflix.data.model.movieimage.MovieImage;

import android.graphics.Bitmap;

public interface MovieItem {

	/**
     * Get the movie's poster {@link Bitmap}
     * 
     * @param context the context of execution. Needed to have access to the stored images.
     * @param forceCache force to get the image file from the cache if it is not already linked to the {@link SoftReference}
     * 
     * @return return a {@link Bitmap} corresponding to the movie's poster or <code>null</code> whether there is no loaded image.
     */
    public Bitmap getPoster(boolean forceCache);
    
    
    /**
     * Get the movie image file path
     * 
     * @return the image path
     */
    public String getPosterPath();
    
    /**
     * Get the movie image file name
     * 
     * @return the image name
     */
    public String getPosterName();
    
    /**
     * Get the HD image's url of the item
     * 
     * @return the URL needed
     */
    public String getImageUrl();
    
    
    /**
     * Get the label corresponding to the item
     * 
     * @param position the current position of the item on the list
     * 
     * @return the label
     */
    public String getLabel(int position);
    
    
    /**
     * Set the {@link MovieImage} of the item
     * 
     * @param newImage the image to set
     */
    public void setImage(MovieImage newImage);
    
    /**
     * Get the {@link MovieImage} of the item
     * 
     * @return the item image
     */
    public MovieImage getImage();
}
