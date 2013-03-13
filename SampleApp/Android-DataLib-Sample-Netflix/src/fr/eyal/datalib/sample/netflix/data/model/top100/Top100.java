// Start of user code fr.eyal.datalib.sample.netflix.data.model.top100.Top100. DO NOT MODIFY THE GENERATED COMMENTS
package fr.eyal.datalib.sample.netflix.data.model.top100;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Parcel;
import fr.eyal.datalib.sample.netflix.fragment.model.MovieItemResponse;

public class Top100 extends Top100Base implements MovieItemResponse{

    private static final String TAG = Top100.class.getSimpleName();

    public Top100() {
        super();
    }

    public Top100(final Parcel in) {
        super(in);
    }

    public Top100(final long id) {
        super(id);
    }

    public Top100(final String url) {
        super(url);
    }

	@Override
	public ArrayList<?> getItems() {
		return itemTop100;
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
