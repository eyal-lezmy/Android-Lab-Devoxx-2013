package fr.eyal.datalib.sample.netflix.util;

import android.graphics.drawable.BitmapDrawable;
import fr.eyal.datalib.sample.cache.BitmapMemoryLruCache;

public class Resources {

	private static Resources mInstance = null;

	/**
	 * The {@link BitmapDrawable} cache of the application
	 */
	public BitmapMemoryLruCache mBitmapCache;
	
	
	public static synchronized Resources getInstance() {
		synchronized (Resources.class) {
			if (mInstance == null) {
				mInstance = new Resources();
			}
			return mInstance;			
		}
	}

	public Resources(){}

	
}
