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

/**
 * Holds data for an HTTP request to be made with the attached HTTP client.
 * 
 * @author David M. Chandler, Eyal LEZMY
 */
public abstract class HttpRequest {

    public static final String URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String MULTIPART = "multipart/form-data";

    protected String path = ""; // avoid null in URL
    protected HttpRESTMethod httpMethod;
    protected String contentType;
    protected byte[] content;

    /**
     * Constructs a request with optional params appended
     * to the query string.
     * 
     * @param path
     * @param params
     */
    public HttpRequest(final String path, final ParameterMap params) {
        String queryString = null;
        if (path != null) {
            this.path = path;
        }
        if (params != null) {
            queryString = params.urlEncode();
            this.path += "?" + queryString;
        }
    }

    public String getPath() {
        return path;
    }

    public HttpRESTMethod getHttpMethod() {
        return httpMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

}
