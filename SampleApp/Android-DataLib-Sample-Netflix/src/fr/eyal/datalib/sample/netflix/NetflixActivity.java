package fr.eyal.datalib.sample.netflix;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Button;
import android.widget.SearchView;

import com.viewpagerindicator.TitlePageIndicator;

import fr.eyal.datalib.sample.cache.BitmapMemoryLruCache;
import fr.eyal.datalib.sample.netflix.util.Resources;
import fr.eyal.lib.util.Out;

public class NetflixActivity extends FragmentActivity {

	Button mBtnSearchTitle;
	Button mBtnNewReleases;
	Button mBtnTop100;
	Button mBtnTopGenre;
	Button mBtnAutocomplete;
	Button mBtnGetPeople;
	Button mBtnGetFilmography;
	Button mBtnGetSynopsis;
	Button mBtnGetMovie;
	Button mBtnGetDirectors;
	Button mBtnGetCast;
	
	NetflixFragmentAdapter mAdapter;
	ViewPager mViewPager;
	TitlePageIndicator mTitlePageIndicator;
	BitmapMemoryLruCache mBitmapCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netflix);
		
		//we initialize the bitmap cache
		final int maxMemory = (int) Runtime.getRuntime().maxMemory()/5;
		Out.w("", "CACHE SIZE = "+maxMemory);
		mBitmapCache = new BitmapMemoryLruCache(maxMemory);
		Resources.getInstance().mBitmapCache = mBitmapCache;
		
		mAdapter = new NetflixFragmentAdapter(getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(mAdapter);
		
		mTitlePageIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mTitlePageIndicator.setViewPager(mViewPager);
		
//		mBtnGetCast = (Button) findViewById(R.id.getCast);
//		mBtnGetDirectors = (Button) findViewById(R.id.getDirectors);
//		mBtnGetFilmography = (Button) findViewById(R.id.getFilmography);
//		mBtnGetMovie = (Button) findViewById(R.id.getMovie);
//		mBtnGetPeople = (Button) findViewById(R.id.getPeople);
//		mBtnGetSynopsis = (Button) findViewById(R.id.getSynopsis);
//		mBtnSearchTitle = (Button) findViewById(R.id.searchTitle);
//		mBtnAutocomplete = (Button) findViewById(R.id.autocomplete);
//		mBtnNewReleases = (Button) findViewById(R.id.newReleases);
//		mBtnTop100 = (Button) findViewById(R.id.top100);
//		mBtnTopGenre = (Button) findViewById(R.id.topGenre);
//		
//		final int movieId = 70206672;
//		final int peopleId = 30144804;
//		
//		mBtnGetCast.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getCast(movieId, NetflixActivity.this, mRequestIds);
//			}
//		});
//
//		mBtnGetDirectors.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getDirectors(movieId, NetflixActivity.this, mRequestIds);
//			}
//		});
//		
//		mBtnGetFilmography.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getFilmography(peopleId, NetflixActivity.this, mRequestIds);
//			}
//		});
//
//		mBtnGetMovie.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getMovie(movieId, NetflixActivity.this, mRequestIds);
//			}
//		});
//
//		mBtnGetPeople.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getPeople(peopleId, NetflixActivity.this, mRequestIds);
//			}
//		});
//
//		mBtnGetSynopsis.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.getSynopsis(movieId, NetflixActivity.this, mRequestIds);
//			}
//		});
//
//		
//		mBtnSearchTitle.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDataManager.searchCatalogTitle("chris", NetflixActivity.this, mRequestIds);
//			}
//		});
//		
//		mBtnNewReleases.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int id;
//				try {
//					id = mDataManager.getNewReleases(DataManager.TYPE_CACHE_THEN_NETWORK, NetflixActivity.this, DataLibRequest.OPTION_NO_OPTION);
//					mRequestIds.add(id);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//		mBtnAutocomplete.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int id;
//				try {
//					id = mDataManager.getAutocomplete(NetflixActivity.this, NetflixConfig.CONSUMER_KEY, "chr", DataLibRequest.OPTION_NO_OPTION);
//					mRequestIds.add(id);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		mBtnAutocomplete.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int id;
//				try {
//					id = mDataManager.getAutocomplete(NetflixActivity.this, NetflixConfig.CONSUMER_KEY, "chr", DataLibRequest.OPTION_NO_OPTION);
//					mRequestIds.add(id);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		mBtnTop100.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int id;
//				try {
//					id = mDataManager.getTop100(DataManager.TYPE_NETWORK, NetflixActivity.this, DataLibRequest.OPTION_NO_OPTION);
//					mRequestIds.add(id);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//		mBtnTopGenre.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				int id;
//				try {
//					id = mDataManager.getTopGenre(DataManager.TYPE_NETWORK, NetflixActivity.this, NetflixUtils.GENRE_ACTION, DataLibRequest.OPTION_NO_OPTION);
//					mRequestIds.add(id);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_netflix, menu);

	    // Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

		return true;
	}
}
