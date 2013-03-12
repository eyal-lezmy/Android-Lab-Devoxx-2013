package fr.eyal.datalib.sample.netflix.data.model.movie;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;

public class MovieCategoryBase extends BusinessObjectDAO {

    protected long _parentId = ID_INVALID;

	//list of attributes
	public String attrScheme = "";
	public String attrLabel = "";
	public String attrTerm = "";

    public MovieCategoryBase() {
        super();
    }

    public MovieCategoryBase(final long id) {
        super(id);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    /**
     * DATABASE MANAGEMENT
     */

    /**
     * Constants used with a ContentProvider's access
     */
    public static final String CONTENT_PATH = "moviecategory";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "moviecategory";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    public static final String FIELD__PARENT_ID = "_parent_id";
	//list of attributes
    public static final String FIELD_ATTR_SCHEME = "ATTR_scheme";
    public static final String FIELD_ATTR_LABEL = "ATTR_label";
    public static final String FIELD_ATTR_TERM = "ATTR_term";


    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__UPDATED_AT, // updated_at
            FIELD__PARENT_ID, // sensors_id
            //list of attributes
		    FIELD_ATTR_SCHEME, //scheme;
		    FIELD_ATTR_LABEL, //label;
		    FIELD_ATTR_TERM, //term;

    };

    /**
     * SQL fields' types in the table
     */
    static String[] DATABASE_TABLE_FIELDS_TYPES = {
            // prevision_meteo_meteo_weather
            // header
            "integer", "integer", "integer",
			
			//list of attributes
			"text",
			"text",
			"text",
		

    };

    /**
     * SQLite scripts to create the table
     */
    public static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" 
			+ DATABASE_TABLE_FIELDS_NAMES[0] + " " + DATABASE_TABLE_FIELDS_TYPES[0] + " PRIMARY KEY AUTOINCREMENT" + ", "
            + DATABASE_TABLE_FIELDS_NAMES[1] + " " + DATABASE_TABLE_FIELDS_TYPES[1] + ", "
            + DATABASE_TABLE_FIELDS_NAMES[2] + " " + DATABASE_TABLE_FIELDS_TYPES[2] + " REFERENCES "
            + Movie.DATABASE_TABLE_NAME + "(" + Movie.DATABASE_TABLE_FIELDS_NAMES[0] + ") ON DELETE CASCADE" + ", "

			//list of attributes
			+ DATABASE_TABLE_FIELDS_NAMES[3] + " " + DATABASE_TABLE_FIELDS_TYPES[3] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[4] + " " + DATABASE_TABLE_FIELDS_TYPES[4] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[5] + " " + DATABASE_TABLE_FIELDS_TYPES[5] + "); "

            + "CREATE INDEX "
            + DATABASE_TABLE_NAME + "_" + DATABASE_TABLE_FIELDS_NAMES[0] + " ON " + DATABASE_TABLE_NAME + " (" + DATABASE_TABLE_FIELDS_NAMES[0] + ");";


