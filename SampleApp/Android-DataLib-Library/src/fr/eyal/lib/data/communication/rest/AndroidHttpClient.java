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

import java.net.HttpURLConnection;

import android.os.AsyncTask;
import android.os.Build;

/**
 * HTTP client for Android providing both synchronous (blocking) and asynchronous
 * interfaces so it can be used on or off the UI thread.
 * 
 * <p>Sample usage:</p>
 * 
 * <p>Synchronous (for use off the UI thread in an {@link AsyncTask} or {@link Runnable})</p>
 * <pre>
 *    AndroidHttpClient httpClient = new AndroidHttpClient("http://www.google.com");
 *    ParameterMap params = httpClient.newParams().add("q", "GOOG");
 *    HttpResponse httpResponse = httpClient.get("/finance", params);
 *    System.out.println(httpResponse.getBodyAsString());
 * </pre>
 * 
 * <p>Asynchronous (can be used anywhere, automatically wraps in an {@link AsyncTask})</p>
 * <pre>
 *    AndroidHttpClient httpClient = new AndroidHttpClient("http://www.google.com");
 *    ParameterMap params = httpClient.newParams().add("q", "GOOG");
 *    httpClient.setMaxRetries(3);
 *    httpClient.get("/finance", params, new AsyncCallback() {
 *        public void onComplete(HttpResponse httpResponse) {
 *            System.out.println(httpResponse.getBodyAsString());
 *        }
 *        public void onError(Exception e) {
 *            e.printStackTrace();
 *        }
 *    });
 * </pre>
 * 
 * @author David M. Chandler
 */
public class AndroidHttpClient extends AbstractHttpClient {

    static {
        disableConnectionReuseIfNecessary();
    }

    /**
     * Constructs a new client with empty baseUrl. When used this way, the path
     * passed to a request method must be the complete URL.
     */
    public AndroidHttpClient() {
    }

    /**
     * Constructs a client with baseUrl.
     * 
     * @param baseUrl
     */
    public AndroidHttpClient(final String baseUrl) {
        super(baseUrl);
    }

    /**
     * Constructs a client with baseUrl and custom {@link RequestHandler}.
     * 
     * @param baseUrl
     * @param requestHandler
     */
    public AndroidHttpClient(final String baseUrl, final RequestHandler requestHandler) {
        super(baseUrl, requestHandler);
    }

    /**
     * Work around bug in {@link HttpURLConnection} on older versions of
     * Android.
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
}
