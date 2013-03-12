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

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.provider.BusinessObjectProvider;
import fr.eyal.lib.util.Out;

/**
 * This abstract class defines all the DAO functionalities of a {@code BusinessObject}.
 * When a class inherits from this class, it is forced to implement the function that allows to store it with the Active Directory pattern.
 * 
 * @author Eyal LEZMY
 */

public abstract class BusinessObjectDAO implements Parcelable {

    /**
     * Id used when the {@link BusinessObjectDAO} is not filled or saved into the database
     */
    public static final int ID_INVALID = -1;

    protected ContentResolver mResolver;

    /**
     * id of the BusinessObject in the database
     */
    public long _id;
    
    /**
     * updated_at field of the BusinessObject in the database
     */
    public Calendar _updatedAt;

    /**
     * Public constructor to automatically fill the object with the content from the database. By default, this method reach the last modified object if several
     * are found
     * 
     * @param id is the "id" primary key of the BusinessObject in the DataLib database
     */
    public BusinessObjectDAO(final long id) {
        initialize();
        fillFromDatabaseById(id);
    }

    /**
     * Public copy constructor
     * 
     * @param obj BusinessObject to copy
     */
    public BusinessObjectDAO(final BusinessObjectDAO obj) {
        initialize();
    }

    /**
     * Public default constructor
     */
    public BusinessObjectDAO() {
        initialize();
    }

    /**
     * Initialize the BusinessObject : store the DatabaseAdapter
     */
    protected void initialize() {
        _id = ID_INVALID;
        _updatedAt = Calendar.getInstance();

        mResolver = getContentResolver();
    }

    /********
     * DAO
     ********/
    public static final String FIELD_ID = "_id";
    public static final String FIELD_UPDATED_AT = "_updated_at";

    /**
     * This function implements the reaching of the {@link ContentResolver} on the project. Usually this object is accessible by the
     * {@link BusinessObjectProvider} class.
     * 
     * @return return the content resolver of the project or <b>null</b> if the {@link ContentProvider} is not declared inside the application
     */
    protected abstract ContentResolver getContentResolver();

    /**
     * This function implements the algorithm to delete all the childs of a business object from the DataLib database.
     * 
     * @return returns the number of row(s) deleted
     */
    public abstract ContentValues getContentValues();

    /**
     * This function implements the saving algorithm to store the whole business object inside the DataLib database. 
     * It manages the existence of a field with the same id. If the object already exists, it updates the row and all the associated fields (and tables)
     * 
     * @return returns the {@code ContentProviderResult} array produced by the operation
     */
    public abstract ContentProviderResult[] save() throws OperationApplicationException, RemoteException;

    /**
     * This function implements the saving algorithm to store the whole business object inside the DataLib database. 
     * It manages the existence of a field with the same id. If the object already exists, it updates the row and all the associated fields (and tables)

     * @param columns the FIELD_ID to reach before the insertion
     * @param where condition to filter the corresponding entry
     * @param args arguments to filter the corresponding entry
     * @param contentUri Uri of the content
     * @param authority Authority of the {@code ContentProvider} to use
     * 
     * @return returns the {@code ContentProviderResult} array produced by the operation
     * 
     * @throws OperationApplicationException
     * @throws RemoteException
     */
    protected ContentProviderResult[] save(final String[] columns, final String where, final String[] args, final Uri contentUri, final String authority) throws OperationApplicationException, RemoteException {

        //we create the DB batch to apply
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // if the object is supposed to be inside the database
        if (_id != ID_INVALID) {

            // we check the existence of the entry in the database
            final Cursor cursor = mResolver.query(contentUri,
                    columns, // id
                    where, // id=?
                    args, // id
                    null);

            // is the entry exists
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {

                _id = cursor.getLong(0); // we get the DB id of the object

                //we get the update batch
                updateIntoTheDatabase(batch);

                // else, we add an entry into the database
            } else {
                //we get the insert batch
                addIntoDatabase(batch, contentUri);
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }

        } else {
            addIntoDatabase(batch, contentUri);
        }

        ContentProviderResult[] result = mResolver.applyBatch(authority, batch);
        
        if(result.length > 0){
        	
        	long parentIndex = Long.parseLong(result[0].uri.getLastPathSegment());
        	_id = parentIndex;
        	
        	//we get the ids list
        	long[] ids = new long[result.length];
	        for (int i = 0; i < result.length; i++) {
				ids[i] = Integer.parseInt(result[i].uri.getLastPathSegment());
			}
	        
	        updateId(ids, 0);
        }
        return result;
    }

