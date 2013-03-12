/*******************************************************************************
 * Copyright 2011, 2013 Chris Banes.
 * Copyright (C) 2012, 2013 Eyal LEZMY (http://www.eyal.fr)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package fr.eyal.datalib.sample.cache;

import java.util.Map.Entry;
import java.util.Set;

import fr.eyal.lib.util.Out;

import android.support.v4.util.LruCache;

public final class BitmapMemoryLruCache extends LruCache<String, CacheableBitmapDrawable> {

	public BitmapMemoryLruCache(int maxSize) {
		super(maxSize);
		Out.d("", "CACHE SIZE " + maxSize + " Ko");
	}

	public CacheableBitmapDrawable put(CacheableBitmapDrawable value) {
		if (null != value) {
			value.setCached(true);
			return put(value.getUrl(), value);
		}
		return null;
	}

	@Override
	protected int sizeOf(String key, CacheableBitmapDrawable value) {
		Out.d("", "CACHE SIZE ITEM " + key + ": " + value.getMemorySize()/1024 + " Ko max:" + maxSize()/1024 + " size:" + size()/1024);
		return value.getMemorySize();
	}

	@Override
	protected void entryRemoved(boolean evicted, String key, CacheableBitmapDrawable oldValue, CacheableBitmapDrawable newValue) {
		Out.d("", "CACHE REMOVE ITEM " + key);
		// Notify the wrapper that it's no longer being cached
		oldValue.setCached(false);
	}

	void trimMemory() {
		final Set<Entry<String, CacheableBitmapDrawable>> values = snapshot().entrySet();

		for (Entry<String, CacheableBitmapDrawable> entry : values) {
			CacheableBitmapDrawable value = entry.getValue();
			if (null == value || !value.isBeingDisplayed()) {
				remove(entry.getKey());
			}
		}
	}

}
