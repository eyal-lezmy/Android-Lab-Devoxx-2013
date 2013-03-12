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
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;

public class Filmography_itemBase extends BusinessObjectDAO {

    protected long _parentId = ID_INVALID;

	//list of attributes
	public String attrTitleShort = "";
	public String attrTitleRegular = "";
	public String attrBox_artSmall = "";
	public String attrBox_artMedium = "";
	public String attrBox_artLarge = "";
	
	//list of content
	public String id;
	public String title;
	public String box_art;
	public int release_year;
	public int runtime;
	public float average_rating;
	
	
	//list of childs
	public ArrayList<FilmographyLink> filmographyLink;
	public ArrayList<FilmographyCategory> filmographyCategory;

    public Filmography_itemBase() {
        super();
    }

    public Filmography_itemBase(final long id) {
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
    public static final String CONTENT_PATH = "filmography_item";
    public static final String CONTENT_URL = NetflixProvider.PROVIDER_PREFIX + NetflixProvider.AUTHORITY + "/" + CONTENT_PATH;
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_URL);

    /**
     * SQL databases table's name
     */
    public static String DATABASE_TABLE_NAME = "filmography_item";

    /**
     * SQL database table's fields names
     */
    public static final String FIELD__ID = BusinessObjectDAO.FIELD_ID;
    public static final String FIELD__UPDATED_AT = BusinessObjectDAO.FIELD_UPDATED_AT;
    public static final String FIELD__PARENT_ID = "_parent_id";
	//list of attributes
    public static final String FIELD_ATTR_TITLESHORT = "ATTR_titleShort";
    public static final String FIELD_ATTR_TITLEREGULAR = "ATTR_titleRegular";
    public static final String FIELD_ATTR_BOX_ARTSMALL = "ATTR_box_artSmall";
    public static final String FIELD_ATTR_BOX_ARTMEDIUM = "ATTR_box_artMedium";
    public static final String FIELD_ATTR_BOX_ARTLARGE = "ATTR_box_artLarge";

	//list of content
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_BOX_ART = "box_art";
    public static final String FIELD_RELEASE_YEAR = "release_year";
    public static final String FIELD_RUNTIME = "runtime";
    public static final String FIELD_AVERAGE_RATING = "average_rating";

    /**
     * List of SQL database fields' names
     */
    public static String[] DATABASE_TABLE_FIELDS_NAMES = {
			// header
            FIELD__ID, // _id
            FIELD__UPDATED_AT, // updated_at
            FIELD__PARENT_ID, // sensors_id
            //list of attributes
		    FIELD_ATTR_TITLESHORT, //titleShort;
		    FIELD_ATTR_TITLEREGULAR, //titleRegular;
		    FIELD_ATTR_BOX_ARTSMALL, //box_artSmall;
		    FIELD_ATTR_BOX_ARTMEDIUM, //box_artMedium;
		    FIELD_ATTR_BOX_ARTLARGE, //box_artLarge;

		    //list of content
		    FIELD_ID, //id;
		    FIELD_TITLE, //title;
		    FIELD_BOX_ART, //box_art;
		    FIELD_RELEASE_YEAR, //release_year;
		    FIELD_RUNTIME, //runtime;
		    FIELD_AVERAGE_RATING, //average_rating;
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
			"text",
			"text",
		
			//list of content
			"text",
			"text",
			"text",
			"integer",
			"integer",
			"real",

    };

    /**
     * SQLite scripts to create the table
     */
    public static String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" 
			+ DATABASE_TABLE_FIELDS_NAMES[0] + " " + DATABASE_TABLE_FIELDS_TYPES[0] + " PRIMARY KEY AUTOINCREMENT" + ", "
            + DATABASE_TABLE_FIELDS_NAMES[1] + " " + DATABASE_TABLE_FIELDS_TYPES[1] + ", "
            + DATABASE_TABLE_FIELDS_NAMES[2] + " " + DATABASE_TABLE_FIELDS_TYPES[2] + " REFERENCES "
            + Filmography.DATABASE_TABLE_NAME + "(" + Filmography.DATABASE_TABLE_FIELDS_NAMES[0] + ") ON DELETE CASCADE" + ", "

			//list of attributes
			+ DATABASE_TABLE_FIELDS_NAMES[3] + " " + DATABASE_TABLE_FIELDS_TYPES[3] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[4] + " " + DATABASE_TABLE_FIELDS_TYPES[4] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[5] + " " + DATABASE_TABLE_FIELDS_TYPES[5] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[6] + " " + DATABASE_TABLE_FIELDS_TYPES[6] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[7] + " " + DATABASE_TABLE_FIELDS_TYPES[7] + ", "

