package fr.eyal.datalib.sample.netflix;

import java.util.UUID;

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {
	
	public static final String SHARED_PREF_NAME = "settings";
	public static final String UUID_KEY = "uuid_key";
	
	public static final String BACKUP_KEY = "backup_settings";
	
	public static String getUuid(Context context) {
		// Retrieve UUID from Shared preferences
		String result = retrieveUuid(context);
		// Return result or generate and save a new UUID
		return result != null ? result : generateNewUuid(context);
	}
	
	private static String retrieveUuid(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		return pref.getString(UUID_KEY, null);
	}
	
	private static final String generateNewUuid(Context context) {
		// If no UUID, we need to generate one
		String result = generateUuid();
		// and save it
		saveUuid(context, result);
		// and ask for a backup
		askForBackup(context);
		// return new generated UUID
		return result;
	}
	
	private static String generateUuid() {
		return UUID.randomUUID().toString();
	}
	
	private static void saveUuid(Context context, String uuid) {
		SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(UUID_KEY, uuid);
		editor.commit();
	}
	
	private static void askForBackup(Context context) {
		BackupManager manager = new BackupManager(context);
		// ask manager to be launch a back up
		manager.dataChanged();
	}
}