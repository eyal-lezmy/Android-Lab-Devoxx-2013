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

package fr.eyal.lib.data.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.util.SparseArray;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.model.BusinessObject;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.model.provider.BusinessObjectProvider;
import fr.eyal.lib.data.service.ServiceHelper.OnRequestFinishedListener;
import fr.eyal.lib.data.service.ServiceHelper.OnRequestFinishedRelayer;
import fr.eyal.lib.data.service.model.BusinessResponse;
import fr.eyal.lib.data.service.model.ComplexOptions;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.util.Out;

/**
 * @author Eyal LEZMY
 */
public abstract class DataManager implements OnRequestFinishedRelayer {

    private static final String LOG_TAG = DataManager.class.getSimpleName();

    /**
     * singleton of the class
     */
    protected static DataManager sInstance;

    /**
     * ServiceHelper to contact the DataLib
     */
    protected ServiceHelper mServiceHelper;

    /**
     * List of listeners who will receive the responses from the DataLib
     */
    protected SparseArray<ArrayList<OnDataListener>> mListenersSparseArray;
    
    /**
     * List of responses stored into the DataManager
     */
    protected SparseArray<Bundle> mDataLibResponsesSparseArray;

    /**
     * Connectivity Manager to access to the network configuration
     */
    protected ConnectivityManager mConnectivityManager;

    /**
     * The content resolver of the application
     */
    protected ContentResolver mContentResolver;

    /**
     * The cache request ids
     */
    protected SparseArray<Object> mDataCacheRequestIds;

    
    /*
     * different types of policies for the request
     */
    
    /**
     * <b>Network only</b> This value defines a DataManager request as a "network only" treatment. The DataManager have only to use the DataLib to get the
     * response.
     */
    public static final int TYPE_NETWORK = 0;

    /**
     * <b>Cache only</b> This value defines a DataManager request as a "cache only" treatment. The DataManager have only to use the DataCache to get the
     * response.
     */
    public static final int TYPE_CACHE = 1;

    /**
     * <b>Network otherwise cache</b> This value defines a DataManager request that have to check the connectivity. If a network request is possible it have to
     * start a network request, if not, it fetchs the information from the cache.
     */
    public static final int TYPE_NETWORK_OTHERWISE_CACHE = 2;

    /**
     * <b>Cache then network</b> This value defines a DataManager request that have to send a network request. While waiting for the network response, it has to
     * fetch an eventual result inside the cache and sending it to the controller.
     */
    public static final int TYPE_CACHE_THEN_NETWORK = 3;

    //Response returned by the "retrieve" functions
    public static final int DATACACHE_REQUEST = -1; //The request is a DataCache request
    public static final int BAD_REQUEST = -2; //An error exists in the function's parameters

    protected DataManager(final Context context) {
        //		mServiceHelper = ServiceHelper.getInstance(context);
        //		mServiceHelper.addOnRequestFinishedRelayer(this);
        mListenersSparseArray = new SparseArray<ArrayList<OnDataListener>>();
        mDataLibResponsesSparseArray = new SparseArray<Bundle>();
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mContentResolver = context.getContentResolver();
        mDataCacheRequestIds = new SparseArray<Object>();
    }

    /**
     * Clients may implements this interface to be notified when a data (network result, cache data or database content) is returned
     */
    public static interface OnDataListener extends ServiceHelper.OnRequestFinishedListener {

        /**
         * Event fired when a cache request is finished.
         * 
         * @param response is the response returned by the cache.
         */
        public void onCacheRequestFinished(int requestId, ResponseBusinessObject response);

        /**
         * Event fired when a database query is finished.
         * 
         * @param code The code that point out the type of the data. It must correspond to the {@link BusinessObjectProvider} constants beginning by "CODE" and
         *            then the name of the expected table (ex: CODE_PREVISION_METEO)
         * @param data The data array returned after the treatment of the query. The type inside the array inherits from {@link BusinessObjectDAO}
         */
        public void onDataFromDatabase(int code, ArrayList<?> data);
    }

