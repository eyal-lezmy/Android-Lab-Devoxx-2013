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

import fr.eyal.lib.data.model.ResponseBusinessObject;

/**
 * @author Eyal LEZMY
 */
public class BusinessResponse {

    /**
     * Value for a successful client request
     */
    public static final int STATUS_OK = 0;
    
    /**
     * Value for an error during the client request
     */
    public static final int STATUS_ERROR = 1;
    
    /**
     * Value for a false server's return code
     */
    public static final int RETURN_CODE_FALSE = -999;

    /**
     * BusinessObject returned by the DataLib
     */
    public ResponseBusinessObject response;
    
    /**
     * Status code of the request STATUS_OK | STATUS_ERROR
     */
    public int status;
    
    /**
     * Code returned by the server with the response
     */
    public int returnCode;
    
    /**
     * Eventual message relative to the status of the response
     */
    public String statusMessage;
    
    /**
     * Type of webservice concerned by the response
     */
    public int webserviceType;

    public BusinessResponse() {
        super();
        returnCode = RETURN_CODE_FALSE;
    }

}
