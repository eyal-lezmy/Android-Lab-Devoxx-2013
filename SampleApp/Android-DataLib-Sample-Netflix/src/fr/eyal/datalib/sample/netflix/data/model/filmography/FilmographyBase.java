package fr.eyal.datalib.sample.netflix.data.model.filmography;

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

public class FilmographyBase extends ResponseBusinessObjectDAO {

    private static final String TAG = FilmographyBase.class.getSimpleName();

	//list of content
	public String error;
	
	
	//list of childs
	public ArrayList<Filmography_item> filmography_item;

    public FilmographyBase() {
        super();
    }

    public FilmographyBase(final long id) {
        super(id);
    }

    public FilmographyBase(final String url) {
        super(url);
    }

    /**
     * DATABASE MANAGEMENT
     */

    /**
     * Constants used with a ContentProvider's access
     */
    public static final String CONTENT_PATH = "filmography";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "filmography";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__URL = "_url";
    public static final String FIELD__TTL = "_ttl";
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    //list of content
    public static final String FIELD_ERROR = "error";


    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__URL, // url
            FIELD__TTL, // ttl
            FIELD__UPDATED_AT, // updated_at
		    //list of content
		    FIELD_ERROR, //error";
   };

    /**
     * SQL fields' types in the table
     */
    static String[] DATABASE_TABLE_FIELDS_TYPES = {
            // prevision_meteo_meteo_weather
            // header
            "integer", "text", "integrer", "integrer",
		
		
		    //list of content
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

		    //list of content
			+ DATABASE_TABLE_FIELDS_NAMES[4] + " " + DATABASE_TABLE_FIELDS_TYPES[4] + "); "
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

	
	    //list of content
	    values.put(FIELD_ERROR, error);
		
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
        	filmography_item = new ArrayList<Filmography_item>();

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
			_id = cursor.getLong(i++);
            _url = cursor.getString(i++);
            _ttl = cursor.getInt(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);

		
		    //list of content
			error = cursor.getString(i++);

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
        for (final Filmography_item filmography_itemElement : filmography_item) {
            index = filmography_itemElement.updateId(ids, index, parentIndex);
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
		for (final Filmography_item filmography_itemElement : filmography_item) {
            filmography_itemElement.addIntoDatabase(batch, Filmography_item.CONTENT_URI, Filmography_item.FIELD__PARENT_ID, previousResult);
        }
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		for (final Filmography_item filmography_itemElement : filmography_item) {
            filmography_itemElement._parentId = _id;
            filmography_itemElement.addIntoDatabase(batch, Filmography_item.CONTENT_URI);
        }
    }

    @Override
    public void deleteChildsFromDatabase(final ArrayList<ContentProviderOperation> batch) {
        Out.d(TAG, "deleting childs of " + _id);
        // we first remove all the childs of the childs of the object
        for (final Filmography_item filmography_itemElement : filmography_item) {
            filmography_itemElement.deleteChildsFromDatabase(batch);
        }

		String whereClause = "";
		final String[] argsClause = { _id + "" };

        // we delete all the child entries
        whereClause = Filmography_item.FIELD__PARENT_ID + "=?"; // filmography_item_id=?
        batch.add(ContentProviderOperation.newDelete(Filmography_item.CONTENT_URI)
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
        columns = new String[Filmography_item.DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < Filmography_item.DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = Filmography_item.DATABASE_TABLE_FIELDS_NAMES[i];
        }

        where = Filmography_item.FIELD__PARENT_ID + "=?";
        args = new String[1];
        args[0] = _id + "";

        // we check the existence of the entry inside the database
        cursor = mResolver.query(Filmography_item.CONTENT_URI,
                columns, // all the columns of the object
                where, // filmography_item_id=?
                args, // id of the object
                null);

        Out.d(TAG, "Adding " + cursor.getCount() + " childs into " + TAG);

        // if we get a result
        if (cursor.moveToFirst()) {

            do {
                // we create and then fill the item with the Cursor
                final Filmography_item itemFilmography_item = new Filmography_item();
                itemFilmography_item.fillObjectFromCursor(cursor);
                filmography_item.add(itemFilmography_item); // we add a new object to the list

            } while (cursor.moveToNext());
        }
        cursor.close();			
    }

	/**
     * This function build an array of {@link FilmographyBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link Filmography}> if you want it as {@link Filmography}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<FilmographyBase> result = new ArrayList<FilmographyBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final FilmographyBase newObject = new FilmographyBase();
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

	public static final Parcelable.Creator<FilmographyBase> CREATOR = new Parcelable.Creator<FilmographyBase>() {
	    @Override
	    public FilmographyBase createFromParcel(final Parcel in) {
	        return new FilmographyBase(in);
	    }
	
	    @Override
	    public FilmographyBase[] newArray(final int size) {
	        return new FilmographyBase[size];
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
		
		//list of content
		dest.writeString(error);
		
		//list of childs
		dest.writeParcelableArray(filmography_item.toArray(new Filmography_item[filmography_item.size()]), 0);
	}

	public FilmographyBase(final Parcel in) {
		// ResponseBusinessObject DAO
		_ttl = in.readLong();
		_url = in.readString();
		
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of content
		error = in.readString();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(Filmography_item.class.getClassLoader());
		filmography_item = new ArrayList<Filmography_item>();
		for (final Parcelable parcelable : items) {
		    filmography_item.add((Filmography_item) parcelable);
		}
		
	}    


}