    @Override
    protected ContentResolver getContentResolver() {
        try {
        return NetflixProvider.getContentResolver();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        // we create the values to add
        final ContentValues values = new ContentValues();

        if (_id != ID_INVALID)
            values.put(FIELD__ID, _id);
        if (_parentId != ID_INVALID)
            values.put(FIELD__PARENT_ID, _parentId);
        values.put(FIELD__UPDATED_AT, _updatedAt.getTimeInMillis() / 1000);

		//list of attributes
	    values.put(FIELD_ATTR_SCHEME, attrScheme);
	    values.put(FIELD_ATTR_LABEL, attrLabel);
	    values.put(FIELD_ATTR_TERM, attrTerm);
	
		
        return values;
    }

    @Override
    protected void fillFromDatabaseById(final long id) {
        // we define the access condition to the object
        final String[] columns = new String[DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = DATABASE_TABLE_FIELDS_NAMES[i];
        }

        final String where = FIELD__ID + "=?";
        final String[] args = { id + "" };

        // we check the existence inside the database
        final Cursor cursor = mResolver.query(CONTENT_URI, // MovieCategory
                columns, // id
                where, // id=?
                args, // id

                FIELD__UPDATED_AT + " DESC"); // ORDER BY updated_at DESC

        // we fill the object thanks to the Cursor recieved
        if (cursor.moveToFirst()) {
            fillObjectFromCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        // we get the childs content
        fillChildrenFromDatabase();

    }

    @Override
    protected void fillObjectFromCursor(final Cursor cursor) {

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
            _id = cursor.getLong(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);
            _parentId = cursor.getLong(i++);

			//list of attributes
			attrScheme = cursor.getString(i++);
			attrLabel = cursor.getString(i++);
			attrTerm = cursor.getString(i++);
		

        } else {
            _id = ID_INVALID;
        }

    }

    @Override
    public ContentProviderResult[] save() throws OperationApplicationException, RemoteException {

        // we define the access conditions to the object
        final String[] columns = {
                FIELD__ID
        };
        final String where = FIELD__ID + " LIKE ?"; // id LIKE {this.id}
        final String[] args = {
                _id + ""
        };

        return save(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);

    }


	@Override
	protected int updateChildrenId(long[] ids, int index, int parentIndex) {
		// Nothing to do

		return index;
	}

    @Override
	public int updateId(long[] ids, int index, int parentIndex) {
		if(parentIndex >= 0)
			_parentId = ids[parentIndex];
		
		return super.updateId(ids, index);
	}


    @Override
    protected void updateIntoTheDatabase(final ArrayList<ContentProviderOperation> batch) {
        updateIntoTheDatabase(batch, CONTENT_URI);
    }

    @Override
    public ContentProviderResult[] delete() throws OperationApplicationException, RemoteException {
        // we define the access conditions to the object
        final String[] columns = { FIELD__ID };
        final String where = FIELD__ID + "=?"; // _url LIKE {this.url}
        final String[] args = { _id + "" };

        return delete(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);
    }

    @Override
    public boolean deleteFromDatabase(final ArrayList<ContentProviderOperation> batch) {
        return deleteFromDatabase(batch, CONTENT_URI);
    }

    @Override
    public void deleteChildsFromDatabase(final ArrayList<ContentProviderOperation> batch) {
		// Nothing to do
    }


    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, final int previousResult) {
		// Nothing to do
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		// Nothing to do
    }


    @Override
    protected void fillChildrenFromDatabase() {
		// Nothing to do
    }

	/**
     * This function build an array of {@link MovieCategoryBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link MovieCategory}> if you want it as {@link MovieCategory}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<MovieCategoryBase> result = new ArrayList<MovieCategoryBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final MovieCategoryBase newObject = new MovieCategoryBase();
                newObject.fillObjectFromCursor(c);
                // if it's asked we fill the childs of the item
                if (join) {
                    newObject.fillChildrenFromDatabase();
                }
                // we add the element to the result
                result.add(newObject);
            } while (c.moveToNext());
        }

        return result;
    }


    /**
     * PARCELABLE MANAGMENT
     */

	public static final Parcelable.Creator<MovieCategoryBase> CREATOR = new Parcelable.Creator<MovieCategoryBase>() {
	    @Override
	    public MovieCategoryBase createFromParcel(final Parcel in) {
	        return new MovieCategoryBase(in);
	    }
	
	    @Override
	    public MovieCategoryBase[] newArray(final int size) {
	        return new MovieCategoryBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		// Business Object DAO
		dest.writeLong(_id);
		dest.writeLong(_updatedAt.getTimeInMillis());
		
		//list of attributes
		dest.writeString(attrScheme);
		dest.writeString(attrLabel);
		dest.writeString(attrTerm);
		
	}

	public MovieCategoryBase(final Parcel in) {
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of attributes
		attrScheme = in.readString();
		attrLabel = in.readString();
		attrTerm = in.readString();	
		
		
	}    
}

