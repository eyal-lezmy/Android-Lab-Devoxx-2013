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

package fr.eyal.lib.data.service.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.service.ServiceHelper;

/**
 * @author Eyal LEZMY
 */
public class DataLibRequest {

    public static final String TAG = "DataLibRequest";

    //request's options
    /**
     * No option
     */
    public static final int OPTION_NO_OPTION = 0x00000000;

    /**
     * Ask to conserve the cookies sent by the server
     */
    public static final int OPTION_CONSERVE_COOKIE_ENABLED = 0x00000002;

    /**
     * Ask to NOT conserve the cookies sent by the server
     */
    public static final int OPTION_CONSERVE_COOKIE_DISABLED = 0x00000001;

    /**
     * Ask to use the Parcelable method to send the information between the DataLibService
     * and the ServiceHelper.
     */
    public static final int OPTION_SEND_WITH_PARCELABLE_ENABLED = 0x00000200;

    /**
     * Ask to NOT use the Parcelable method to send the information between the DataLibService
     * and the ServiceHelper.
     */
    public static final int OPTION_SEND_WITH_PARCELABLE_DISABLED = 0x00000100;

    /**
     * Ask to NOT use the database to store the BusinessObjects. 
     * In this case the request option OPTION_SEND_WITH_PARCELABLE is also enabled.
     */
    public static final int OPTION_DATABASE_CACHE_DISABLED = 0x00000010 | OPTION_SEND_WITH_PARCELABLE_ENABLED;

    /**
     * Ask to use the database to store the BusinessObjects.
     */
    public static final int OPTION_DATABASE_CACHE_ENABLED = 0x00000020;

    /**
     * Ask to use a HttpRequest that will not check the host for a https connection
     * <b>Not implemented yet</b>
     */
    public static final int OPTION_HOST_VERIFIER_ENABLED = 0x00002000;

    /**
     * Ask to use a HttpRequest that will check the host for a https connection
     * <b>Not implemented yet</b>
     */
    public static final int OPTION_HOST_VERIFIER_DISABLED = 0x00001000;

    /**
     * Ask to add the the response result to the {@link ServiceHelper} cache
     * <b>For now the helper cache is always enabled</b> 
     */
    public static final int OPTION_HELPER_CACHE_ENABLED = 0x00020000;

    /**
     * Ask to NOT add the the response result to the {@link ServiceHelper} cache
     * <b>Not implemented yet, for now the helper cache is always enabled</b> 
     */
    public static final int OPTION_HELPER_CACHE_DISABLED = 0x00010000;

        
    /**
     * Ask to not use the database to store the BusinessObjects. In this case the request
     * option OPTION_SEND_WITH_PARCELABLE is also enabled.
     */
    public static final int PARSE_TYPE_SAX_XML = 1;

    /**
     * Ask to use the Parcelable method to send the information between the DataLibService
     * and the ServiceHelper.
     */
    public static final int PARSE_TYPE_JSON_OBJECT = 2;

    /**
     * Ask to use the Parcelable method to send the information between the DataLibService
     * and the ServiceHelper.
     */
    public static final int PARSE_TYPE_IMAGE = 3;

    //HTTP request type
    public static final int HTTP_REST_GET = 0;
    public static final int HTTP_REST_PUT = 1;
    public static final int HTTP_REST_POST = 2;
    public static final int HTTP_REST_DELETE = 3;
    public static final int HTTP_REST_HEAD = 4;

    /**
     * Host to contact
     */
    public String url;
    /**
     * Path of the request
     */
    public String path;
    
    /**
     * parameters to send
     */
    public ParameterMap params;
    
    /**
     * useragent to send to the distant server
     */
    public String userAgent;
    
    /**
     * Intent recu du ServiceHelper
     */
    public Intent intent;
    
    /**
     * Request options
     */
    public int option;
    
    /**
     * An HashMap containing all the complex options of the request
     */
    public ComplexOptions complexOptions;
        
