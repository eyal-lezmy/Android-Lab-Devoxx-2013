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

import java.util.List;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.CookieSpecRegistry;

/**
 * @author Eyal LEZMY
 */
public class DataLibResponse extends BusinessResponse {

    /**
     * Headers returned by the server
     */
    public Map<String, List<String>> headers;
    
    /**
     * Request sent by the Service to the processor
     */
    public DataLibRequest request;
    
    public CookieStore cookies; //Headers returned by the server
    public CookieSpecRegistry cookiesSpec; //Headers returned by the server

    public DataLibResponse() {
    }

}
