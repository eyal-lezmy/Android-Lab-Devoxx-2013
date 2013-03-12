package fr.eyal.datalib.sample.cache;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import fr.eyal.lib.util.Out;

public class SimpleBitmapCache extends LruCache<String, BitmapDrawable> {

	public SimpleBitmapCache(int maxSize) {
		super(maxSize);
		Out.d("", "CACHE SIZE " + maxSize + " Ko");
	}	
	
	@Override
	protected int sizeOf(String key, BitmapDrawable value) {
		Out.d("", "CACHE SIZE ITEM " + key + ": " + value.getBitmap().getByteCount()/1024 + " Ko");
		return value.getBitmap().getByteCount()/1024;
	}
}
