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
import fr.eyal.lib.data.model.ResponseBusinessObjectDAO;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.util.Out;
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;

public class NewReleasesBase extends ResponseBusinessObjectDAO {

    private static final String TAG = NewReleasesBase.class.getSimpleName();

	//list of attributes
	public String attrVersion = "";
	
	//list of content
	public String title;
	public int ttl;
	public String link;
	public String description;
	public String language;
	
	
	//list of childs
	public ArrayList<ItemNewRelease> itemNewRelease;

    public NewReleasesBase() {
        super();
    }

    public NewReleasesBase(final long id) {
        super(id);
    }

    public NewReleasesBase(final String url) {
        super(url);
    }

    /**
     * DATABASE MANAGEMENT
     */

    /**
     * Constants used with a ContentProvider's access
     */
    public static final String CONTENT_PATH = "newreleases";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "newreleases";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__URL = "_url";
    public static final String FIELD__TTL = "_ttl";
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
	//list of attributes
    public static final String FIELD_ATTR_VERSION = "ATTR_version";

    //list of content
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_TTL = "ttl";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LANGUAGE = "language";


    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__URL, // url
            FIELD__TTL, // ttl
            FIELD__UPDATED_AT, // updated_at
            //list of attributes
		    FIELD_ATTR_VERSION, //version";

