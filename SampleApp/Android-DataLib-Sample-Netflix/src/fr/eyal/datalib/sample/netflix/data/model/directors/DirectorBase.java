package fr.eyal.datalib.sample.netflix.data.model.directors;

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

public class DirectorBase extends BusinessObjectDAO {

    protected long _parentId = ID_INVALID;

	//list of content
	public String id;
	public String name;
	public String bio;
	
	
	//list of childs
	public ArrayList<DirectorLink> directorLink;

    public DirectorBase() {
        super();
    }

    public DirectorBase(final long id) {
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
    public static final String CONTENT_PATH = "director";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "director";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    public static final String FIELD__PARENT_ID = "_parent_id";
	//list of content
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_BIO = "bio";

    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__UPDATED_AT, // updated_at
            FIELD__PARENT_ID, // sensors_id
		    //list of content
		    FIELD_ID, //id;
		    FIELD_NAME, //name;
		    FIELD_BIO, //bio;
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
            + Directors.DATABASE_TABLE_NAME + "(" + Directors.DATABASE_TABLE_FIELDS_NAMES[0] + ") ON DELETE CASCADE" + ", "

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
	    values.put(FIELD_ID, id);
	    values.put(FIELD_NAME, name);
	    values.put(FIELD_BIO, bio);
		
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
        final Cursor cursor = mResolver.query(CONTENT_URI, // Director
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
		// we initialize the childs tabs
        	directorLink = new ArrayList<DirectorLink>();

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
            _id = cursor.getLong(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);
            _parentId = cursor.getLong(i++);

		
			//list of content
			id = cursor.getString(i++);
			name = cursor.getString(i++);
			bio = cursor.getString(i++);

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
        for (final DirectorLink directorLinkElement : directorLink) {
            index = directorLinkElement.updateId(ids, index, parentIndex);
        }

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
        // we first remove all the childs of the childs of the object
        for (final DirectorLink directorLinkElement : directorLink) {
            directorLinkElement.deleteChildsFromDatabase(batch);
        }

		String whereClause = "";
		final String[] argsClause = { _id + "" };

        // we delete all the child entries
        whereClause = DirectorLink.FIELD__PARENT_ID + "=?"; // director_id=?
        batch.add(ContentProviderOperation.newDelete(DirectorLink.CONTENT_URI)
                .withSelection(whereClause, argsClause)
                .build());

    }


    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, final int previousResult) {
		for (final DirectorLink directorLinkElement : directorLink) {
            directorLinkElement.addIntoDatabase(batch, DirectorLink.CONTENT_URI, DirectorLink.FIELD__PARENT_ID, previousResult);
        }
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		for (final DirectorLink directorLinkElement : directorLink) {
            directorLinkElement._parentId = _id;
            directorLinkElement.addIntoDatabase(batch, DirectorLink.CONTENT_URI);
        }
    }


    @Override
    protected void fillChildrenFromDatabase() {
		String[] columns;
        String where;
        String[] args;
        Cursor cursor;

        // we define the access conditions to the objects
        columns = new String[DirectorLink.DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < DirectorLink.DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = DirectorLink.DATABASE_TABLE_FIELDS_NAMES[i];
        }

        where = DirectorLink.FIELD__PARENT_ID + "=?";
        args = new String[1];
        args[0] = _id + "";

        // we check the existence of the entry inside the database
        cursor = mResolver.query(DirectorLink.CONTENT_URI,
                columns, // all the columns of the object
                where, // director_id=?
                args, // id of the object
                null);

        // if we get a result
        if (cursor.moveToFirst()) {

            do {
                // we create and then fill the item with the Cursor
                final DirectorLink itemDirectorLink = new DirectorLink();
                itemDirectorLink.fillObjectFromCursor(cursor);
                directorLink.add(itemDirectorLink); // we add a new object to the list

            } while (cursor.moveToNext());
        }
        cursor.close();			
    }

	/**
     * This function build an array of {@link DirectorBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link Director}> if you want it as {@link Director}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<DirectorBase> result = new ArrayList<DirectorBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final DirectorBase newObject = new DirectorBase();
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

	public static final Parcelable.Creator<DirectorBase> CREATOR = new Parcelable.Creator<DirectorBase>() {
	    @Override
	    public DirectorBase createFromParcel(final Parcel in) {
	        return new DirectorBase(in);
	    }
	
	    @Override
	    public DirectorBase[] newArray(final int size) {
	        return new DirectorBase[size];
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
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(bio);
		
		//list of childs
		dest.writeParcelableArray(directorLink.toArray(new DirectorLink[directorLink.size()]), 0);
	}

	public DirectorBase(final Parcel in) {
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of content
		id = in.readString();
		name = in.readString();
		bio = in.readString();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(DirectorLink.class.getClassLoader());
		directorLink = new ArrayList<DirectorLink>();
		for (final Parcelable parcelable : items) {
		    directorLink.add((DirectorLink) parcelable);
		}
		
	}    
}

