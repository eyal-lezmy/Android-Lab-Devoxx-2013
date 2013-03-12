package fr.eyal.datalib.sample.netflix.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import fr.eyal.datalib.sample.cache.CacheableBitmapDrawable;
import fr.eyal.datalib.sample.netflix.MovieActivity;
import fr.eyal.datalib.sample.netflix.NetflixConfig;
import fr.eyal.datalib.sample.netflix.R;
import fr.eyal.datalib.sample.netflix.data.model.movie.Movie;
import fr.eyal.datalib.sample.netflix.data.model.movie.MovieCategory;
import fr.eyal.datalib.sample.netflix.data.model.movieimage.MovieImage;
import fr.eyal.datalib.sample.netflix.data.service.NetflixService;
import fr.eyal.datalib.sample.netflix.fragment.model.MovieItem;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.model.ResponseBusinessObjectDAO;
import fr.eyal.lib.data.service.DataManager;
import fr.eyal.lib.data.service.model.BusinessResponse;
import fr.eyal.lib.data.service.model.DataLibRequest;

public class MovieFragment extends NetflixFragment {

	MovieItem mMovieItem;
	Movie mMovie;
	
	TextView mTxtTitle;
	TextView mTxtCategory;
	TextView mTxtYear;
	TextView mTxtTime;
	TextView mTxtSynopsis;
	TextView mTxtCast1;
	TextView mTxtCast2;
	ImageView mImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle extras = getActivity().getIntent().getExtras();
		
		mMovieItem = (MovieItem) extras.get(MovieActivity.EXTRA_MOVIE);
		
		super.onCreate(savedInstanceState);
		
		try {
			int id = mDataManager.getMovie(DataManager.TYPE_NETWORK, this, Integer.parseInt(mMovieItem.getId()), DataLibRequest.OPTION_NO_OPTION, null, null);
			mRequestIds.add(id);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fgmt_movie, null, false);
				
		mTxtCast1 = (TextView) scrollView.findViewById(R.id.cast1);
		mTxtCast2 = (TextView) scrollView.findViewById(R.id.cast2);
		mTxtCategory = (TextView) scrollView.findViewById(R.id.category);
		mTxtSynopsis = (TextView) scrollView.findViewById(R.id.synopsis_content);
		mTxtTime = (TextView) scrollView.findViewById(R.id.time);
		mTxtTitle = (TextView) scrollView.findViewById(R.id.title);
		mTxtYear = (TextView) scrollView.findViewById(R.id.year);
		mImage = (ImageView) scrollView.findViewById(R.id.image);

		mTxtTitle.setText(mMovieItem.getLabel(-1));
		
		Bitmap bmp = mMovieItem.getPoster(false);
		if(bmp != null) {
			mImage.setImageBitmap(bmp);
		} else {
			try {
				int id = mDataManager.getMovieImage(DataManager.TYPE_CACHE, this, mMovieItem.getImageUrl(), DataLibRequest.OPTION_NO_OPTION, null, null);
				mRequestIds.add(id);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return scrollView;
	}

	@Override
	public void onCacheRequestFinished(int requestId, ResponseBusinessObject response) {
		mRequestIds.remove(Integer.valueOf(requestId));
		
		if(response instanceof MovieImage){
			MovieImage movieImage = (MovieImage) response;
			
			if(movieImage.image != null) {
				Bitmap bmp = movieImage.image.get();
				if(bmp != null)
				mImage.post(new UpdatePoster(bmp, mImage));
			} else {
				
			}

		} else if(response instanceof Movie){
			
			Movie movie = (Movie) response;
			
			if(!movie.isInvalidID()){
				mMovie = movie;
				mTxtTitle.post(new UpdateContent(mMovie));
				
			} else {
				try {
					int id = mDataManager.getMovie(DataManager.TYPE_NETWORK, this, Integer.parseInt(mMovieItem.getId()), DataLibRequest.OPTION_NO_OPTION, null, null);
					mRequestIds.add(id);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

		}
		
	}

	@Override
	public void onDataFromDatabase(int code, ArrayList<?> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestFinished(int requestId, boolean suceed, BusinessResponse response) {
		if(!suceed){
			if(response.response instanceof ResponseBusinessObjectDAO){
				try {
					int id = mDataManager.getMovie(DataManager.TYPE_NETWORK, this, Integer.parseInt(mMovieItem.getId()), DataLibRequest.OPTION_NO_OPTION, null, null);
					mRequestIds.add(id);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return;
		}
		
		switch (response.webserviceType) {
		
		case NetflixService.WEBSERVICE_MOVIE:

			Movie movie = (Movie) response.response;
			mTxtTitle.post(new UpdateContent(movie));
			
			break;

		default:
			break;
		}
		
	}
	
	public class UpdatePoster implements Runnable {

		Bitmap mImage;
		ImageView mView;
		Animation mFadeIn;
		
		public UpdatePoster(Bitmap image, ImageView view){
			mImage = image;
			mView = view;
			mFadeIn = new AlphaAnimation(0, 1);
			mFadeIn.setDuration(300);
		}
		
		@Override
		public void run() {
			Animation anim = mView.getAnimation();
			if(anim != null){
				anim.cancel();
				anim.reset();
				mView.setImageBitmap(mImage);
				anim.startNow();
			} else {
				mView.startAnimation(mFadeIn);
			}
		}
	}

	public class UpdateContent implements Runnable {

		Movie mMovie;
		
		public UpdateContent(Movie movie){
			mMovie = movie;
		}
		
		@Override
		public void run() {
			mTxtTitle.setText(mMovie.attrTitleRegular);
			mTxtYear.setText(""+mMovie.release_year);
			mTxtTime.setText(""+mMovie.runtime);
			
			ArrayList<MovieCategory> categories = mMovie.movieCategory;
			if(categories.size() > 0)
				mTxtCategory.setText(categories.get(0).attrLabel);
			else
				mTxtCategory.setText("");
		}
	}

}
