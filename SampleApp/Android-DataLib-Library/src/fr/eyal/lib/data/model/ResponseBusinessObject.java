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
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.service.model.DataLibRequest;

/**
 * This abstract class defines all the functionalities to give to a {@code ResponseBusinessObject}.
 * 
 * @author Eyal LEZMY
 */
public interface ResponseBusinessObject extends Parcelable {

    /**
     * Save request fired in case of caching process.
     * If this {@code ResponseBusinessObject} is not supposed to be cached into the database, this function can be let empty.
     * 
     * @param request the {@code DataLibRequest} processed
     * 
     * @throws OperationApplicationException 
     * @throws RemoteException 
     */
    public void save(DataLibRequest request) throws RemoteException, OperationApplicationException;
}
