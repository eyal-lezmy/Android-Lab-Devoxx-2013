package fr.eyal.datalib.sample.netflix.data.model.catalogtitles;

import java.util.ArrayList;
import android.content.OperationApplicationException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.service.model.DataLibRequest;

public class CatalogTitlesBase implements ResponseBusinessObject {

    private static final String TAG = CatalogTitlesBase.class.getSimpleName();

	//list of content
	public String error;
	public int number_of_results;
	public int start_index;
	public int results_per_page;
	
	
	//list of childs
	public ArrayList<CatalogTitle> catalogTitle;

    public CatalogTitlesBase() {
        super();
    }

    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<CatalogTitlesBase> CREATOR = new Parcelable.Creator<CatalogTitlesBase>() {
	    @Override
	    public CatalogTitlesBase createFromParcel(final Parcel in) {
	        return new CatalogTitlesBase(in);
	    }
	
	    @Override
	    public CatalogTitlesBase[] newArray(final int size) {
	        return new CatalogTitlesBase[size];
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
		dest.writeInt(number_of_results);
		dest.writeInt(start_index);
		dest.writeInt(results_per_page);
		
		//list of childs
		dest.writeParcelableArray(catalogTitle.toArray(new CatalogTitle[catalogTitle.size()]), 0);
	}

	public CatalogTitlesBase(final Parcel in) {
		//list of content
		error = in.readString();
		number_of_results = in.readInt();
		start_index = in.readInt();
		results_per_page = in.readInt();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(CatalogTitle.class.getClassLoader());
		catalogTitle = new ArrayList<CatalogTitle>();
		for (final Parcelable parcelable : items) {
		    catalogTitle.add((CatalogTitle) parcelable);
		}
		
	}    


    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {
        // Nothing to do
    }

}

