package fr.eyal.datalib.sample.netflix;

import fr.eyal.datalib.sample.netflix.fragment.NewFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class SearchableActivity extends FragmentActivity {

	private static final String TAG = "SearchableActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_search);

	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      doSearch(query);
	    }
	}

	private void doSearch(String query) {
		Log.d(TAG, "doSearch(query="+query+")");
        Fragment newFragment = new NewFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.search_holder, newFragment).commit();
	}
}


