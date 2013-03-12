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

/**
 * HTTP request logger used by {@link BasicHttpClient}.
 * 
 * @author David M. Chandler
 */
public interface RequestLogger {

    /**
     * Determine whether requests should be logged.
     * 
     * @return true if enabled
     */
    boolean isLoggingEnabled();

    /**
     * Writes a log message.
     * 
     * @param msg
     */
    void log(String msg);

    /**
     * Log the HTTP request and content to be sent with the request.
     * 
     * @param urlConnection
     * @param content
     * @throws IOException
     */
    void logRequest(HttpURLConnection urlConnection, Object content) throws IOException;

    /**
     * Logs the HTTP response.
     * 
     * @param httpResponse
     * @throws IOException
     */
    void logResponse(HttpResponse httpResponse);

}