    /**
     * request type (REST_GET, REST_POST, ..., SOAP)
     */
    public int requestMethod;
    
    /**
     * Format expected from the webservice (XML, JSON, IMAGE, ...)
     */
    public int parseType;
    
    /**
     * content type of the data to send
     */
    public String contentType;
    
    /**
     * data to send (for PUT or POST request)
     */
    public byte[] data;

    /**
     * The application's context of execution
     */
    public Context context;
    
    public DataLibRequest() {
        option = OPTION_NO_OPTION;
        parseType = PARSE_TYPE_SAX_XML;
    }

    public DataLibRequest(String url, ParameterMap params) {
    	this();
    	this.url = url;
        this.params = params;
	}

    public DataLibRequest(String url, ParameterMap params, ComplexOptions options) {
    	this(url, params);
    	complexOptions = options;
	}

	/**
     * This function tells if the DataLibRequest ask to conserve the cookie returned by the server
     * 
     * @return return true if the cookie have to be conserved or false if not
     */
    public boolean isConservingTheCookies() {
        return ((option & OPTION_CONSERVE_COOKIE_ENABLED) == OPTION_CONSERVE_COOKIE_ENABLED);
    }

    /**
     * This function tells if it has been defined a specific behavior for the conserve the cookie option
     * 
     * @return return true if a behavior is defined, false if not
     */
    public boolean isDefinedBehaviorConservingTheCookies() {
        return ((option & (OPTION_CONSERVE_COOKIE_ENABLED | OPTION_CONSERVE_COOKIE_DISABLED)) != 0);
    }

    /**
     * This function tells if the DataLibRequest ask to not use the database to store
     * the BusinessObjects.
     * If its true, the request option OPTION_SEND_WITH_PARCELABLE is also enabled and the function
     * isParcelableMethodEnabled return also true.
     * 
     * @return return true if no data is stored
     */
    public boolean isDatabaseCacheEnabled() {
        return ((option & OPTION_DATABASE_CACHE_DISABLED) != OPTION_DATABASE_CACHE_DISABLED);
    }

    /**
     * This function tells if the DataLibRequest ask to not use the database to store
     * the BusinessObjects.
     * If its true, the request option OPTION_SEND_WITH_PARCELABLE is also enabled and the function
     * isParcelableMethodEnabled return also true.
     * 
     * @return return true if a behavior is defined, false if not
     */
    public boolean isDefinedBehaviorDatabaseCache() {
        return ((option & (OPTION_DATABASE_CACHE_DISABLED | OPTION_DATABASE_CACHE_ENABLED)) != 0);
    }

    /**
     * This function tells if the DataLibRequest ask to use the Parcelable method
     * to send the information between the DataLibService and the ServiceHelper
     * 
     * @return return true if no data is stored
     */
    public boolean isParcelableMethodEnabled() {
        return ((option & OPTION_SEND_WITH_PARCELABLE_ENABLED) == OPTION_SEND_WITH_PARCELABLE_ENABLED);
    }

    /**
     * This function tells if the DataLibRequest ask to use the Parcelable method
     * to send the information between the DataLibService and the ServiceHelper
     * 
     * @return return true if no data is stored
     */
    public boolean isDefinedBehaviorParcelableMethod() {
        return ((option & (OPTION_SEND_WITH_PARCELABLE_ENABLED | OPTION_SEND_WITH_PARCELABLE_DISABLED)) != 0);
    }

    /**
     * This function tells if the DataLibRequest ask to disable the host verifier
     * 
     * @return return true if the host verifier is enabled, false if not
     */
    public boolean isHostVerifierEnabled() {
        return ((option & OPTION_HOST_VERIFIER_ENABLED) == OPTION_HOST_VERIFIER_ENABLED);
    }

