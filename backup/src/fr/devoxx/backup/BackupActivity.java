package fr.devoxx.backup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BackupActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView text = (TextView) findViewById(R.id.uuid);
        text.setText(Settings.getUuid(this));
    }
}
