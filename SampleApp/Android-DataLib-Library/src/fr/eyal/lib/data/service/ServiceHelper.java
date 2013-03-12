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
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.SparseArray;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.service.model.BusinessResponse;
import fr.eyal.lib.data.service.model.ComplexOptions;
import fr.eyal.lib.util.FileManager;
import fr.eyal.lib.util.Out;

/**
 * @author Eyal LEZMY
 */
public class ServiceHelper {

    private static final String LOG_TAG = ServiceHelper.class.getSimpleName();

    private static final int MAX_RANDOM_REQUEST_ID = Integer.MAX_VALUE;
    private static int REQUEST_ID_CPT = 0;

    /**
     * Singleton of the ServiceHelper
     */
    protected static ServiceHelper sInstance;

    /**
     * List of requests currently computing
     */
    protected SparseArray<Intent> mRequestSparseArray;
    
    /**
     * Context of execution
     */
    protected Context mContext;
    
    /**
     * Sparse List of RequestFinishedListener. Each request can be connected to one or several listeners
     */
    protected SparseArray<ArrayList<OnRequestFinishedListener>> mListenersSparseArray;
    
    /**
     * List or relayers. Every relayers wil receive each Bundle comming from the Service
     */
    protected ArrayList<OnRequestFinishedRelayer> mRelayersArray;

    
    /**
     * Handler used by the EvalReceiver
     */
    protected Handler mHandler = new Handler();
    
    /**
     * Receiver that receives the result from the DataLibService
     */
    protected EvalReceiver mEvalReceiver = new EvalReceiver(mHandler);

    //List of bundled information that can be received from the Service
    protected static final String RECEIVER_EXTRA_REQUEST_ID = "requestId";
    protected static final String RECEIVER_EXTRA_RESULT = "result";
    protected static final String RECEIVER_EXTRA_RESULT_ID = "resultId";
    protected static final String RECEIVER_EXTRA_REQUEST = "request";
    protected static final String RECEIVER_EXTRA_RETURN_CODE = "returnCode";
    protected static final String RECEIVER_EXTRA_RESULT_CODE = "resultCode";
    protected static final String RECEIVER_EXTRA_RESULT_MESSAGE = "Message";
    protected static final String RECEIVER_EXTRA_WEBSERVICE_TYPE = "webserviceType";


    /**
     * Get the instance of the {@link ServiceHelper}
     * 
     * @param context The context of execution. Any Context can be put here, the application context will be automatically used for the {@link ServiceHelper}
     * @return Returns the singleton
     */
    public static ServiceHelper getInstance(final Context context) {
    	synchronized (ServiceHelper.class) {
    		if (sInstance == null) {
    			sInstance = new ServiceHelper(context.getApplicationContext());
    		}
		}
        return sInstance;
    }

    protected ServiceHelper(final Context context) {
        mContext = context;

        mRequestSparseArray = new SparseArray<Intent>();
        mListenersSparseArray = new SparseArray<ArrayList<OnRequestFinishedListener>>();
        mRelayersArray = new ArrayList<OnRequestFinishedRelayer>();
        //we initialize the FileManager
        FileManager.getInstance(context);
    }

    /**
     * The ResultReceiver that will receive the result from the Service
     */
    private class EvalReceiver extends ResultReceiver {
        @TargetApi(Build.VERSION_CODES.CUPCAKE)
		EvalReceiver(final Handler h) {
            super(h);
        }

        @Override
        public void onReceiveResult(final int resultCode, final Bundle resultData) {

            handleResult(resultCode, resultData);
        }
    }

    /**
     * Clients may implements this interface to be notified when a request is finished
     */
    public static interface OnRequestFinishedListener extends EventListener {

        /**
         * Event fired when a request is finished.
         * 
         * @param requestId the id of the request (to see if this is the right request)
         * @param resultCode the result code (-1 if there was an error)
         * @param payload the payload. Can be any Object depending on the request
         */
        public void onRequestFinished(int requestId, boolean suceed, BusinessResponse response);
    }

    /**
     * DataManager may implements this interface to be notified when a request is finished
     */
    public static interface OnRequestFinishedRelayer extends EventListener {

        /**
         * Event fired when a request is finished.
         * 
         * @param resultCode the result code (-1 if there was an error)
         * @param resultData the result Bundle received from the Service
         */
        public void onRequestFinished(int resultCode, Bundle resultData);
    }

