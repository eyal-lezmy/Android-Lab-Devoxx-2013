package com.example.devoxx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	private static final String TAG = "DatabaseBench";
	private static final int NB_INSERTS = 42000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getBaseContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		long startTime, endTime;
		float duration;
		/*
		mDbHelper.onUpgrade(db, 1, 2);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < NB_INSERTS; i++) {
			mDbHelper.insertOne(db, ""+i, "title"+i, "subtitle of row "+i);
		}
		endTime = System.currentTimeMillis();
		duration = ((endTime - startTime) / 1000.f);
		Log.d(TAG, "Simple insert took: " + duration + "s");
		*/
		
		mDbHelper.onUpgrade(db, 1, 2);
		startTime = System.currentTimeMillis();
		mDbHelper.insertBulk(db, NB_INSERTS, "BULK", "subtitle of row ");
		endTime = System.currentTimeMillis();
		duration = ((endTime - startTime) / 1000.f);
		Log.d(TAG, "Bulk insert took: " + duration + "s");
		
		/*
		mDbHelper.onUpgrade(db, 1, 2);
		startTime = System.currentTimeMillis();
		mDbHelper.insertBulk(db, NB_INSERTS, "title", "subtitle of row ");
		endTime = System.currentTimeMillis();
		duration = ((endTime - startTime) / 1000.f);
		Log.d(TAG, "Fast bulk insert took: " + duration + "s");
		*/
		
		db.close();
		db.deleteDatabase(new File(FeedReaderDbHelper.DATABASE_NAME));
		
		startTime = System.currentTimeMillis();
		String dbZipName = FeedReaderDbHelper.DATABASE_NAME + ".zip";
		InputStream is = null;
		try {
			is = getAssets().open(dbZipName);
			mDbHelper.copyDataBaseFromStream(is, getDatabasePath(FeedReaderDbHelper.DATABASE_NAME));
			if (is != null)
				is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db = mDbHelper.getWritableDatabase();
		endTime = System.currentTimeMillis();
		duration = ((endTime - startTime) / 1000.f);
		Log.d(TAG, "db file loading took: " + duration + "s");
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
