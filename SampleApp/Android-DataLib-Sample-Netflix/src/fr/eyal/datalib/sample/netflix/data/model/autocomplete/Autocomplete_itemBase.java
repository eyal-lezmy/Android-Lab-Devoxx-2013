package fr.eyal.datalib.sample.netflix.data.model.autocomplete;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;
import fr.eyal.lib.data.model.BusinessObject;

public class Autocomplete_itemBase implements BusinessObject {

    private static final String TAG = Autocomplete_itemBase.class.getSimpleName();

	//list of attributes
	public String attrTitleShort = "";
	
	//list of content
	public String title;

    public Autocomplete_itemBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<Autocomplete_itemBase> CREATOR = new Parcelable.Creator<Autocomplete_itemBase>() {
	    @Override
	    public Autocomplete_itemBase createFromParcel(final Parcel in) {
	        return new Autocomplete_itemBase(in);
	    }
	
	    @Override
	    public Autocomplete_itemBase[] newArray(final int size) {
	        return new Autocomplete_itemBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		//list of attributes
		dest.writeString(attrTitleShort);
		
		//list of content
		dest.writeString(title);
		
	}

	public Autocomplete_itemBase(final Parcel in) {
		//list of attributes
		attrTitleShort = in.readString();	
		
		//list of content
		title = in.readString();	
		
		
	}    
}