    /**
     * This function tells if a behavior is defined for the host verifier
     * 
     * @return return true if a behavior is defined, false if not
     */
    public boolean isDefinedBehaviorHostVerifier() {
        return ((option & (OPTION_HOST_VERIFIER_ENABLED | OPTION_HOST_VERIFIER_DISABLED)) != 0);
    }

    /**
     * This function tells if the DataLibRequest ask to enable the {@link ServiceHelper} responses cache
     * 
     * @return return true if it is enabled
     */
    public boolean isServiceHelperCacheEnabled() {
        return ((option & OPTION_HELPER_CACHE_ENABLED) == OPTION_HELPER_CACHE_ENABLED);
    }

    /**
     * This function tells if a behavior is defined for the {@link ServiceHelper} responses cache
     * 
     * @return return true if a behavior is defined, false if not
     */
    public boolean isDefinedBehaviorServiceHelperCache() {
        return ((option & (OPTION_HELPER_CACHE_ENABLED | OPTION_HELPER_CACHE_DISABLED)) != 0);
    }

    /**
     * This function tells if the DataLibRequest ask to conserve the cookie returned by the server
     * 
     * @return return true if the cookie have to be conserved or false if not
     */
    public boolean hasOptionEnabled() {
        return (option != OPTION_NO_OPTION);
    }
    
    /**
     * Compute the fingerprint of the request. This function is use when you want to compare this request with another
     * 
     * @return returns the fingerprint of the request.
     */
    public String getFingerprint() {

        //assembling the data
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(path);
        builder.append(params.urlEncode());
        builder.append(userAgent);
        builder.append(requestMethod);
        builder.append(contentType);
        builder.append(data);

        //create the MD5 hash 
        MessageDigest digester;
        try {
            String finger = builder.toString();
            byte[] bytes = finger.getBytes();

            digester = MessageDigest.getInstance("MD5");
            digester.update(bytes);
            byte[] digest = digester.digest();

            //create hexadecimal String
            builder.setLength(0);
            for (int i = 0; i < digest.length; i++)
                builder.append(Integer.toHexString(0xFF & digest[i]));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
    
    /**
     * Get the filename of the request
     * 
     * @return the file name contained on the path
     */
    public String getRequestFileName(){
    	String[] elements = url.split("/");
    	
    	//we return the last element of the path
		return elements[elements.length-1];
    }

    /**
     * Inner class that contains the masks to filter the {@link DataLibRequest#option} variable
     * 
     * @author Eyal
     *
     */
    public class Mask {

        /**
         * Ask to conserve the cookies sent by the server
         */
        public static final int OPTION_CONSERVE_COOKIE = OPTION_CONSERVE_COOKIE_ENABLED | OPTION_CONSERVE_COOKIE_DISABLED;

        /**
         * Ask to use the Parcelable method to send the information between the DataLibService
         * and the ServiceHelper.
         */
        public static final int OPTION_SEND_WITH_PARCELABLE = DataLibRequest.OPTION_SEND_WITH_PARCELABLE_ENABLED;

        /**
         * Ask to NOT use the database to store the BusinessObjects. 
         * In this case the request option OPTION_SEND_WITH_PARCELABLE is also enabled.
         */
        public static final int OPTION_DATABASE_CACHE = OPTION_DATABASE_CACHE_ENABLED | OPTION_DATABASE_CACHE_DISABLED;

        /**
         * Ask to use a HttpRequest that will not check the host for a https connection
         * <b>Not implemented yet</b>
         */
        public static final int OPTION_HOST_VERIFIER = OPTION_HOST_VERIFIER_ENABLED | OPTION_HOST_VERIFIER_DISABLED;

        /**
         * Ask to add the the response result to the {@link ServiceHelper} cache
         * <b>Not implemented yet</b> 
         */
        public static final int OPTION_HELPER_CACHE = OPTION_HELPER_CACHE_ENABLED | OPTION_HELPER_CACHE_DISABLED;
    }

}
