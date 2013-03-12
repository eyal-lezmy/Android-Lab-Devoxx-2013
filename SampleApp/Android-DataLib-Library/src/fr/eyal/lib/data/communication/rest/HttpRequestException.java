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
 * Custom exception class that holds an {@link HttpResponse}.
 * This allows upstream code to receive an HTTP status code and 
 * any content received as well as the underlying exception.
 * 
 * @author David M. Chandler
 */
public class HttpRequestException extends Exception {

    private static final long serialVersionUID = -2413629666163901633L;

    private HttpResponse httpResponse;

    /**
     * Constructs the exception with 
     * 
     * @param e
     * @param httpResponse
     */
    public HttpRequestException(final Exception e, final HttpResponse httpResponse) {
        super(e);
        this.httpResponse = httpResponse;
    }

    /**
     * Access the response.
     * 
     * @return Response object
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
