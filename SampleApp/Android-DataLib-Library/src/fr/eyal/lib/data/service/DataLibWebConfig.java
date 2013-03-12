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

package fr.eyal.lib.data.service;

import fr.eyal.lib.data.service.model.DataLibRequest;

/**
 * @author Eyal LEZMY
 */
public class DataLibWebConfig {

    /**
     * HTTP Type of the request (REST GET, PUT, POST, ...)
     */
    private int httpType = DataLibRequest.HTTP_REST_GET;
    
    /**
     * Request's options (cache management, cookies, ...)
     */
    private int requestOptions = DataLibRequest.OPTION_SEND_WITH_PARCELABLE_ENABLED;
    
    /**
     * Format type expected by the webservice (XML, JSON, IMAGE, ...)
     */
    private int parseType = DataLibRequest.PARSE_TYPE_SAX_XML;

    protected static DataLibWebConfig sInstance; //Singleton of the WebConfig

    public static DataLibWebConfig getInstance() {
        synchronized (DataLibWebConfig.class) {
	        if (sInstance == null) {
	        	sInstance = new DataLibWebConfig();
	        }
        }	
        return (DataLibWebConfig) sInstance;
    }

    protected DataLibWebConfig() {
    }

    /**
     * Apply the config parameters to a {@link DataLibRequest}.
     * If an option have already been applied, it is overridden by using this function. If you want to not change an already defined option use :
     * <pre>
     * applyToRequest(request, config, <b>false<b>);
     * </pre>
     * @param request the request to modify
     * @param config the config to apply
     * 
     * @return the request modified
     */
    public static DataLibRequest applyToRequest(final DataLibRequest request, final DataLibWebConfig config) {
        return applyToRequest(request, config, false);
    }

    /**
     * Apply the config parameters to a {@link DataLibRequest}
     *  
     * @param request the request to modify
     * @param config the config to apply
     * @param force forces or not the application of the configuration
     * 
     * @return the request modified
     */
    public static DataLibRequest applyToRequest(final DataLibRequest request, final DataLibWebConfig config, final boolean force) {
        request.requestMethod = config.getHttpType();
        request.parseType = config.getParseType();

        //we apply the options to the request
        applyToRequest(request, config.getRequestOptions(), force);
        return request;
    }

    /**
     * Apply the config parameters to a {@link DataLibRequest}
     *  
     * @param request the request to modify
     * @param requestOptions the options to apply
     * @param force forces or not the application of the configuration
     * 
     * @return the request modified
     * 
     */
    public static DataLibRequest applyToRequest(final DataLibRequest request, final int requestOptions, final boolean force) {

        //we apply the config options to the non already defined slots
        if (!request.isDefinedBehaviorConservingTheCookies() || force)
            request.option |= requestOptions & DataLibRequest.Mask.OPTION_CONSERVE_COOKIE;
        if (!request.isDefinedBehaviorDatabaseCache() || force)
            request.option |= requestOptions & DataLibRequest.Mask.OPTION_DATABASE_CACHE;
        if (!request.isDefinedBehaviorHostVerifier() || force)
            request.option |= requestOptions & DataLibRequest.Mask.OPTION_HOST_VERIFIER;
        if (!request.isDefinedBehaviorServiceHelperCache() || force)
            request.option |= requestOptions & DataLibRequest.Mask.OPTION_HELPER_CACHE;
        if (!request.isDefinedBehaviorParcelableMethod() || force)
            request.option |= requestOptions & DataLibRequest.Mask.OPTION_SEND_WITH_PARCELABLE;

        return request;
    }

    /**
     * Get the HTTP type o the request
     * @return the corresponding
     */
    public int getHttpType() {
        return httpType;
    }

    public int getParseType() {
        return parseType;
    }

    public int getRequestOptions() {
        return requestOptions;
    }
}