		    //list of content
		    FIELD_TITLE, //title";
		    FIELD_TTL, //ttl";
		    FIELD_LINK, //link";
		    FIELD_DESCRIPTION, //description";
		    FIELD_LANGUAGE, //language";
   };

    /**
     * SQL fields' types in the table
     */
    static String[] DATABASE_TABLE_FIELDS_TYPES = {
            // prevision_meteo_meteo_weather
            // header
            "integer", "text", "integrer", "integrer",
		
			//list of attributes
			"text",
		
		    //list of content
			"text",
			"integer",
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
            + DATABASE_TABLE_FIELDS_NAMES[2] + " " + DATABASE_TABLE_FIELDS_TYPES[2] + ", "
            + DATABASE_TABLE_FIELDS_NAMES[3] + " " + DATABASE_TABLE_FIELDS_TYPES[3] + ", "
			//list of attributes
			+ DATABASE_TABLE_FIELDS_NAMES[4] + " " + DATABASE_TABLE_FIELDS_TYPES[4] + ", "

		    //list of content
			+ DATABASE_TABLE_FIELDS_NAMES[5] + " " + DATABASE_TABLE_FIELDS_TYPES[5] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[6] + " " + DATABASE_TABLE_FIELDS_TYPES[6] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[7] + " " + DATABASE_TABLE_FIELDS_TYPES[7] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[8] + " " + DATABASE_TABLE_FIELDS_TYPES[8] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[9] + " " + DATABASE_TABLE_FIELDS_TYPES[9] + "); "
            + "CREATE INDEX " + DATABASE_TABLE_NAME + "_" + DATABASE_TABLE_FIELDS_NAMES[0] + " " + DATABASE_TABLE_NAME + " (" + DATABASE_TABLE_FIELDS_NAMES[0] + ");"
            + "CREATE INDEX " + DATABASE_TABLE_NAME + "_" + DATABASE_TABLE_FIELDS_NAMES[1] + " ON " + DATABASE_TABLE_NAME + " (" + DATABASE_TABLE_FIELDS_NAMES[1] + ");";


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

        values.put(FIELD__URL, _url);
        values.put(FIELD__TTL, _ttl);
        if (_updatedAt != null) {
            values.put(FIELD__UPDATED_AT, _updatedAt.getTimeInMillis() / 1000);
        }

		//list of attributes
	    values.put(FIELD_ATTR_VERSION, attrVersion);
	
	    //list of content
	    values.put(FIELD_TITLE, title);
	    values.put(FIELD_TTL, ttl);
	    values.put(FIELD_LINK, link);
	    values.put(FIELD_DESCRIPTION, description);
	    values.put(FIELD_LANGUAGE, language);
		
        return values;
    }

    @Override
    protected void fillFromDatabaseByUrl(final String url) {
        // on defini les conditions d'acces a l'objet
        final String[] columns = new String[DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = DATABASE_TABLE_FIELDS_NAMES[i];
        }

        final String where = FIELD__URL + " LIKE ?";
        final String[] args = { url + "" };

        // we check the entry's existence
        final Cursor cursor = mResolver.query(CONTENT_URI, 
                columns, // all the table's columns
                where, // _url LIKE ?
                args, // url
                FIELD__UPDATED_AT + " DESC"); // ORDER BY updated_at DESC

        // we fill the object with the cursor content
        if (cursor.moveToFirst()) {
            fillObjectFromCursor(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        // we get the child infos
        fillChildrenFromDatabase();
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
        final Cursor cursor = mResolver.query(CONTENT_URI, // prevision_meteo
                columns, // _id
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
		// we initialize the childs tabs
        	itemNewRelease = new ArrayList<ItemNewRelease>();

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
			_id = cursor.getLong(i++);
            _url = cursor.getString(i++);
            _ttl = cursor.getInt(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);

			//list of attributes
			attrVersion = cursor.getString(i++);
		
		    //list of content
			title = cursor.getString(i++);
			ttl = cursor.getInt(i++);
			link = cursor.getString(i++);
			description = cursor.getString(i++);
			language = cursor.getString(i++);

        } else {
            _id = ID_INVALID;
        }

    }


    @Override
    public void save(final DataLibRequest request) throws RemoteException, OperationApplicationException {
        _url = request.getFingerprint();
        save();
    }

    @Override
    public ContentProviderResult[] save() throws OperationApplicationException, RemoteException {

        // on defini les conditions d'acces a l'objet
        final String[] columns = { FIELD__ID };
        final String where = FIELD__URL + " LIKE ?"; // id LIKE {this.id}
        final String[] args = { _url + "" };

        return save(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);

    }

	@Override
	protected int updateChildrenId(long[] ids, int index, int parentIndex) {
        for (final ItemNewRelease itemNewReleaseElement : itemNewRelease) {
            index = itemNewReleaseElement.updateId(ids, index, parentIndex);
        }

		return index;
	}

	@Override
	public int updateId(long[] ids, int index, int parentIndex) {
		return updateId(ids, index);
	}

    @Override
    public ContentProviderResult[] delete() throws OperationApplicationException, RemoteException {
        // we define the access conditions to the object
        final String[] columns = { FIELD__ID };
        final String where = FIELD__URL + "=?"; // _url LIKE {this.url}
        final String[] args = { _url + "" };

        return delete(columns, where, args, CONTENT_URI, NetflixProvider.AUTHORITY);
    }

    @Override
    protected void updateIntoTheDatabase(final ArrayList<ContentProviderOperation> batch) {
        updateIntoTheDatabase(batch, CONTENT_URI);
    }

    @Override
    public boolean deleteFromDatabase(final ArrayList<ContentProviderOperation> batch) {
        return deleteFromDatabase(batch, CONTENT_URI);
    }


    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, final int previousResult) {
		for (final ItemNewRelease itemNewReleaseElement : itemNewRelease) {
            itemNewReleaseElement.addIntoDatabase(batch, ItemNewRelease.CONTENT_URI, ItemNewRelease.FIELD__PARENT_ID, previousResult);
        }
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		for (final ItemNewRelease itemNewReleaseElement : itemNewRelease) {
            itemNewReleaseElement._parentId = _id;
            itemNewReleaseElement.addIntoDatabase(batch, ItemNewRelease.CONTENT_URI);
        }
    }

    @Override
    public void deleteChildsFromDatabase(final ArrayList<ContentProviderOperation> batch) {
        Out.d(TAG, "deleting childs of " + _id);
        // we first remove all the childs of the childs of the object
        for (final ItemNewRelease itemNewReleaseElement : itemNewRelease) {
            itemNewReleaseElement.deleteChildsFromDatabase(batch);
        }

		String whereClause = "";
		final String[] argsClause = { _id + "" };

        // we delete all the child entries
        whereClause = ItemNewRelease.FIELD__PARENT_ID + "=?"; // itemnewrelease_id=?
        batch.add(ContentProviderOperation.newDelete(ItemNewRelease.CONTENT_URI)
                .withSelection(whereClause, argsClause)
                .build());

    }



    @Override
    protected void fillChildrenFromDatabase() {
		String[] columns;
        String where;
        String[] args;
        Cursor cursor;

        // we define the access conditions to the objects
        columns = new String[ItemNewRelease.DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < ItemNewRelease.DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = ItemNewRelease.DATABASE_TABLE_FIELDS_NAMES[i];
        }

        where = ItemNewRelease.FIELD__PARENT_ID + "=?";
        args = new String[1];
        args[0] = _id + "";

        // we check the existence of the entry inside the database
        cursor = mResolver.query(ItemNewRelease.CONTENT_URI,
                columns, // all the columns of the object
                where, // itemnewrelease_id=?
                args, // id of the object
                null);

        Out.d(TAG, "Adding " + cursor.getCount() + " childs into " + TAG);

        // if we get a result
        if (cursor.moveToFirst()) {

            do {
                // we create and then fill the item with the Cursor
                final ItemNewRelease itemItemNewRelease = new ItemNewRelease();
                itemItemNewRelease.fillObjectFromCursor(cursor);
                itemNewRelease.add(itemItemNewRelease); // we add a new object to the list

            } while (cursor.moveToNext());
        }
        cursor.close();			
    }

	/**
     * This function build an array of {@link NewReleasesBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link NewReleases}> if you want it as {@link NewReleases}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<NewReleasesBase> result = new ArrayList<NewReleasesBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final NewReleasesBase newObject = new NewReleasesBase();
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

	public static final Parcelable.Creator<NewReleasesBase> CREATOR = new Parcelable.Creator<NewReleasesBase>() {
	    @Override
	    public NewReleasesBase createFromParcel(final Parcel in) {
	        return new NewReleasesBase(in);
	    }
	
	    @Override
	    public NewReleasesBase[] newArray(final int size) {
	        return new NewReleasesBase[size];
	    }
	};
	
	@Override
	public int describeContents() {
	    return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		// ResponseBusinessObject DAO
		dest.writeLong(_ttl);
		dest.writeString(_url);
		
		// Business Object DAO
		dest.writeLong(_id);
		dest.writeLong(_updatedAt.getTimeInMillis());
		
		//list of attributes
		dest.writeString(attrVersion);
		
		//list of content
		dest.writeString(title);
		dest.writeInt(ttl);
		dest.writeString(link);
		dest.writeString(description);
		dest.writeString(language);
		
		//list of childs
		dest.writeParcelableArray(itemNewRelease.toArray(new ItemNewRelease[itemNewRelease.size()]), 0);
	}

	public NewReleasesBase(final Parcel in) {
		// ResponseBusinessObject DAO
		_ttl = in.readLong();
		_url = in.readString();
		
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of attributes
		attrVersion = in.readString();	
		
		//list of content
		title = in.readString();
		ttl = in.readInt();
		link = in.readString();
		description = in.readString();
		language = in.readString();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(ItemNewRelease.class.getClassLoader());
		itemNewRelease = new ArrayList<ItemNewRelease>();
		for (final Parcelable parcelable : items) {
		    itemNewRelease.add((ItemNewRelease) parcelable);
		}
		
	}    


}

