package fr.devoxx.backup;

import java.io.IOException;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class MyBackupAgent extends BackupAgentHelper {
	
	private static final String TAG = "MyBackupAgent";

    public void onCreate() {
    	Log.d(TAG, "onCreate()");
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, Settings.SHARED_PREF_NAME);
        addHelper(Settings.BACKUP_KEY, helper);
    }
    
    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
    		ParcelFileDescriptor newState) throws IOException {
    	Log.d(TAG, "onRestore()");
    	super.onRestore(data, appVersionCode, newState);
    	RuntimeException runtime = new RuntimeException();
    	runtime.fillInStackTrace();
    	Log.d(TAG, "call stack", runtime.fillInStackTrace());
    	getApplicationContext().startActivity(new Intent(getApplicationContext(), BackupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    
    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
    		ParcelFileDescriptor newState) throws IOException {
    	Log.d(TAG, "onBackup()");
    	super.onBackup(oldState, data, newState);
    }
}
