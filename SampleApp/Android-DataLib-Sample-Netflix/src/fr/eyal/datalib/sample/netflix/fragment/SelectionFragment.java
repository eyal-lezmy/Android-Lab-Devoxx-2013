package fr.eyal.datalib.sample.netflix.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import fr.eyal.datalib.sample.cache.BitmapMemoryLruCache;
import fr.eyal.datalib.sample.cache.CacheableBitmapDrawable;
import fr.eyal.datalib.sample.netflix.R;
import fr.eyal.datalib.sample.netflix.data.model.top100.ItemTop100;
import fr.eyal.datalib.sample.netflix.data.model.top100.Top100;
import fr.eyal.datalib.sample.netflix.data.service.NetflixService;
import fr.eyal.datalib.sample.netflix.ui.GridLayout;
import fr.eyal.datalib.sample.netflix.ui.MovieItemHolder;
import fr.eyal.datalib.sample.netflix.util.Resources;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.service.DataManager;
import fr.eyal.lib.data.service.model.BusinessResponse;
import fr.eyal.lib.data.service.model.DataLibRequest;

public class SelectionFragment extends NetflixFragment {

	public static final String TAG = SelectionFragment.class.getSimpleName(); 
	
	public static final int DEFAULT_SCREEN_WIDTH = 360;
	public static final int DEFAULT_WIDTH = 170;
	public static final int DEFAULT_HEIGHT = 80;

	
	Point mPoint = new Point();
	DisplayMetrics mMetrics = new DisplayMetrics();

	ArrayList<MovieItemHolder> mMovies = new ArrayList<MovieItemHolder>();	
	ImageView mNetflixItem;
	Top100 mCurrentTop;
	BitmapMemoryLruCache mBitmapCache;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getSize(mPoint);
		display.getMetrics(mMetrics);
		setRetainInstance(true);
		mBitmapCache = Resources.getInstance().mBitmapCache;

		super.onCreate(savedInstanceState);

		//we ask for the selection content
		try {
			int requestId = mDataManager.getTop100(DataManager.TYPE_CACHE, this, DataLibRequest.OPTION_NO_OPTION, null, null);
			mRequestIds.add(requestId);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ScrollView contentView = (ScrollView) inflater.inflate(R.layout.fgmt_selection, null, false);

		GridLayout selectionLayout = (GridLayout) contentView.findViewById(R.id.selectionLayout);

		//if the data is not compliant with the interface we create it
		int size = selectionLayout.getChildCount();
		
			mMovies = new ArrayList<MovieItemHolder>(size);
			
			for (int i = 0; i < size; i++) {
				View v = selectionLayout.getChildAt(i);
				if(v instanceof RelativeLayout){
					boolean isBig = false;
					if(i == 0 || i == 5)
						isBig = true;
					
					MovieItemHolder holder = new MovieItemHolder(getActivity(), isBig);
					RelativeLayout layout = (RelativeLayout) v;
					holder.image = (ImageView) layout.getChildAt(0);
					holder.text = (TextView) layout.getChildAt(1);
					mMovies.add(holder);
				}
			}
			
			if(mCurrentTop != null)
				updateContent(mCurrentTop);
				
		return contentView;
	}

	@Override
	public void onCacheRequestFinished(int requestId, ResponseBusinessObject response) {
		mRequestIds.remove(Integer.valueOf(requestId));
		
		if(response instanceof Top100){
			Top100 top = (Top100) response;
			mCurrentTop = top;
			
			//we update the page content
			updateContent(top);

			//we compute the update time
			Calendar updateTime = Calendar.getInstance();
			updateTime.setTimeInMillis(top._updatedAt.getTimeInMillis());
			updateTime.add(Calendar.MINUTE, top.ttl);
			
			//we update the content if the ttl is consumed
			if(updateTime.compareTo(Calendar.getInstance()) <= 0) {
				try {
					mDataManager.getTop100(DataManager.TYPE_NETWORK, this, DataLibRequest.OPTION_NO_OPTION, null, null);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}			
		}
		
	}

	@Override
	public void onDataFromDatabase(int code, ArrayList<?> data) {
		
	}

	@Override
	public void onRequestFinished(int requestId, boolean suceed, BusinessResponse response) {
		mRequestIds.remove(Integer.valueOf(requestId));
		
		if(!suceed)
			return;
		
		switch (response.webserviceType) {
		
		case NetflixService.WEBSERVICE_TOP100:
			
			Top100 top100 = (Top100) response.response;
			mCurrentTop = top100;
			updateContent(top100);
			
			break;

			
//		case NetflixService.WEBSERVICE_MOVIEIMAGE:
//			
//			MovieImage img = (MovieImage) response.response;
//			mMovies.get(0).image.setImageBitmap(img.image);
//			
//			break;

		default:
			break;
		}
		
	}

	public void updateContent(Top100 top100) {
		
		if(top100 == null || top100.itemTop100 == null)
			return;
		
		ArrayList<ItemTop100> movies = top100.itemTop100;
		
		try {

			int size = mMovies.size();
			for (int i = 0; i < size; i++) {
				MovieItemHolder holder = mMovies.get(i);
				ItemTop100 item = movies.get(i);
				holder.item = item;
				holder.text.post(new UpdateText(holder.text, movies.get(i).title));

				CacheableBitmapDrawable bmp = null;
				if(item.image != null){
					String appendix = "";
					if(holder.mBigImage) //this is dirty but handles the different size of the big elements on the selection panel
						appendix = MovieItemHolder.BIG_APPENDIX;
					bmp = mBitmapCache.get(item.getPosterName() + appendix);
					if(bmp != null){
						holder.image.post(holder.new UpdatePoster(bmp, holder.image));
					}
				}
				//if we didn't updated the item
				if(bmp == null) {
					//we ask for movie's picture
					//the movie item holder will handle the bitmap received 
					mDataManager.getMovieImage(DataManager.TYPE_CACHE, holder, item.getImageUrl(), DataLibRequest.OPTION_NO_OPTION, null, null);
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public class UpdateText implements Runnable {

		TextView mView;
		String mContent;
		
		public UpdateText(TextView view, String text){
			mView = view;
			mContent = text;
		}
		
		@Override
		public void run() {
			mView.setText(mContent);
		}
	}
	
}
