package fr.eyal.datalib.sample.netflix.data.model.catalogtitles;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import fr.eyal.lib.data.model.BusinessObject;

public class CatalogTitleLinkBase implements BusinessObject {

    private static final String TAG = CatalogTitleLinkBase.class.getSimpleName();

	//list of attributes
	public String attrHref = "";
	public String attrRel = "";
	public String attrTitle = "";

    public CatalogTitleLinkBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<CatalogTitleLinkBase> CREATOR = new Parcelable.Creator<CatalogTitleLinkBase>() {
	    @Override
	    public CatalogTitleLinkBase createFromParcel(final Parcel in) {
	        return new CatalogTitleLinkBase(in);
	    }
	
	    @Override
	    public CatalogTitleLinkBase[] newArray(final int size) {
	        return new CatalogTitleLinkBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		//list of attributes
		dest.writeString(attrHref);
		dest.writeString(attrRel);
		dest.writeString(attrTitle);
		
	}

	public CatalogTitleLinkBase(final Parcel in) {
		//list of attributes
		attrHref = in.readString();
		attrRel = in.readString();
		attrTitle = in.readString();	
		
		
	}    
}

