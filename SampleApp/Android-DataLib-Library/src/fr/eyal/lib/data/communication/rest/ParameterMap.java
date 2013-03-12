/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.lib.data.communication.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parameter map knows offers convenience methods for chaining add()s
 * as well as URL encoding. 
 * It is a similar class than {@link ContentValues} but it is not final.
 * 
 * @author David M. Chandler, Eyal LEZMY
 */
public class ParameterMap implements Parcelable {

    public static final String TAG = ParameterMap.class.getSimpleName();
    private static final int DEFAULT_SIZE = 5;

    /** Holds the actual values */
    private HashMap<String, String> mValues;

    public ParameterMap() {
        mValues = new HashMap<String, String>(DEFAULT_SIZE);
    }

    /**
     * Creates a set of values copied from the given HashMap. This is used
     * by the Parcel unmarshalling code.
     *
     * @param values the values to start with
     * {@hide}
     */
    private ParameterMap(final HashMap<String, String> values) {
        mValues = values;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof ParameterMap)) {
            return false;
        }
        return mValues.equals(((ParameterMap) object).mValues);
    }

    @Override
    public int hashCode() {
        return mValues.hashCode();
    }

    /**
     * Adds a value to the set.
     *
     * @param key the name of the value to put
     * @param value the data for the value to put
     */
    public void put(final String key, final String value) {
        mValues.put(key, value);
    }

    /**
     * Adds all values from the passed in ContentValues.
     *
     * @param other the ContentValues from which to copy
     */
    public void putAll(final ParameterMap other) {
        mValues.putAll(other.mValues);
    }

    /**
     * Returns the number of values.
     *
     * @return the number of values
     */
    public int size() {
        return mValues.size();
    }

    /**
     * Remove a single value.
     *
     * @param key the name of the value to remove
     */
    public void remove(final String key) {
        mValues.remove(key);
    }

    /**
     * Removes all values.
     */
    public void clear() {
        mValues.clear();
    }

    /**
     * Returns true if this object has the named value.
     *
     * @param key the value to check for
     * @return {@code true} if the value is present, {@code false} otherwise
     */
    public boolean containsKey(final String key) {
        return mValues.containsKey(key);
    }

    /**
     * Get a value. Valid value types are {@link String}, {@link Boolean}, and
     * {@link Number} implementations.
     *
     * @param key the value to get
     * @return the data for the value
     */
    public Object get(final String key) {
        return mValues.get(key);
    }
    
    /**
     * Get the key set
     *
     * @return the Set of parameters names
     */
    public Set<String> keySet() {
        return mValues.keySet();
    }
    
    /**
     * Get the key set
     *
     * @param ordered define the list as ordered or not
     *
     * @return the Set of parameters names
     */
    public SortedSet<String> keySet(boolean ordered) {
        return new TreeSet<String>(mValues.keySet());
    }

    
    /**
     * Returns URL encoded data
     * 
     * @return URL encoded String
     */
    public String urlEncode() {
    	return urlEncode(false);
    }
    
    /**
     * Returns URL encoded data
     * 
     * @param ordered asks to order the parameter keys before building the result String
     *  
     * @return URL encoded String
     */
    public String urlEncode(boolean ordered) {
        StringBuilder sb = new StringBuilder();
        
        Set<String> keys;
        
        if(ordered){
        	keys = (SortedSet<String>) new TreeSet<String>(mValues.keySet());
        } else {
        	keys = mValues.keySet();
        }
        
        for (String key : keys) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key);
            String value = mValues.get(key);
            if (value != null) {
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(value, RequestHandler.UTF8));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Return a URL encoded byte array in UTF-8 charset.
     * 
     * @return URL encoded bytes
     */
    public byte[] urlEncodedBytes() {
        byte[] bytes = null;
        try {
            bytes = this.urlEncode().getBytes(RequestHandler.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static final Parcelable.Creator<ParameterMap> CREATOR = new Parcelable.Creator<ParameterMap>() {
        @Override
        public ParameterMap createFromParcel(final Parcel in) {
            @SuppressWarnings("unchecked")
            HashMap<String, String> values = in.readHashMap(null);
            return new ParameterMap(values);
        }

        @Override
        public ParameterMap[] newArray(final int size) {
            return new ParameterMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeMap(mValues);
    }

}
