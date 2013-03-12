package fr.eyal.lib.data.service.model;

import java.util.HashMap;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * This class contains all the requests' options that cannot be simply handled by a <code>int</code>.<br>
 * This is used for example to handle the {@link BitmapFactory.Options} during a network or a cache request.<br>
 * The options are stored on a <code>HashMap&lt;String, Object&gt;</code>. Use the getters to reach the options ou need. 
 * 
 * @author Eyal
 *
 */
public class ComplexOptions extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196582009360012075L;

    /**
     * The key to get the {@link BitmapFactory.Options} object from the {@code complexOptions}
     */
    public static final String COMPLEX_OPTION_BITMAP_FACTORY_OPTION = "bitmap-factory-options";

    /**
     * Put a {@link BitmapFactory.Options} to the complex options
     * 
     * @param options the bitmap options to add
     * 
     * @return the value of any previous mapping with the specified key or <code>null</code> if there was no such mapping. 
     */
    public BitmapFactory.Options putBitmapOptions(BitmapFactory.Options options){
    	return (Options) put(COMPLEX_OPTION_BITMAP_FACTORY_OPTION, options);
    }

    /**
     * Get the {@link BitmapFactory.Options} object from the request
     * 
     * @return the Bitmap options or null if it is not set
     */
    public BitmapFactory.Options getBitmapOptions(){
    	return (BitmapFactory.Options) get(COMPLEX_OPTION_BITMAP_FACTORY_OPTION);
    }
}
