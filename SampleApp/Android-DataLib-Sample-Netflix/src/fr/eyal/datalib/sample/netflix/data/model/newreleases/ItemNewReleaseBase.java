package fr.eyal.datalib.sample.netflix.data.model.newreleases;

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

public class ItemNewReleaseBase extends BusinessObjectDAO {

    protected long _parentId = ID_INVALID;

	//list of content
	public String title;
	public String link;
	public String description;

    public ItemNewReleaseBase() {
        super();
    }

    public ItemNewReleaseBase(final long id) {
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
    public static final String CONTENT_PATH = "itemnewrelease";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "itemnewrelease";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    public static final String FIELD__PARENT_ID = "_parent_id";
	//list of content
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__UPDATED_AT, // updated_at
            FIELD__PARENT_ID, // sensors_id
		    //list of content
		    FIELD_TITLE, //title;
		    FIELD_LINK, //link;
		    FIELD_DESCRIPTION, //description;
    };

    /**
     * SQL fields' types in the table
     */
    static String[] DATABASE_TABLE_FIELDS_TYPES = {
            // prevision_meteo_meteo_weather
            // header
            "integer", "integer", "integer",
			
		
			//list of content
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
            + NewReleases.DATABASE_TABLE_NAME + "(" + NewReleases.DATABASE_TABLE_FIELDS_NAMES[0] + ") ON DELETE CASCADE" + ", "

			//list of content
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

	
	    //list of content
	    values.put(FIELD_TITLE, title);
	    values.put(FIELD_LINK, link);
	    values.put(FIELD_DESCRIPTION, description);
		
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
        final Cursor cursor = mResolver.query(CONTENT_URI, // ItemNewRelease
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

		
			//list of content
			title = cursor.getString(i++);
			link = cursor.getString(i++);
			description = cursor.getString(i++);

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
     * This function build an array of {@link ItemNewReleaseBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link ItemNewRelease}> if you want it as {@link ItemNewRelease}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<ItemNewReleaseBase> result = new ArrayList<ItemNewReleaseBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final ItemNewReleaseBase newObject = new ItemNewReleaseBase();
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

	public static final Parcelable.Creator<ItemNewReleaseBase> CREATOR = new Parcelable.Creator<ItemNewReleaseBase>() {
	    @Override
	    public ItemNewReleaseBase createFromParcel(final Parcel in) {
	        return new ItemNewReleaseBase(in);
	    }
	
	    @Override
	    public ItemNewReleaseBase[] newArray(final int size) {
	        return new ItemNewReleaseBase[size];
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
		
		//list of content
		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(description);
		
	}

	public ItemNewReleaseBase(final Parcel in) {
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of content
		title = in.readString();
		link = in.readString();
		description = in.readString();	
		
		
	}    
}

