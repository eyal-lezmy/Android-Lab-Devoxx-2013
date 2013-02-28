package com.example.devoxx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
					FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
					FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
					FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + 
					FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE +
					" )";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public FeedReaderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public long insertOne(SQLiteDatabase db, String id, String title, String subtitle) {

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, id);
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
		values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				FeedReaderContract.FeedEntry.TABLE_NAME,
				null,
				values);
		return newRowId;
	}

	public long insertBulk(SQLiteDatabase db, int nbToInsert, String title, String subtitle) {
		long nbInserted = 0;
		ContentValues values = new ContentValues();
		db.beginTransaction();
		try{
			for(int i = 0; i < nbToInsert; i++) {
				values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, i);
				values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title + i);
				values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle + i);
				if (db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values) != -1)
					nbInserted++;
				//db.yieldIfContendedSafely();
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return nbInserted;
	}

	public long insertBulkFaster(SQLiteDatabase db, int nbToInsert, String title, String subtitle) {
		long nbInserted = 0;
		SQLiteStatement insert = 
				db.compileStatement("insert into " + FeedReaderContract.FeedEntry.TABLE_NAME
						+ " (" + FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID
						+ "," + FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE 
						+ "," + FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + ")"
						+" values " + "(?,?,?)");
		db.beginTransaction();
		try{
			for(int i = 0; i < nbToInsert; i++) {
				insert.bindString(0, "" + i);
				insert.bindString(1, title + i);
				insert.bindString(2, subtitle + i);
				if (insert.executeInsert() != -1)
					nbInserted++;
				//db.yieldIfContendedSafely();
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return nbInserted;
	}

	public void copyDataBaseFromStream(InputStream is, File dbFile) throws IOException {
		OutputStream out = null;
		ZipInputStream in = null;
		try{
			in = new ZipInputStream(is);
			ZipEntry entry = in.getNextEntry();
			out = new FileOutputStream(dbFile);
			byte[] buffer = new byte[4096];
			int nbRead = 0;
			while ((nbRead = in.read(buffer, 0, buffer.length))>0 ){
				out.write(buffer, 0, nbRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			in.close();
			out.close();
		}
	}
}