    /**
     * Update the POJO's {@link BusinessObjectDAO#_id} following a {@link ContentProviderResult} array.
     * This function update the current business object and all its children thanks to the ids array.
     * 
     * @param ids the ids list
     * @param index index of the id to set into the business object
     * 
     * @return returns the index after increment
     */
    public int updateId(long[] ids, int index) {
		Out.d("ID", ""+index);
		_id = ids[index];
		
		//we update the childs id
		updateChildrenId(ids, index+1, index);
		
		return index+1;
	}

	/**
     * Update the {@link BusinessObjectDAO#_id} variable of the business object's children. This function is called 
     * following a {@link ContentProviderResult} array.<br>
     * This function update the children of the current business object and all their children thanks to the ids array.
     * 
     * @param ids the ids list
     * @param index index of the id to set into the business object
     * @param parentIndex index of the id to set as parentId if it exists. Set it as {@link BusinessObjectDAO#ID_INVALID} 
     * if there is no parent
     * 
     * @return returns the index after increment
	 */
	protected abstract int updateChildrenId(long[] ids, int index, int parentIndex);

    /**
     * Update the POJO's {@link BusinessObjectDAO#_id} following a {@link ContentProviderResult} array.
     * This function update the current business object and all its children thanks to the ids array.
     * 
     * @param ids the ids list
     * @param index index of the id to set into the business object
     * 
     * @return returns the index after increment
     */
	public abstract int updateId(long[] ids, int index, int parentIndex);

	/**
     * This function implements the deletion algorithm of the business object inside the DataLib database.
     * 
     * @return returns the {@code ContentProviderResult} array produced by the operation
     */
    public abstract ContentProviderResult[] delete() throws OperationApplicationException, RemoteException;

    /**
     * This function implements the deletion algorithm of the business object inside the DataLib database.
     * 
     * 
     * @param columns the FIELD_ID to reach before the insertion
     * @param where condition to filter the corresponding entry
     * @param args arguments to filter the corresponding entry
     * @param contentUri Uri of the content
     * @param authority Authority of the {@code ContentProvider} to use
     * 
     * @return returns the {@code ContentProviderResult} array produced by the operation
     * 
     * @throws OperationApplicationException
     * @throws RemoteException
     */
    protected ContentProviderResult[] delete(final String[] columns, final String where, final String[] args, final Uri contentUri, final String authority) throws OperationApplicationException, RemoteException {
        //we create the DB batch to apply
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // if the object is supposed to be inside the database
        if (_id != ID_INVALID) {

            // we check the existence of the entry inside the database
            final Cursor cursor = mResolver.query(contentUri, // sensor
                    columns, // id
                    where, // _url LIKE ?
                    args, // url
                    null);

            // is the entry exists
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {

                //we delete the 
                deleteChildsFromDatabase(batch);
                deleteFromDatabase(batch);

                return mResolver.applyBatch(authority, batch);

            } else {
                return null;
            }

        } else {

        }

        return null;

    }

    /**
     * Private function that update the content of the business object into the database
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    protected abstract void updateIntoTheDatabase(final ArrayList<ContentProviderOperation> batch);

    /**
     * Private function that update the content of the business object into the database
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    protected void updateIntoTheDatabase(final ArrayList<ContentProviderOperation> batch, final Uri contentUri) {
        // we update the update value
        _updatedAt = Calendar.getInstance();

        //        final String where = FIELD_URL + " LIKE ?"; // url LIKE {this.url}
        //        final String[] args = { _url + "" };

        // we get the object's values
        final ContentValues values = getContentValues();

        // we add the ID inside the Content values
        values.put(FIELD_ID, _id);

        batch.add(ContentProviderOperation.newUpdate(contentUri)
                .withValues(values)
                .build());

        //        mResolver.update(CONTENT_URI, values, where, args);

        //TODO improve the childs update with verification or another technique

        // we remove all the childs
        deleteChildsFromDatabase(batch);
        addChildsIntoDatabase(batch);
        //        // we add the new childs
        //        for (final Sensor item : sensors) {
        //            item._parentId = _id;
        //            item.addIntoDatabase(batch);
        //        }
    }

    /**
     * This function implements the algorithm to delete the whole business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     * 
     * @return returns if weather the adding instruction have been added
     */
    public abstract boolean deleteFromDatabase(final ArrayList<ContentProviderOperation> batch);

