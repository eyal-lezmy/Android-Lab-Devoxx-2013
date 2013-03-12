package fr.eyal.datalib.sample.netflix.data.model.catalogtitles;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import fr.eyal.lib.data.model.BusinessObject;

public class CatalogTitleCategoryBase implements BusinessObject {

    private static final String TAG = CatalogTitleCategoryBase.class.getSimpleName();

	//list of attributes
	public String attrLabel = "";
	public String attrTerm = "";

    public CatalogTitleCategoryBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<CatalogTitleCategoryBase> CREATOR = new Parcelable.Creator<CatalogTitleCategoryBase>() {
	    @Override
	    public CatalogTitleCategoryBase createFromParcel(final Parcel in) {
	        return new CatalogTitleCategoryBase(in);
	    }
	
	    @Override
	    public CatalogTitleCategoryBase[] newArray(final int size) {
	        return new CatalogTitleCategoryBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		//list of attributes
		dest.writeString(attrLabel);
		dest.writeString(attrTerm);
		
	}

	public CatalogTitleCategoryBase(final Parcel in) {
		//list of attributes
		attrLabel = in.readString();
		attrTerm = in.readString();	
		
		
	}    
}

