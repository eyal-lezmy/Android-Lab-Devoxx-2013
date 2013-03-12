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

package fr.eyal.lib.data.model;

import android.content.OperationApplicationException;
import android.os.RemoteException;
import fr.eyal.lib.data.service.model.DataLibRequest;

/**
 * This abstract class defines all the functionalities to give to a {@code ResponseBusinessObjectDAO}.
 * The {@code ResponseBusinessObject} is a {@code BusinessObject} that is the root node of a DataLib response. 
 * It contains information needed to manage the cache :<br>
 * {@link #_url} defines the field that identify the request cache inside the database<br>
 * {@link #_ttl} is the time to live of the data into the cache<br>
 * 
 * @author Eyal LEZMY
 */

public abstract class ResponseBusinessObjectDAO extends BusinessObjectDAO implements ResponseBusinessObject {

    /**
     * url field of the BusinessObject in the database. 
     * This field is used to store a representation of a unique request to be able to choose if the data have 
     * to be added or overridden
     */
    public String _url; 
    /**
     * ttl (time to live) value of the BusinessObject in the database (no logic associated with it for now)
     */
    public long _ttl;

    /**
     * Public constructor to automatically fill the object with the content from the database. By default, this method reach the last modified object if several
     * are found
     * 
     * @param id is the "id" primary key of the BusinessObject in the DataLib database
     */
    public ResponseBusinessObjectDAO(final long id) {
        initialize();
        fillFromDatabaseById(id);
    }

    /**
     * Public constructor to automatically fill the object with the content from the database By default, this method reach the last modified object if several
     * are found
     * 
     * @param url is the "url" field of the BusinessObject in the DataLib database
     */
    public ResponseBusinessObjectDAO(final String url) {
        initialize();
        fillFromDatabaseByUrl(url);
    }

    /**
     * Public copy constructor
     * 
     * @param obj BusinessObject to copy
     */
    public ResponseBusinessObjectDAO(final ResponseBusinessObjectDAO obj) {
        initialize();
    }

    /**
     * Public default constructor
     */
    public ResponseBusinessObjectDAO() {
        initialize();
    }

    /*******************************
     * DAO
     *******************************/

    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {

        _url = request.getFingerprint();

        save();
    }

    /**
     * This function implements the algorithm to reach the content form the database and fill the object with the object with the corresponding content By
     * default, this method will reach the last modified object if several are found
     * 
     * @param url is the "url" field of the BusinessObject in the DataLib database
     */
    protected abstract void fillFromDatabaseByUrl(String url);

}
