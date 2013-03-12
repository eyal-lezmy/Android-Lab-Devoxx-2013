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

import fr.eyal.lib.util.Out;

/**
 * Default {@link RequestLogger} used by {@link BasicHttpClient}. In recent
 * versions of Android, log() gets directed to LogCat so this can
 * work for Android, too.
 * http://stackoverflow.com/questions/2220547/why-doesnt-system
 * -out-println-work-in-android
 * 
 * @author David M. Chandler
 */
public class ConsoleRequestLogger implements RequestLogger {

    /*
     * (non-Javadoc)
     * @see com.turbomanage.httpclient.RequestLogger#isLoggingEnabled()
     */
    @Override
    public boolean isLoggingEnabled() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.turbomanage.httpclient.RequestLogger#log(java.lang.String)
     */
    @Override
    public void log(final String msg) {
        Out.d("RestLog", msg);
    }

    /*
     * (non-Javadoc)
     * @see com.turbomanage.httpclient.RequestLogger#logRequest(java.net.
     * HttpURLConnection, java.lang.Object)
     */
    @Override
    public void logRequest(final HttpURLConnection uc, final Object content) throws IOException {
        log("=== HTTP Request ===");
        log(uc.getRequestMethod() + " " + uc.getURL().toString());
        if (content instanceof String) {
            log("Content: " + (String) content);
        }
        logHeaders(uc.getRequestProperties());
    }

    /*
     * (non-Javadoc)
     * @see com.turbomanage.httpclient.RequestLogger#logResponse(java.net.
     * HttpURLConnection)
     */
    @Override
    public void logResponse(final HttpResponse res) {
        if (res != null) {
            log("=== HTTP Response ===");
            log("Receive url: " + res.getUrl());
            log("Status: " + res.getStatus());
            logHeaders(res.getHeaders());
            log("Content:\n" + res.getBodyAsString());
        }
    }

    /**
     * Iterate over request or response headers and log them.
     * 
     * @param map
     */
    private void logHeaders(final Map<String, List<String>> map) {
        if (map != null) {
            for (String field : map.keySet()) {
                List<String> headers = map.get(field);
                for (String header : headers) {
                    log(field + ":" + header);
                }
            }
        }
    }

}