    /**
     * This function implements the algorithm to delete the whole business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     * 
     * @return returns if weather the adding instruction have been added
     */
    public boolean deleteFromDatabase(final ArrayList<ContentProviderOperation> batch, final Uri contentUri) {
        // if the object is already inside the database
        if (!isInvalidID()) {

            // we remove the entry inside the database
            final String whereClause = FIELD_ID + "=?"; // id=?
            final String[] argsClause = { _id + "" };

            batch.add(ContentProviderOperation.newDelete(contentUri)
                    .withSelection(whereClause, argsClause)
                    .build());

            return true;
        }
        return false;
    }

    /**
     * This function implements the algorithm to delete all the childs of a business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    public abstract void deleteChildsFromDatabase(final ArrayList<ContentProviderOperation> batch);

    /**
     * This function implements the algorithm to store all the childs of a business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    public abstract void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch);

    /**
     * This function implements the algorithm to store all the childs of a business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     * @param previousResult the index of the previous operation to refer to in the parent's insertion
     */
    public abstract void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, int previousResult);

    /**
     * This function implements the algorithm to delete all the childs of a business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    public void addIntoDatabase(final ArrayList<ContentProviderOperation> batch, final Uri contentUri) {
        addIntoDatabase(batch, contentUri, null, -1);
    }

    /**
     * This function implements the algorithm to delete all the childs of a business object from the DataLib database.
     * <b>Attention: </b> the query is not performed by this function, you have to use the {@link BusinessObjectProvider#applyBatch(ArrayList)} function to execute it.
     * 
     * @param batch the {@code ContentProviderOperation} container where to add the list of the instructions to perform the action
     */
    public void addIntoDatabase(final ArrayList<ContentProviderOperation> batch, final Uri contentUri, final String parentField, final int indexPrevious) {
        // on met a jour la date de mise a jour
        _updatedAt = Calendar.getInstance();

        // on recupere les ContentValues de l'objet
        final ContentValues values = getContentValues();

        //we get the adding index to set the ValueBackReference for the childrens
        int addingIndex = batch.size();

        //if we have to add a back reference
        if (parentField != null && indexPrevious >= 0) {
            ContentProviderOperation op = ContentProviderOperation.newInsert(contentUri)
                    .withValues(values)
                    .withValueBackReference(parentField, indexPrevious)
                    .build();
            batch.add(op);
        } else {
            ContentProviderOperation op = ContentProviderOperation.newInsert(contentUri)
                    .withValues(values)
                    .build();
            batch.add(op);
        }

        //then we add its children into the database
        addChildsIntoDatabase(batch, addingIndex);

        //        Out.d(TAG, "Nouvel ID de l'objet : " + _id);
    }

    /**
     * This function implements the algorithm to reach the content form the database and fill the object with the object with the corresponding content
     * 
     * @param id is the id key of the BusinessObject in the database
     */
    protected abstract void fillFromDatabaseById(long id);

    /**
     * This function implements the algorithm to fill an object thanks to a Cursor
     * 
     * @param cursor the cursor containing the data
     */
    protected abstract void fillObjectFromCursor(Cursor cursor);

    /**
     * This function implements the algorithm to fill all the childs of the Business Object thanks to the database
     * 
     * @param cursor the cursor containing the data
     */
    protected abstract void fillChildrenFromDatabase();

    /*****
     * GETTERS & SETTERS
     *****/

    /**
     * Check if the object get a valid ID
     * 
     * @return returns true if the id is not ID_INVALID, otherwise it returns falses
     */
    public boolean isInvalidID() {
        return _id == ID_INVALID;
    }
}