    /**
     * Add a {@link OnRequestFinishedListener} to this {@link ServiceHelper}. Clients may use it in order to listen to events fired when a request is finished.
     * <p>
     * <b>Warning !! </b> The listener must be detached when onPause is called in an Activity.
     * </p>
     * 
     * @param listener The listener to add to this {@link ServiceHelper}.
     */
    public synchronized void addOnRequestFinishedListener(final int requestId, final OnRequestFinishedListener listener) {

        if (listener == null)
            return;

        ArrayList<OnRequestFinishedListener> listeners = mListenersSparseArray.get(requestId);
        if (listeners == null) {
            listeners = new ArrayList<OnRequestFinishedListener>();
            mListenersSparseArray.append(requestId, listeners);
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener);
            Out.i(LOG_TAG, requestId + " Adding listener " + listener);
        } else {
            Out.i(LOG_TAG, requestId + " Already contains " + listener);
        }
    }

    /**
     * Remove a {@link OnRequestFinishedListener} to this {@link ServiceHelper}.
     * 
     * @param listenerThe listener to remove to this {@link ServiceHelper}.
     */
    public synchronized void removeOnRequestFinishedListener(final int requestId, final OnRequestFinishedListener listener) {

        if (listener == null)
            return;

        ArrayList<OnRequestFinishedListener> listeners = mListenersSparseArray.get(requestId);

        if (listeners != null) {
            listeners.remove(listener);
            Out.i(LOG_TAG, requestId + " Suppression du listener " + listener);
        } else {
            Out.i(LOG_TAG, requestId + " Pas de liste de listeners");
        }
    }

    /**
     * Add a {@link OnRequestFinishedRelayer} to this {@link ServiceHelper}. Clients may use it in order to listen to events fired when a request is finished.
     * <p>
     * <b>Warning !! </b> The listener must be detached when onPause is called in an Activity.
     * </p>
     * 
     * @param relayer The relayer to add to this {@link ServiceHelper}.
     */
    public synchronized void addOnRequestFinishedRelayer(final OnRequestFinishedRelayer relayer) {

        if (relayer == null)
            return;

        if (!mRelayersArray.contains(relayer)) {
            mRelayersArray.add(relayer);
        }
    }

    /**
     * Remove a {@link OnRequestFinishedRelayer} to this {@link ServiceHelper}.
     * 
     * @param relayer The relayer to remove to this {@link ServiceHelper}.
     */
    public synchronized void removeOnRequestFinishedRelayer(final OnRequestFinishedRelayer relayer) {

        if (relayer == null)
            return;

        mRelayersArray.remove(relayer);
    }

    /**
     * Cancel the request. This cancel the response's return of the request
     * 
     * @param id The id of the requests
     */
    public synchronized void cancelRequest(final int id) {
        //we erase the request from the requests list
        mRequestSparseArray.delete(id);
    }

    /**
     * Returns whether a request (specified by an id) is still in progress or not
     * 
     * @param requestId the id of the request
     * @return
     */
    public boolean isRequestInProgress(final int requestId) {
        return (mRequestSparseArray.indexOfKey(requestId) >= 0);
    }

    /**
     * This method manages the result of the service
     */
    protected synchronized void handleResult(final int resultCode, final Bundle resultData) {

        int requestId = resultData.getInt(RECEIVER_EXTRA_REQUEST_ID);
        int webserviceType = resultData.getInt(RECEIVER_EXTRA_WEBSERVICE_TYPE);
        int returnCode = resultData.getInt(RECEIVER_EXTRA_RETURN_CODE);
        String statutMessage = resultData.getString(RECEIVER_EXTRA_RESULT_MESSAGE);

        //state succeed or not of the request (to be sent to the OnRequestFinishedListener)
        boolean succeed = (resultCode == BusinessResponse.STATUS_OK);

        //if the request is still active
        if (mRequestSparseArray.indexOfKey(requestId) >= 0) {

            //we erase the request from the requests list
            mRequestSparseArray.delete(requestId);

            //if there is at least one relayer
            if (mRelayersArray.size() > 0) {
                for (OnRequestFinishedRelayer relayer : mRelayersArray) {
                    relayer.onRequestFinished(resultCode, resultData);
                }
            }

            //we extract the different listeners linked to this request
            ArrayList<OnRequestFinishedListener> listeners = mListenersSparseArray.get(requestId);

            //if there is a least one listener we send the signal
            if (listeners != null && listeners.size() > 0) {

                //we build the BusinessResponse
                BusinessResponse businessResponse = new BusinessResponse();
                businessResponse.webserviceType = webserviceType;
                businessResponse.returnCode = returnCode;
                businessResponse.status = resultCode;
                businessResponse.statusMessage = statutMessage;

                //we add the response business object
                businessResponse.response = (ResponseBusinessObject) resultData.getParcelable(RECEIVER_EXTRA_RESULT);

                //we send the result to each Listener, waiting for this request's response
                for (OnRequestFinishedListener listener : listeners) {
                    listener.onRequestFinished(requestId, succeed, businessResponse);
                }
                //we delete the erase the request's listeners
                mListenersSparseArray.delete(requestId);
                Out.i(LOG_TAG, "Response " + requestId + " sent!");

                //if there is no listener
            } else {
                //we store the response to be able to send it again later
                Out.i(LOG_TAG, "The response " + requestId + " does not have any listener");
            }
        }
    }

    /**
     * FUNCTIONS TO SEND REQUESTS TO THE SERVICE
     */

    /**
     * This method ask to the DataLib to flush the list of cookies stored. After executing this, the next client's request won't send any cookie.
     */
    public synchronized void flushCookies() {

        Intent i = new Intent(mContext, DataLibService.class);
        i.putExtra(DataLibService.INTENT_EXTRA_PROCESSOR_TYPE, DataLibService.COOKIES_FLUSH);

        mContext.startService(i);
    }

    public synchronized static int generateRequestId() {
        int id = REQUEST_ID_CPT++;
        if (id > MAX_RANDOM_REQUEST_ID)
            REQUEST_ID_CPT = 0;

        return id;
    }

    public int checkRequestExists(final Intent intent) {
    	
    	String sourceUrl = intent.getStringExtra(DataLibService.INTENT_EXTRA_URL);
    	ParameterMap sourceParams = intent.getParcelableExtra(DataLibService.INTENT_EXTRA_PARAMS);

    	String sourceParamsUrl = "";
    	if(sourceParams != null)
    		sourceParamsUrl = sourceParams.urlEncode(true);
    	

    	// Check if a request is already launched
        final int requestSparseArrayLength = mRequestSparseArray.size();
        for (int i = 0; i < requestSparseArrayLength; i++) {
        	
        	//TODO check the exception occuring sometime here when there is a very intensive request rythme
            final Intent savedIntent = mRequestSparseArray.valueAt(i);

            if(savedIntent == null)
            	continue;
            
            String savedUrl = savedIntent.getStringExtra(DataLibService.INTENT_EXTRA_URL);
            ParameterMap savedParams = savedIntent.getParcelableExtra(DataLibService.INTENT_EXTRA_PARAMS);
            
            String savedParamsUrl = "";
            if(savedParams != null)
            	savedParamsUrl = savedParams.urlEncode(true);

            //we compare the whole URL
            if (savedUrl.equals(sourceUrl) && savedParamsUrl.equals(sourceParamsUrl))
                return mRequestSparseArray.keyAt(i);

        }
        return -1;
    }

    /**
     * Launch the request to the Service through an Intent
     * 
     * @param options
     * @param webserviceType
     * @param params
     * @param service
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public int launchRequest(final int options, final int webserviceType, final ParameterMap params, final Class<?> service, final String url, final ComplexOptions complexOptions) throws UnsupportedEncodingException {

        return launchRequest(options, webserviceType, params, service, url, complexOptions, -1);
    }

    /**
     * Launch the request to the Service through an Intent
     * 
     * @param options
     * @param webserviceType
     * @param params
     * @param service
     * @param url
     * @param parseType
     * @return
     * @throws UnsupportedEncodingException
     */
    public int launchRequest(final int options, final int webserviceType, final ParameterMap params, final Class<?> service, final String url, final ComplexOptions complexOptions, final int parseType) throws UnsupportedEncodingException {

        //we buid the Intent to send
        Intent i = new Intent(mContext, service);
        i.putExtra(DataLibService.INTENT_EXTRA_RECEIVER, mEvalReceiver);
        i.putExtra(DataLibService.INTENT_EXTRA_PROCESSOR_TYPE, webserviceType);
        i.putExtra(DataLibService.INTENT_EXTRA_REQUEST_OPTION, options);
        if (parseType > 0)
            i.putExtra(DataLibService.INTENT_EXTRA_PARSE_TYPE, parseType);

        i.putExtra(DataLibService.INTENT_EXTRA_URL, url);
        i.putExtra(DataLibService.INTENT_EXTRA_PARAMS, params);
        i.putExtra(DataLibService.INTENT_EXTRA_COMPLEX_OPTIONS, complexOptions);

        int requestId = checkRequestExists(i);

        //if a requestId have been found
        if (requestId > 0)
            return requestId; //we return it to the user

        requestId = generateRequestId();
        mListenersSparseArray.append(requestId, new ArrayList<ServiceHelper.OnRequestFinishedListener>());

        i.putExtra(DataLibService.INTENT_EXTRA_REQUEST_ID, requestId);

        mContext.startService(i);

        mRequestSparseArray.append(requestId, i);

        return requestId;
    }

    /**
     * Get the URL which is identifying a specific request thanks to its base url and the parameters
     * 
     * @param urlBase the base url of the service
     * @param params a HashMap the parameters to transmit
     * @return returns the url generated
     * @throws UnsupportedEncodingException
     */
    public static String getServiceUrl(final String urlBase, final ParameterMap params) throws UnsupportedEncodingException {

        return urlBase + "?" + params.urlEncode();
    }
    
    /**
     * Get the application context
     * 
     * @return returns the application context if defined or <code>null</code>
     */
    public Context getApplicationContext(){
    	return mContext;
    }

}
