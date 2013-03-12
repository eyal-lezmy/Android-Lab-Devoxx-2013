package fr.eyal.datalib.sample.netflix.fragment;

import java.io.UnsupportedEncodingException;

import fr.eyal.lib.data.service.DataManager;
import fr.eyal.lib.data.service.model.ComplexOptions;
import fr.eyal.lib.data.service.model.DataLibRequest;

public class NewFragment extends NetflixListFragment {

	@Override
	protected int callDataCache(ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		return mDataManager.getNewReleases(DataManager.TYPE_CACHE, this, DataLibRequest.OPTION_NO_OPTION, complexOptionsCache, complexOptionsNetwork);
	}

	@Override
	protected int callDataNetwork(ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		return mDataManager.getNewReleases(DataManager.TYPE_NETWORK, this, DataLibRequest.OPTION_NO_OPTION, complexOptionsCache, complexOptionsNetwork);
	}

	@Override
	protected int callImageCache(String url, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		return mDataManager.getMovieImage(DataManager.TYPE_CACHE, this, url, DataLibRequest.OPTION_NO_OPTION, complexOptionsCache, complexOptionsNetwork);
	}

	@Override
	protected int callImageNetwork(String url, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		return mDataManager.getMovieImage(DataManager.TYPE_NETWORK, this, url, DataLibRequest.OPTION_NO_OPTION, complexOptionsCache, complexOptionsNetwork);
	}
	
}