    /**
     * Add a {@link OnRequestFinishedListener} to this {@link ServiceHelper}. Clients may use it in order to listen to events fired when a request is finished.
     * <p>
     * <b>Warning !! </b> The listener <b>must</b> be detached when onPause is called in an Activity.
     * </p>
     * 
     * @param listener The listener to add to this {@link ServiceHelper}.
     */
    public synchronized void addOnDataListener(final int requestId, final OnDataListener listener) {

        if (listener == null) {
            Out.i(LOG_TAG, requestId + " adding listener impossible: " + listener);
            return;
        }
        ArrayList<OnDataListener> listeners = mListenersSparseArray.get(requestId);
        if (listeners == null) {
            listeners = new ArrayList<OnDataListener>();
            mListenersSparseArray.append(requestId, listeners);
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener);
            Out.i(LOG_TAG, requestId + " adding listener " + listener);
        } else {
            Out.i(LOG_TAG, requestId + " already contains " + listener);
        }
    }

    /**
     * Remove a {@link OnRequestFinishedListener} to this {@link ServiceHelper}.
     * 
     * @param listenerThe listener to remove to this {@link ServiceHelper}.
     */
    public synchronized void removeOnDataListener(final int requestId, final OnDataListener listener) {

        if (listener == null)
            return;

        final ArrayList<OnDataListener> listeners = mListenersSparseArray.get(requestId);

        if (listeners != null) {
            listeners.remove(listener);
            Out.i(LOG_TAG, requestId + " listener removed" + listener);
        } else {
            Out.i(LOG_TAG, requestId + " no listener");
        }
    }

    /**
     * Cancel the request. This cancel the response's return of the request
     * 
     * @param id The id of the requests
     */
    public synchronized void cancelRequest(final int id) {
        if (mDataCacheRequestIds.indexOfKey(id) >= 0)
            mDataCacheRequestIds.delete(id);

        mServiceHelper.cancelRequest(id);
    }

    /**
     * Returns whether a request (specified by an id) is still in progress or not
     * 
     * @param requestId the id of the request
     * @return
     */
    public boolean isRequestInProgress(final int requestId) {
        return mServiceHelper.isRequestInProgress(requestId);
    }

    /**
     * Launch the response handling asynchronously
     * TODO determine if this function is still useful
     * 
     * @param requestId the request ID
     * 
     * @return <b>true</b> if the request have been found among the non treated request and <b>false</b> if not
     */
    protected boolean launchResponse(final int requestId) {
        final Bundle bundle = mDataLibResponsesSparseArray.get(requestId);

        if (bundle == null)
            return false;
        else {
            //we start launching the response in another thread
            final Thread responseThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    handleResult(bundle.getInt(ServiceHelper.RECEIVER_EXTRA_RESULT_CODE), bundle);
                }
            });
            responseThread.start();
            return true;
        }
    }

    /**
     * This method manages the result of the {@link ServiceHelper}
     */
    protected synchronized void handleResult(final int resultCode, final Bundle resultData) {

        final int requestId = resultData.getInt(ServiceHelper.RECEIVER_EXTRA_REQUEST_ID);
        final int webserviceType = resultData.getInt(ServiceHelper.RECEIVER_EXTRA_WEBSERVICE_TYPE);
        final int returnCode = resultData.getInt(ServiceHelper.RECEIVER_EXTRA_RETURN_CODE);
        final String statutMessage = resultData.getString(ServiceHelper.RECEIVER_EXTRA_RESULT_MESSAGE);

        Out.w(LOG_TAG, "Handle RESULT " + requestId + " !!!");

        //state succeed or not of the request (to be sent to the OnRequestFinishedListener)
        final boolean succeed = (resultCode == BusinessResponse.STATUS_OK);

        //we extract the different listeners linked to this request
        final ArrayList<OnDataListener> listeners = mListenersSparseArray.get(requestId);

        //we delete the erase the request and its listeners
        mListenersSparseArray.delete(requestId);
        mDataLibResponsesSparseArray.delete(requestId);

        //if there is a least one listener we send the signal
        if (listeners != null && listeners.size() > 0) {

            //we build the BusinessResponse
            final BusinessResponse businessResponse = new BusinessResponse();
            businessResponse.webserviceType = webserviceType;
            businessResponse.returnCode = returnCode;
            businessResponse.status = resultCode;
            businessResponse.statusMessage = statutMessage;

            ResponseBusinessObject response = resultData.getParcelable(ServiceHelper.RECEIVER_EXTRA_RESULT);
            //if there is nothing to get from the Bundle
            if (response == null) {
                //we check the result id
                long resultId = resultData.getLong(ServiceHelper.RECEIVER_EXTRA_RESULT_ID);
                //if it is present
                if (resultId != BusinessObjectDAO.ID_INVALID)
                    response = getResponseBusinessObjectById(webserviceType, resultId); //we get te result from te database

            }

            businessResponse.response = response;

            for (final OnRequestFinishedListener listener : listeners) {
                listener.onRequestFinished(requestId, succeed, businessResponse);
            }

            //if there is no listener
        } else {
            //we store the response to be able to send it again later
            mDataLibResponsesSparseArray.append(requestId, resultData);
            Out.i(LOG_TAG, "Reponse " + requestId + " mise en attente");
        }
    }

    /**
     * Get a ResponseBusinessObject from its type and its id
     * 
     * @param webserviceType type of the webservice
     * @param id the id of the {@link ResponseBusinessObject}
     * @return return the corresponding object or null if the webserviceType is not found
     */
    protected abstract ResponseBusinessObject getResponseBusinessObjectById(int webserviceType, long id);

    @Override
    public void onRequestFinished(final int resultCode, final Bundle resultData) {
        handleResult(resultCode, resultData);
    }

    /**
     * FUNCTIONS TO SEND REQUESTS TO THE DATASERVICE OR RETURN INFORMATION FROM THE DATACACHE
     */

    /**
     * This method ask to the DataLib to flush the list of cookies stored. After executing this, the next client request won't send any cookie.
     */
    public synchronized void flushCookies() {
        mServiceHelper.flushCookies();
    }

    /**
     * Function used to send the datacahe to a listener
     * 
     * @param listener The listener who will receive the cache data
     * @param url The url field in the database to get the response from
     * @return Returns the id to return to the launcher of the retrieve function
     */
    protected int sendDataCache(final OnDataListener listener, final String url, final int type, ComplexOptions complexOptions) {
        //we create and launch the data cache access
        final int requestId = ServiceHelper.generateRequestId();
    	final DataCacheRunnable runnable = new DataCacheRunnable(requestId, url, type, listener, complexOptions);
        final Thread thread = new Thread(runnable);
        thread.start();
        return requestId;
    }
    
    /**
     * Launch a request
     * 
     * @param serviceHelper the {@link ServiceHelper} to use to launch the request
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK.
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (TYPE_CACHE, TYPE_CACHE_THEN_NETWORK
     * and TYPE_NETWORK_OTHERWISE_CACHE). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param params the request parameters
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: OPTION_CONSERVE_COOKIE or OPTION_NO_DATABASE).
     * The options can be aggregated thanks to the pipe character '|' (ex: OPTION_CONSERVE_COOKIE |
     * OPTION_NO_DATABASE).
     * 
     * @param url the URL of the web service defined inside the {@link ServiceHelper}
     * 
     * @param webService the web service type defined inside the {@link DataLibService}
     * 
     * @param serviceClass the {@link Class} of the {@link DataLibService} of the project
     * 
     * @return Returns the request Id if it have been generated by the DataLib. If there is only
     * a Datacache access, the id returned is the constant {@link DataManager#DATACACHE_REQUEST}.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
	 * 
	 */
	protected int launchRequest(final ServiceHelper serviceHelper, final int policy, final OnDataListener datacacheListener, final ParameterMap params, final int options, final String url, int webService, Class<?> serviceClass, final ComplexOptions complexOptionsCache, final ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		switch (policy) {

            case TYPE_CACHE:
                //we create and launch the database access
                if (datacacheListener != null){
                	//TODO maybe improve the fingerprint process
                	DataLibRequest request = new DataLibRequest(url, params);
                	return sendDataCache(datacacheListener, request.getFingerprint(), webService, complexOptionsCache);
                }
                break;

            case TYPE_CACHE_THEN_NETWORK:
                //we create and launch the database access
                if (datacacheListener != null){
                	//TODO maybe improve the fingerprint process
                	DataLibRequest request = new DataLibRequest(url, params);
                	sendDataCache(datacacheListener, request.getFingerprint(), webService, complexOptionsCache);
                }
                //we launch the network request
    			return serviceHelper.launchRequest(options, webService, params, serviceClass, url, complexOptionsNetwork);

            case TYPE_NETWORK:
                //we launch the network request
    			return serviceHelper.launchRequest(options, webService, params, serviceClass, url, complexOptionsNetwork);

            case TYPE_NETWORK_OTHERWISE_CACHE:
                //if the device is connected
                final NetworkInfo infos = mConnectivityManager.getActiveNetworkInfo();
                if (infos != null && infos.isConnected()) {
                    //we launch the network request
        			return serviceHelper.launchRequest(options, webService, params, serviceClass, url, complexOptionsNetwork);

                } else if (datacacheListener != null) {
                	//TODO maybe improve the fingerprint process
                	DataLibRequest request = new DataLibRequest(url, params);
                	return sendDataCache(datacacheListener, request.getFingerprint(), webService, complexOptionsCache);
                }
                break;

            default:
                break;
        }
        return BAD_REQUEST;
	}


    /**
     * This class implement the fetching of information from the Datacache. It allows to execute it in another thread than the one where we launched the helper
     * function.
     * 
     * @author Eyal LEZMY
     */
    private class DataCacheRunnable implements Runnable {

        protected int mRequestId;
        protected String mId;
        protected int mType;
        protected OnDataListener mListener;
        protected ComplexOptions mOptions;

        protected DataCacheRunnable(final int requestId, final String id, final int type, final OnDataListener listener, ComplexOptions complexOptions) {
            super();
            mRequestId = requestId;
            mId = id;
            mType = type;
            mListener = listener;
            mOptions = complexOptions;
        }

        @Override
        public void run() {
        	//we set the thread as background
        	Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        	
            //we reach the data from cache
            final ResponseBusinessObject data = (ResponseBusinessObject) getBusinessObjectFromCacheByUrl(mType, mId, mOptions);

            //we return the result to the listener
            mListener.onCacheRequestFinished(mRequestId, data);
        }
    }

    /**
     * This function implements the reaching of data from the cache depending on the webservice's type given in the type parameter.
     * 
     * @param type describes the type of webservice is supposed to be reach inside the database. The value can be found in a {@link DataLibService}'s 
     * daughter class. Ex : WEBSERVICE_PREVISION_METEO
     *            
     * @return returns the {@code BusinessObjectDAO} if exists or null if the asked webservice don't implements the cache process
     */
    public abstract ResponseBusinessObject getBusinessObjectFromCacheByUrl(int type, String url, ComplexOptions complexOptions);

    /**
     * FUNCTIONS TO GET INFORMATION DIRECTLY FROM THE DATABASE
     */

    /**
     * Private function that starts an asynchronous access to the database.
     * 
     * @param databaseCode The code corresponding to the business object type to fetch in the database. The possible codes are contained inside the class
     *            {@link BusinessObjectProvider} as constants.
     * @param where A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows
     *            for the given URI.
     * @param whereArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in the order that they appear in the
     *            selection. The values will be bound as Strings.
     * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order,
     *            which may be unordered.
     * @param join Tells if the {@link MeteoWeather} objects returned have to get his children's arrays filled thanks to the database.
     * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched objects.
     * @return The request id generated by the {@link DataManager} or BAD_REQUEST constant if the request contains error (ex: listener == null)
     */
    protected int startDatabaseAsyncAccess(final int databaseCode, final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener) {

        //if there is not listener we send a BAD_REQUEST result
        if (listener == null)
            return BAD_REQUEST;

        //we manage the request's id
        final int requestId = ServiceHelper.generateRequestId();
        mDataCacheRequestIds.append(requestId, null);

        //we build and start the process to launch
        final DatabaseRunnable runnable = new DatabaseRunnable(requestId, where, whereArgs, order, join, listener, databaseCode);
        final Thread thread = new Thread(runnable);
        thread.start();

        return requestId;
    }

    /**
     * This class implement the fetching of information from the Database. It allows to execute it in another thread than the one where we launched the helper
     * function.
     * 
     * @author Eyal LEZMY
     */
    private class DatabaseRunnable implements Runnable {

        protected int mId;
        protected String mWhere;
        protected String[] mWhereArgs;
        protected String mOrder;
        protected boolean mJoin;
        protected OnDataListener mListener;
        protected int mCode;

        public DatabaseRunnable(final int id, final String mWhere, final String[] mWhereArgs, final String mOrder, final boolean mJoin, final OnDataListener mListener, final int mCode) {
            super();
            mId = id;
            this.mWhere = mWhere;
            this.mWhereArgs = mWhereArgs;
            this.mOrder = mOrder;
            this.mJoin = mJoin;
            this.mListener = mListener;
            this.mCode = mCode;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {

            ArrayList<?> result;

            result = getBusinessObjectsFromDatabase(mCode, mWhere, mWhereArgs, mOrder, mJoin);

            //we delete the pending request if it exists
            mDataCacheRequestIds.delete(mId);

            //we send the result to the listener
            mListener.onDataFromDatabase(mCode, (ArrayList<BusinessObject>) result);
        }
    }

    /**
     * Function that implements the database access request depending on the code value in parameter.
     * 
     * @param code BusinessObjectProvider's code to describe the data you need access
     * @param where A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows
     *            for the given URI.
     * @param whereArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in the order that they appear in the
     *            selection. The values will be bound as Strings.
     * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order,
     *            which may be unordered.
     * @param join Tells if the {@link PrevisionMeteo} objects returned have to get his children's arrays filled thanks to the database.
     * @return
     */
    protected abstract ArrayList<?> getBusinessObjectsFromDatabase(int code, String where, String[] whereArgs, String order, boolean join);
}