			//list of content
			+ DATABASE_TABLE_FIELDS_NAMES[8] + " " + DATABASE_TABLE_FIELDS_TYPES[8] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[9] + " " + DATABASE_TABLE_FIELDS_TYPES[9] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[10] + " " + DATABASE_TABLE_FIELDS_TYPES[10] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[11] + " " + DATABASE_TABLE_FIELDS_TYPES[11] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[12] + " " + DATABASE_TABLE_FIELDS_TYPES[12] + ", "
			+ DATABASE_TABLE_FIELDS_NAMES[13] + " " + DATABASE_TABLE_FIELDS_TYPES[13] + "); "

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
	    values.put(FIELD_ATTR_TITLESHORT, attrTitleShort);
	    values.put(FIELD_ATTR_TITLEREGULAR, attrTitleRegular);
	    values.put(FIELD_ATTR_BOX_ARTSMALL, attrBox_artSmall);
	    values.put(FIELD_ATTR_BOX_ARTMEDIUM, attrBox_artMedium);
	    values.put(FIELD_ATTR_BOX_ARTLARGE, attrBox_artLarge);
	
	    //list of content
	    values.put(FIELD_ID, id);
	    values.put(FIELD_TITLE, title);
	    values.put(FIELD_BOX_ART, box_art);
	    values.put(FIELD_RELEASE_YEAR, release_year);
	    values.put(FIELD_RUNTIME, runtime);
	    values.put(FIELD_AVERAGE_RATING, average_rating);
		
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
        final Cursor cursor = mResolver.query(CONTENT_URI, // filmography_item
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
        	filmographyLink = new ArrayList<FilmographyLink>();
        	filmographyCategory = new ArrayList<FilmographyCategory>();

		// if we have a content
        if (!cursor.isClosed() && !cursor.isAfterLast()) {

            int i = 0;

            // header
            _id = cursor.getLong(i++);
            _updatedAt = Calendar.getInstance();
            _updatedAt.setTimeInMillis(cursor.getLong(i++) * 1000);
            _parentId = cursor.getLong(i++);

			//list of attributes
			attrTitleShort = cursor.getString(i++);
			attrTitleRegular = cursor.getString(i++);
			attrBox_artSmall = cursor.getString(i++);
			attrBox_artMedium = cursor.getString(i++);
			attrBox_artLarge = cursor.getString(i++);
		
			//list of content
			id = cursor.getString(i++);
			title = cursor.getString(i++);
			box_art = cursor.getString(i++);
			release_year = cursor.getInt(i++);
			runtime = cursor.getInt(i++);
			average_rating = cursor.getFloat(i++);

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
        for (final FilmographyLink filmographyLinkElement : filmographyLink) {
            index = filmographyLinkElement.updateId(ids, index, parentIndex);
        }

        for (final FilmographyCategory filmographyCategoryElement : filmographyCategory) {
            index = filmographyCategoryElement.updateId(ids, index, parentIndex);
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
        for (final FilmographyLink filmographyLinkElement : filmographyLink) {
            filmographyLinkElement.deleteChildsFromDatabase(batch);
        }
        // we first remove all the childs of the childs of the object
        for (final FilmographyCategory filmographyCategoryElement : filmographyCategory) {
            filmographyCategoryElement.deleteChildsFromDatabase(batch);
        }

		String whereClause = "";
		final String[] argsClause = { _id + "" };

        // we delete all the child entries
        whereClause = FilmographyLink.FIELD__PARENT_ID + "=?"; // filmography_item_id=?
        batch.add(ContentProviderOperation.newDelete(FilmographyLink.CONTENT_URI)
                .withSelection(whereClause, argsClause)
                .build());

        whereClause = FilmographyCategory.FIELD__PARENT_ID + "=?"; // filmography_item_id=?
        batch.add(ContentProviderOperation.newDelete(FilmographyCategory.CONTENT_URI)
                .withSelection(whereClause, argsClause)
                .build());

    }


    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch, final int previousResult) {
		for (final FilmographyLink filmographyLinkElement : filmographyLink) {
            filmographyLinkElement.addIntoDatabase(batch, FilmographyLink.CONTENT_URI, FilmographyLink.FIELD__PARENT_ID, previousResult);
        }
		for (final FilmographyCategory filmographyCategoryElement : filmographyCategory) {
            filmographyCategoryElement.addIntoDatabase(batch, FilmographyCategory.CONTENT_URI, FilmographyCategory.FIELD__PARENT_ID, previousResult);
        }
    }

    @Override
    public void addChildsIntoDatabase(final ArrayList<ContentProviderOperation> batch) {
		for (final FilmographyLink filmographyLinkElement : filmographyLink) {
            filmographyLinkElement._parentId = _id;
            filmographyLinkElement.addIntoDatabase(batch, FilmographyLink.CONTENT_URI);
        }
		for (final FilmographyCategory filmographyCategoryElement : filmographyCategory) {
            filmographyCategoryElement._parentId = _id;
            filmographyCategoryElement.addIntoDatabase(batch, FilmographyCategory.CONTENT_URI);
        }
    }


    @Override
    protected void fillChildrenFromDatabase() {
		String[] columns;
        String where;
        String[] args;
        Cursor cursor;

        // we define the access conditions to the objects
        columns = new String[FilmographyLink.DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < FilmographyLink.DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = FilmographyLink.DATABASE_TABLE_FIELDS_NAMES[i];
        }

        where = FilmographyLink.FIELD__PARENT_ID + "=?";
        args = new String[1];
        args[0] = _id + "";

        // we check the existence of the entry inside the database
        cursor = mResolver.query(FilmographyLink.CONTENT_URI,
                columns, // all the columns of the object
                where, // filmography_item_id=?
                args, // id of the object
                null);

        // if we get a result
        if (cursor.moveToFirst()) {

            do {
                // we create and then fill the item with the Cursor
                final FilmographyLink itemFilmographyLink = new FilmographyLink();
                itemFilmographyLink.fillObjectFromCursor(cursor);
                filmographyLink.add(itemFilmographyLink); // we add a new object to the list

            } while (cursor.moveToNext());
        }
        cursor.close();			
        // we define the access conditions to the objects
        columns = new String[FilmographyCategory.DATABASE_TABLE_FIELDS_NAMES.length];
        for (int i = 0; i < FilmographyCategory.DATABASE_TABLE_FIELDS_NAMES.length; i++) {
            columns[i] = FilmographyCategory.DATABASE_TABLE_FIELDS_NAMES[i];
        }

        where = FilmographyCategory.FIELD__PARENT_ID + "=?";
        args = new String[1];
        args[0] = _id + "";

        // we check the existence of the entry inside the database
        cursor = mResolver.query(FilmographyCategory.CONTENT_URI,
                columns, // all the columns of the object
                where, // filmography_item_id=?
                args, // id of the object
                null);

        // if we get a result
        if (cursor.moveToFirst()) {

            do {
                // we create and then fill the item with the Cursor
                final FilmographyCategory itemFilmographyCategory = new FilmographyCategory();
                itemFilmographyCategory.fillObjectFromCursor(cursor);
                filmographyCategory.add(itemFilmographyCategory); // we add a new object to the list

            } while (cursor.moveToNext());
        }
        cursor.close();			
    }

	/**
     * This function build an array of {@link Filmography_itemBase} thanks to a Cursor
     * object received from the database.
     * 
     * @param c The cursor object.
     * @param join Tells if the child of the objects have to be recursively
     *            filled from the database.
     * @return Returns an {@link ArrayList} of the result fill with the content
     *         of the Cursor. If the Cursor is empty, it returns an empty array.
     *  	   <b>The result of this function is return as ArrayList<?>. It has
     *  	   to be casted into the expected class to be useful.</b>
     *  	   Ex: Cast to ArrayList<{@link Filmography_item}> if you want it as {@link Filmography_item}
     */
    public static ArrayList<?> buildArrayFromCursor(final Cursor c, final boolean join) {

        final ArrayList<Filmography_itemBase> result = new ArrayList<Filmography_itemBase>();

        if (c.moveToFirst()) {
            do {
                // we create and fill the item
                final Filmography_itemBase newObject = new Filmography_itemBase();
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

	public static final Parcelable.Creator<Filmography_itemBase> CREATOR = new Parcelable.Creator<Filmography_itemBase>() {
	    @Override
	    public Filmography_itemBase createFromParcel(final Parcel in) {
	        return new Filmography_itemBase(in);
	    }
	
	    @Override
	    public Filmography_itemBase[] newArray(final int size) {
	        return new Filmography_itemBase[size];
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
		dest.writeString(attrTitleShort);
		dest.writeString(attrTitleRegular);
		dest.writeString(attrBox_artSmall);
		dest.writeString(attrBox_artMedium);
		dest.writeString(attrBox_artLarge);
		
		//list of content
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(box_art);
		dest.writeInt(release_year);
		dest.writeInt(runtime);
		dest.writeFloat(average_rating);
		
		//list of childs
		dest.writeParcelableArray(filmographyLink.toArray(new FilmographyLink[filmographyLink.size()]), 0);
		dest.writeParcelableArray(filmographyCategory.toArray(new FilmographyCategory[filmographyCategory.size()]), 0);
	}

	public Filmography_itemBase(final Parcel in) {
		// Business Object DAO
		_id = in.readLong();
		_updatedAt = Calendar.getInstance();
		_updatedAt.setTimeInMillis(in.readLong());
		
		//list of attributes
		attrTitleShort = in.readString();
		attrTitleRegular = in.readString();
		attrBox_artSmall = in.readString();
		attrBox_artMedium = in.readString();
		attrBox_artLarge = in.readString();	
		
		//list of content
		id = in.readString();
		title = in.readString();
		box_art = in.readString();
		release_year = in.readInt();
		runtime = in.readInt();
		average_rating = in.readFloat();	
		
		//list of children
		Parcelable[] items; 
		items = in.readParcelableArray(FilmographyLink.class.getClassLoader());
		filmographyLink = new ArrayList<FilmographyLink>();
		for (final Parcelable parcelable : items) {
		    filmographyLink.add((FilmographyLink) parcelable);
		}
		items = in.readParcelableArray(FilmographyCategory.class.getClassLoader());
		filmographyCategory = new ArrayList<FilmographyCategory>();
		for (final Parcelable parcelable : items) {
		    filmographyCategory.add((FilmographyCategory) parcelable);
		}
		
	}    
}

