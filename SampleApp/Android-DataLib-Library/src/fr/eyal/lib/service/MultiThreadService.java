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

package fr.eyal.lib.service;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;


abstract public class MultiThreadService extends Service {

    public static final String INTENT_EXTRA_REQUEST_ID = "requestId";
    public static final String INTENT_EXTRA_RECEIVER = "receiver";

    public static final int SUCCESS_CODE = 1337;
    public static final int ERROR_CODE = -1;

    private ExecutorService mThreadPool;
    private int mMaxThreads;

    @SuppressWarnings("rawtypes")
    private ArrayList<Future> mFutureList;

    private Handler mHandler;

    public MultiThreadService(final int maxThreads) {
        mMaxThreads = maxThreads;
    }

    /**
     * Callback used when a thread has finished working
     */
    final Runnable mHasFinishedWorkingRunnable = new Runnable() {

        @SuppressWarnings("rawtypes")
        @Override
        public void run() {
            final ArrayList<Future> futureList = mFutureList;
            for (int i = 0; i < futureList.size(); i++) {
                if (futureList.get(i).isDone()) {
                    futureList.remove(i);
                    i--;
                }
            }

            if (futureList.isEmpty()) {
                stopSelf();
            }
        }
    };

    @SuppressWarnings("rawtypes")
    @Override
    public void onCreate() {
        super.onCreate();
        mThreadPool = Executors.newFixedThreadPool(mMaxThreads);
        mHandler = new Handler();
        mFutureList = new ArrayList<Future>();
    }

    @Override
    public void onStart(final Intent intent, final int startId) {
        IntentRunnable runnable = new IntentRunnable(intent);
        mFutureList.add(mThreadPool.submit(runnable));
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        onStart(intent, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    private class IntentRunnable implements Runnable {
        private Intent mIntent;

        public IntentRunnable(final Intent intent) {
            mIntent = intent;
        }

        @Override
        public void run() {
        	//we set the thread as background
        	Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            onHandleIntent(mIntent);
            mHandler.post(mHasFinishedWorkingRunnable);
        }
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * The processing happens on a worker thread that runs independently from
     * other application logic.
     * 
     * @param intent The value passed to {@link Content#startService(Intent)}.
     */
    abstract protected void onHandleIntent(Intent intent);

}
