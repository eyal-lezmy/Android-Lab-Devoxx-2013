// Start of user code fr.eyal.datalib.sample.netflix.data.model.newreleases.NewReleases. DO NOT MODIFY THE GENERATED COMMENTS
package fr.eyal.datalib.sample.netflix.data.model.newreleases;

import java.util.ArrayList;
import java.util.Calendar;

import fr.eyal.datalib.sample.netflix.fragment.model.MovieItemResponse;
import android.os.Parcel;

public class NewReleases extends NewReleasesBase implements MovieItemResponse{

    private static final String TAG = NewReleases.class.getSimpleName();

    public NewReleases() {
        super();
    }

    public NewReleases(final Parcel in) {
        super(in);
    }

    public NewReleases(final long id) {
        super(id);
    }

    public NewReleases(final String url) {
        super(url);
    }

	@Override
	public ArrayList<?> getItems() {
		return itemNewRelease;
	}

	@Override
	public Calendar getUpdatedAt() {
		return _updatedAt;
	}

	@Override
	public int getTtl() {
		return ttl;
	}



}
// End of user code
