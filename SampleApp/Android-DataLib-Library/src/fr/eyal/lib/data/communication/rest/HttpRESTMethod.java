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
 * Enumerated type that represents an HTTP request method.
 * Besides the method name, it determines whether the client
 * should do the output phase of the connection.
 *  
 * @author David M. Chandler
 */
public enum HttpRESTMethod {

    GET(true, false), POST(true, true), PUT(true, true), DELETE(true, false), HEAD(false, false);

    private boolean doInput;
    private boolean doOutput;

    private HttpRESTMethod(final boolean doInput, final boolean doOutput) {
        this.doInput = doInput;
        this.doOutput = doOutput;
    }

    public boolean getDoInput() {
        return doInput;
    }

    /**
     * Whether the client should do the write phase, or just read
     * 
     * @return doOutput
     */
    public boolean getDoOutput() {
        return doOutput;
    }

    /**
     * Accessor method.
     * 
     * @return HTTP method name (GET, PUT, POST, DELETE)
     */
    public String getMethodName() {
        return this.toString();
    }

}
