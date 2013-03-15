package fr.eyal.datalib.sample.netflix;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class MyBackupAgent extends BackupAgentHelper {
	
    public void onCreate() {
        SharedPreferencesBackupHelper helper =
        		new SharedPreferencesBackupHelper(this, Settings.SHARED_PREF_NAME);
        addHelper(Settings.BACKUP_KEY, helper);
    }
}
