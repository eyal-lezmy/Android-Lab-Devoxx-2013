package fr.eyal.datalib.sample.netflix.data.model.autocomplete;

import java.util.ArrayList;
import android.content.OperationApplicationException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.service.model.DataLibRequest;

public class AutocompleteBase implements ResponseBusinessObject {

    private static final String TAG = AutocompleteBase.class.getSimpleName();

	//list of content
	public String error;
	
	
	//list of childs
	public ArrayList<Autocomplete_item> autocomplete_item;

    public AutocompleteBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<AutocompleteBase> CREATOR = new Parcelable.Creator<AutocompleteBase>() {
	    @Override
	    public AutocompleteBase createFromParcel(final Parcel in) {
	        return new AutocompleteBase(in);
	    }
	
	    @Override
	    public AutocompleteBase[] newArray(final int size) {
	        return new AutocompleteBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		//list of content
		dest.writeString(error);
		
		//list of childs
		dest.writeParcelableArray(autocomplete_item.toArray(new Autocomplete_item[autocomplete_item.size()]), 0);
	}

	public AutocompleteBase(final Parcel in) {
		//list of content
		error = in.readString();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(Autocomplete_item.class.getClassLoader());
		autocomplete_item = new ArrayList<Autocomplete_item>();
		for (final Parcelable parcelable : items) {
		    autocomplete_item.add((Autocomplete_item) parcelable);
		}
		
	}    


    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {
        // Nothing to do
    }

}

