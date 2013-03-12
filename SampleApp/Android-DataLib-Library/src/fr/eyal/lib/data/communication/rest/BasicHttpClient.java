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
 * Minimal HTTP client that facilitates simple GET, POST, PUT, and DELETE
 * requests. To implement buffering, streaming, or set other request properties, 
 * set an alternate {@link RequestHandler}.
 * 
 * <p>Sample usage:</p>
 * <pre>
 *    BasicHttpClient httpClient = new BasicHttpClient("http://www.google.com");
 *    ParameterMap params = httpClient.newParams().add("q", "GOOG");
 *    HttpResponse httpResponse = httpClient.get("/finance", params);
 *    System.out.println(httpResponse.getBodyAsString());
 * </pre>
 * 
 * @author David M. Chandler
 */
public class BasicHttpClient extends AbstractHttpClient {

    /**
     * Constructs the default client with empty baseUrl. 
     */
    public BasicHttpClient() {
        this("");
    }

    /**
     * Constructs the default client with baseUrl.
     * 
     * @param baseUrl
     */
    public BasicHttpClient(final String baseUrl) {
        this(baseUrl, new BasicRequestHandler() {});
    }

    /**
     * Constructs a client with baseUrl and custom {@link RequestHandler}.
     * 
     * @param baseUrl
     * @param requestHandler
     */
    public BasicHttpClient(final String baseUrl, final RequestHandler requestHandler) {
        super(baseUrl, requestHandler);
    }

}
