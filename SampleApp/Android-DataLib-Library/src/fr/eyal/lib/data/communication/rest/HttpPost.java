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
 * An HTTP POST request.
 * 
 * @author David M. Chandler
 */
public class HttpPost extends HttpRequest {

    /**
     * Constructs an HTTP POST request with name-value pairs to
     * be sent in the request BODY.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be sent in request BODY
     */
    public HttpPost(final String path, final ParameterMap params) {
        super(path, null);
        httpMethod = HttpRESTMethod.POST;
        this.path = path;
        contentType = URLENCODED;
        if (params != null) {
            content = params.urlEncodedBytes();
        }
    }

    /**
     * Constructs an HTTP POST request with arbitrary content.
     * If params is non-null, the name-value pairs will be appended to the QUERY STRING
     * while the content is sent in the request BODY.
     * This is not a common use case and is therefore not represented in the post()
     * methods in {@link AbstractHttpClient} or {@link AsyncHttpClient}, 
     * but is nevertheless possible using this constructor.
     * 
     * @param path Partial URL
     * @param params Optional name-value pairs to be appended to QUERY STRING
     * @param contentType MIME type
     * @param data Content to be sent in the request body
     */
    public HttpPost(final String path, final ParameterMap params, final String contentType, final byte[] data) {
        super(path, params);
        httpMethod = HttpRESTMethod.POST;
        this.contentType = contentType;
        content = data;
    }

}
