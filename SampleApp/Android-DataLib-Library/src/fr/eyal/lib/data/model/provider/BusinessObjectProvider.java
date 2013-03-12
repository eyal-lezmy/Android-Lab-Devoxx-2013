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

package fr.eyal.lib.data.model.provider;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.util.Out;

/**
 * @author Eyal LEZMY
 */
public abstract class BusinessObjectProvider extends ContentProvider {

	private static final String TAG = BusinessObjectProvider.class.getSimpleName();

    public static String DATABASE_NAME = null;
    public static int DATABASE_VERSION = -1;

    /**
     * Tables' names list
     */
    public static String[] DATABASE_TABLES_NAMES = null;

    /**
     * Tables' fields' names list
     */
    public static String[][] DATABASE_TABLES_FIELDS_NAMES = null;

    /**
     * Tables' fields' names list
     */
    public static String[] CREATE_TABLES = null;


    /**
     * Context of execution
     */
    private static Context sContext;
    
    /**
     * SQLite Open Helper
     */
    private static DatabaseHelper sDbHelper;

    /**
     * Uri matcher
     */
    protected static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String PROVIDER_PREFIX = "content://";

    /**
     * Codes returned by the URIMatcher corresponding to the URI analyzed
     */


    @Override
    public boolean onCreate() {
        Out.d(TAG, "On Create BusinessObjectProvider");

        initialize(getContext());
        return true;
    }

    /**
     * Initialize provider context
     * 
     * @param context is the context of execution. If possible give the Application's context
     */
    public static void initialize(final Context context) {
        if (sContext == null) {
            Out.d(TAG, "initialize!!");
            sContext = context.getApplicationContext();
            sDbHelper = new DatabaseHelper(sContext);
        }
    }

    /*****************************
     * 
     * ContentProvider's Functions
     * 
     *****************************/

    @Override
    public int delete(final Uri uri, final String where, final String[] whereArgs) {

        final int match = sUriMatcher.match(uri);

        Out.d(TAG, TAG + ".delete: uri=" + uri + ", match is " + match);

        //We get the database access
        SQLiteDatabase db = sDbHelper.getWritableDatabase();
        int count;

        //we get the table and then we delete the field into the database
        String table = getTableName(match);
        count = db.delete(table, where, whereArgs);

        sContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Uri insert(final Uri uri, ContentValues values) {

        //we get the type of the URI received
        final int match = sUriMatcher.match(uri);

        //log message
        Out.d(TAG, "BusinessObjectProvider.insert: uri=" + uri + ", match is " + match);

        //we manage the null ContentValues
        if (values == null)
            values = new ContentValues();

        //we prepare the database access
        SQLiteDatabase db = sDbHelper.getWritableDatabase();

        //we create the result values
        long id = BusinessObjectDAO.ID_INVALID;
        Uri resultUri;

        //we get the table name and then insert into the database
        String table = getTableName(match);
        id = db.insert(table, "foo", values);

        //we add the id received at the end of the URI
        resultUri = ContentUris.withAppendedId(uri, id);

        //we return the URI with the id of the entry at the end
        return resultUri;
    }

    @Override
    public Cursor query(final Uri uri, final String[] columns, final String where, final String[] whereArgs, final String order) {
        Cursor c = null;

        final int match = sUriMatcher.match(uri);

        //get a readable database access
        SQLiteDatabase db = sDbHelper.getReadableDatabase();

        //log message
        Out.d(TAG, "BusinessObjectProvider.query: uri=" + uri + ", match is " + match);

        //we get the table and then query to the database
        String table = getTableName(match);
        c = db.query(table, columns, where, whereArgs, null, null, order);

        //we notify that a change happened if it actually occurred
        if ((c != null) && !isTemporary()) {
            c.setNotificationUri(sContext.getContentResolver(), uri);
        }
        return c;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String where, final String[] whereArgs) {
        final int match = sUriMatcher.match(uri);

        //get a readable database access
        SQLiteDatabase db = sDbHelper.getWritableDatabase();

        //log message
        Out.d(TAG, "BusinessObjectProvider.query: uri=" + uri + ", match is " + match);

        //we initialize the update counter
        int count = 0;

        String table = getTableName(match);

        //throws an exception if there is no table name
        if (table == null)
            throw new IllegalArgumentException("Unknown URI " + uri);

        //update the database
        count = db.update(table, values, where, whereArgs);

        //we notify that a change happened
        sContext.getContentResolver().notifyChange(uri, null);

        return count;
    }

    /**
     * Get the ContentResolver of the application Context
     * 
     * @return the ContentResolver of the context
     * @throws InstantiationException 
     */
    public static ContentResolver getContentResolver() throws InstantiationException {
    	if(sContext == null)
    		throw new InstantiationException("The ContentProvider does not exits. Did you declare it on the Android Manifest?");
        return sContext.getContentResolver();
    }

    /**
     * Get the table name corresponding to the matched URI
     * This used to implement a switch/case algorithm like this :
     * 
     * <code>
     * switch (match) {
     * 	case CODE_PREVISION_METEO:
     *  	return PrevisionMeteo.DATABASE_TABLE_NAME;
     *  	break;
     *  case CODE_METEO_WEATHER:
     *  	return MeteoWeather.DATABASE_TABLE_NAME;
     *  	break;
     *  default:
     *  throw new IllegalArgumentException("Unknown URI " + uri);
     *  }
     * </code>
     * 
     * 
     * @param match result of the {@link UriMatcher} match method
     * 
     * @return the name of the corresponding table
     */
    protected abstract String getTableName(int match);

    /**************** Subclass for the database automatic generation ******************/

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String TAG = "DatabaseHelper";

        DatabaseHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {

            Out.d(TAG, "Creation de la BDD");

            //we go through the tables creation scripts
            for (int i = 0; i < CREATE_TABLES.length; i++) {

                Out.d(TAG, CREATE_TABLES[i]);

                //we create each table into the database
                db.execSQL(CREATE_TABLES[i]);
            }
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

            Out.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

            //we go through the tables names array
            for (int i = 0; i < DATABASE_TABLES_NAMES.length; i++) {

                //we DROP each table
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLES_NAMES[i]);
            }

            //we recreate the table
            onCreate(db);
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    @Override
    public ContentProviderResult[] applyBatch(final ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = sDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

}
