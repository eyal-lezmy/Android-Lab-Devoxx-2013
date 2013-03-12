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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Minimal representation of the raw HTTP response copied from {@link HttpURLConnection}. 
 * 
 * @author David M. Chandler
 */
public class HttpResponse {

    private int status;
    private String url;
    private Map<String, List<String>> headers;
    private byte[] body;

    public HttpResponse(final HttpURLConnection urlConnection, final byte[] body) {
        try {
            status = urlConnection.getResponseCode();
            url = urlConnection.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        headers = urlConnection.getHeaderFields();
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public String getBodyAsString() {
        if (body != null) {
            return new String(body);
        }
        return null;
    }

}
