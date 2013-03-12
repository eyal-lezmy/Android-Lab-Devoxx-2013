package fr.eyal.datalib.sample.netflix.data.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.text.MessageFormat;

import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.data.model.ResponseBusinessObjectDAO;
import fr.eyal.lib.data.service.DataManager;
import fr.eyal.lib.data.service.ServiceHelper;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.data.service.model.ComplexOptions;
import fr.eyal.datalib.sample.netflix.data.model.newreleases.*;
import fr.eyal.datalib.sample.netflix.data.model.movieimage.*;
import fr.eyal.datalib.sample.netflix.data.model.top100.*;
import fr.eyal.datalib.sample.netflix.data.model.topgenre.*;
import fr.eyal.datalib.sample.netflix.data.model.autocomplete.*;
import fr.eyal.datalib.sample.netflix.data.model.catalogtitles.*;
import fr.eyal.datalib.sample.netflix.data.model.people.*;
import fr.eyal.datalib.sample.netflix.data.model.filmography.*;
import fr.eyal.datalib.sample.netflix.data.model.movie.*;
import fr.eyal.datalib.sample.netflix.data.model.synopsis.*;
import fr.eyal.datalib.sample.netflix.data.model.cast.*;
import fr.eyal.datalib.sample.netflix.data.model.directors.*;
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;
// Start of user code NetflixDataManager imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

public class NetflixDataManager extends DataManager {

    @SuppressWarnings("unused")
    private static final String TAG = NetflixDataManager.class.getSimpleName();

    /**
     * Get the instance of the {@link NetflixDataManager}
     * 
     * @param context The context of execution. Any Context can be put here, the application context
     * will be automatically used for the {@link NetflixDataManager}
     * 
     * @return Returns the singleton
     */
    public static NetflixDataManager getInstance(final Context context) {
        synchronized (NetflixDataManager.class) {
			if (sInstance == null) {
            	sInstance = new NetflixDataManager(context.getApplicationContext());
        	}
		}
        return (NetflixDataManager) sInstance;
    }

    /**
     * Get the instance of the {@link NetflixDataManager}
     *  
     * @return Returns the singleton <b>only if</b> the instance have already been create by the call to 
     * <code>{@linkplain NetflixDataManager#getInstance(Context)}</code>. If it has not been called, this 
     * function returns <b>null</b>
     */
    public static NetflixDataManager getInstance() {
		if (sInstance == null) {
        	return null;
    	}
        return (NetflixDataManager) sInstance;
    }


    protected NetflixDataManager(final Context context) {
        super(context);

        mServiceHelper = NetflixServiceHelper.getInstance(context);
        mServiceHelper.addOnRequestFinishedRelayer(this);
    }

    @Override
    public ResponseBusinessObject getBusinessObjectFromCacheByUrl(final int type, final String url, ComplexOptions complexOptions) {

        switch (type) {
            case NetflixService.WEBSERVICE_NEWRELEASES:
            		return new NewReleases(url);
            case NetflixService.WEBSERVICE_MOVIEIMAGE:
					return new MovieImage(url, complexOptions);
            case NetflixService.WEBSERVICE_TOP100:
            		return new Top100(url);
            case NetflixService.WEBSERVICE_TOPGENRE:
            		return new TopGenre(url);
            case NetflixService.WEBSERVICE_PEOPLE:
            		return new People(url);
            case NetflixService.WEBSERVICE_FILMOGRAPHY:
            		return new Filmography(url);
            case NetflixService.WEBSERVICE_MOVIE:
            		return new Movie(url);
            case NetflixService.WEBSERVICE_SYNOPSIS:
            		return new Synopsis(url);
            case NetflixService.WEBSERVICE_CAST:
            		return new Cast(url);
            case NetflixService.WEBSERVICE_DIRECTORS:
            		return new Directors(url);
            default:
                return null;
        }
    }


    @Override
    protected ResponseBusinessObjectDAO getResponseBusinessObjectById(final int webserviceType, final long id) {

        switch (webserviceType) {
            case NetflixService.WEBSERVICE_NEWRELEASES:
            	return new NewReleases(id);
            case NetflixService.WEBSERVICE_TOP100:
            	return new Top100(id);
            case NetflixService.WEBSERVICE_TOPGENRE:
            	return new TopGenre(id);
            case NetflixService.WEBSERVICE_PEOPLE:
            	return new People(id);
            case NetflixService.WEBSERVICE_FILMOGRAPHY:
            	return new Filmography(id);
            case NetflixService.WEBSERVICE_MOVIE:
            	return new Movie(id);
            case NetflixService.WEBSERVICE_SYNOPSIS:
            	return new Synopsis(id);
            case NetflixService.WEBSERVICE_CAST:
            	return new Cast(id);
            case NetflixService.WEBSERVICE_DIRECTORS:
            	return new Directors(id);

            default:
                return null;
        }
    }


    @Override
    protected ArrayList<?> getBusinessObjectsFromDatabase(final int code, final String where, final String[] whereArgs, final String order, final boolean join) {

        switch (code) {
			case NetflixProvider.CODE_NEWRELEASES:
				return NetflixDataManager.this.retrieveDataNewReleases(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_NEWRELEASES_ITEMNEWRELEASE:
				return NetflixDataManager.this.retrieveDataItemNewRelease(where, whereArgs, order);
			case NetflixProvider.CODE_TOP100:
				return NetflixDataManager.this.retrieveDataTop100(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_TOP100_ITEMTOP100:
				return NetflixDataManager.this.retrieveDataItemTop100(where, whereArgs, order);
			case NetflixProvider.CODE_TOPGENRE:
				return NetflixDataManager.this.retrieveDataTopGenre(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_TOPGENRE_ITEMTOPGENRE:
				return NetflixDataManager.this.retrieveDataItemTopGenre(where, whereArgs, order);
			case NetflixProvider.CODE_PEOPLE:
				return NetflixDataManager.this.retrieveDataPeople(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_PEOPLE_PEOPLELINK:
				return NetflixDataManager.this.retrieveDataPeopleLink(where, whereArgs, order);
			case NetflixProvider.CODE_FILMOGRAPHY:
				return NetflixDataManager.this.retrieveDataFilmography(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM:
				return NetflixDataManager.this.retrieveDataFilmography_item(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM_FILMOGRAPHYLINK:
				return NetflixDataManager.this.retrieveDataFilmographyLink(where, whereArgs, order);
			case NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM_FILMOGRAPHYCATEGORY:
				return NetflixDataManager.this.retrieveDataFilmographyCategory(where, whereArgs, order);
			case NetflixProvider.CODE_MOVIE:
				return NetflixDataManager.this.retrieveDataMovie(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_MOVIE_MOVIELINK:
				return NetflixDataManager.this.retrieveDataMovieLink(where, whereArgs, order);
			case NetflixProvider.CODE_MOVIE_MOVIECATEGORY:
				return NetflixDataManager.this.retrieveDataMovieCategory(where, whereArgs, order);
			case NetflixProvider.CODE_SYNOPSIS:
				return NetflixDataManager.this.retrieveDataSynopsis(where, whereArgs, order);
			case NetflixProvider.CODE_CAST:
				return NetflixDataManager.this.retrieveDataCast(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_CAST_CASTPERSON:
				return NetflixDataManager.this.retrieveDataCastPerson(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_CAST_CASTPERSON_CASTLINK:
				return NetflixDataManager.this.retrieveDataCastLink(where, whereArgs, order);
			case NetflixProvider.CODE_DIRECTORS:
				return NetflixDataManager.this.retrieveDataDirectors(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_DIRECTORS_DIRECTOR:
				return NetflixDataManager.this.retrieveDataDirector(where, whereArgs, order, join);
			
			case NetflixProvider.CODE_DIRECTORS_DIRECTOR_DIRECTORLINK:
				return NetflixDataManager.this.retrieveDataDirectorLink(where, whereArgs, order);

            default:
                return null;
        }
    }



	/**
	 * Get {@link NewReleases} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link NewReleases} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link NewReleases} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<NewReleases> retrieveDataNewReleases(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(NewReleases.CONTENT_URI,
				NewReleases.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<NewReleases>) NewReleases.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link NewReleases} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link NewReleases} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataNewReleasesAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_NEWRELEASES,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link ItemNewRelease} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link ItemNewRelease} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<ItemNewRelease> retrieveDataItemNewRelease(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(ItemNewRelease.CONTENT_URI,
				ItemNewRelease.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<ItemNewRelease>) ItemNewRelease.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link ItemNewRelease} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataItemNewReleaseAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_NEWRELEASES_ITEMNEWRELEASE,
				where, whereArgs, order, false, listener);
	}	




	/**
	 * Get {@link Top100} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Top100} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Top100} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Top100> retrieveDataTop100(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Top100.CONTENT_URI,
				Top100.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Top100>) Top100.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Top100} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Top100} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataTop100Async(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_TOP100,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link ItemTop100} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link ItemTop100} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<ItemTop100> retrieveDataItemTop100(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(ItemTop100.CONTENT_URI,
				ItemTop100.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<ItemTop100>) ItemTop100.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link ItemTop100} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataItemTop100Async(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_TOP100_ITEMTOP100,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link TopGenre} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link TopGenre} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link TopGenre} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<TopGenre> retrieveDataTopGenre(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(TopGenre.CONTENT_URI,
				TopGenre.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<TopGenre>) TopGenre.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link TopGenre} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link TopGenre} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataTopGenreAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_TOPGENRE,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link ItemTopGenre} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link ItemTopGenre} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<ItemTopGenre> retrieveDataItemTopGenre(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(ItemTopGenre.CONTENT_URI,
				ItemTopGenre.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<ItemTopGenre>) ItemTopGenre.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link ItemTopGenre} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataItemTopGenreAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_TOPGENRE_ITEMTOPGENRE,
				where, whereArgs, order, false, listener);
	}	





	/**
	 * Get {@link People} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link People} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link People} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<People> retrieveDataPeople(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(People.CONTENT_URI,
				People.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<People>) People.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link People} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link People} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataPeopleAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_PEOPLE,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link PeopleLink} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link PeopleLink} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<PeopleLink> retrieveDataPeopleLink(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(PeopleLink.CONTENT_URI,
				PeopleLink.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<PeopleLink>) PeopleLink.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link PeopleLink} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataPeopleLinkAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_PEOPLE_PEOPLELINK,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link Filmography} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Filmography} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Filmography} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Filmography> retrieveDataFilmography(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Filmography.CONTENT_URI,
				Filmography.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Filmography>) Filmography.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Filmography} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Filmography} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataFilmographyAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_FILMOGRAPHY,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link Filmography_item} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Filmography_item} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Filmography_item} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Filmography_item> retrieveDataFilmography_item(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Filmography_item.CONTENT_URI,
				Filmography_item.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Filmography_item>) Filmography_item.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Filmography_item} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Filmography_item} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataFilmography_itemAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link FilmographyLink} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link FilmographyLink} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<FilmographyLink> retrieveDataFilmographyLink(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(FilmographyLink.CONTENT_URI,
				FilmographyLink.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<FilmographyLink>) FilmographyLink.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link FilmographyLink} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataFilmographyLinkAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM_FILMOGRAPHYLINK,
				where, whereArgs, order, false, listener);
	}
	/**
	 * Get {@link FilmographyCategory} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link FilmographyCategory} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<FilmographyCategory> retrieveDataFilmographyCategory(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(FilmographyCategory.CONTENT_URI,
				FilmographyCategory.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<FilmographyCategory>) FilmographyCategory.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link FilmographyCategory} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataFilmographyCategoryAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_FILMOGRAPHY_FILMOGRAPHY_ITEM_FILMOGRAPHYCATEGORY,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link Movie} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Movie} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Movie} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Movie> retrieveDataMovie(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Movie.CONTENT_URI,
				Movie.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Movie>) Movie.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Movie} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Movie} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataMovieAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_MOVIE,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link MovieLink} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link MovieLink} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<MovieLink> retrieveDataMovieLink(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(MovieLink.CONTENT_URI,
				MovieLink.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<MovieLink>) MovieLink.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link MovieLink} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataMovieLinkAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_MOVIE_MOVIELINK,
				where, whereArgs, order, false, listener);
	}
	/**
	 * Get {@link MovieCategory} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link MovieCategory} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<MovieCategory> retrieveDataMovieCategory(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(MovieCategory.CONTENT_URI,
				MovieCategory.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<MovieCategory>) MovieCategory.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link MovieCategory} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataMovieCategoryAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_MOVIE_MOVIECATEGORY,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link Synopsis} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Synopsis} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Synopsis> retrieveDataSynopsis(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(Synopsis.CONTENT_URI,
				Synopsis.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Synopsis>) Synopsis.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link Synopsis} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataSynopsisAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_SYNOPSIS,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link Cast} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Cast} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Cast} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Cast> retrieveDataCast(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Cast.CONTENT_URI,
				Cast.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Cast>) Cast.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Cast} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Cast} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataCastAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_CAST,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link CastPerson} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link CastPerson} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link CastPerson} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<CastPerson> retrieveDataCastPerson(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(CastPerson.CONTENT_URI,
				CastPerson.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<CastPerson>) CastPerson.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link CastPerson} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link CastPerson} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataCastPersonAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_CAST_CASTPERSON,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link CastLink} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link CastLink} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<CastLink> retrieveDataCastLink(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(CastLink.CONTENT_URI,
				CastLink.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<CastLink>) CastLink.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link CastLink} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataCastLinkAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_CAST_CASTPERSON_CASTLINK,
				where, whereArgs, order, false, listener);
	}	



	/**
	 * Get {@link Directors} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Directors} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Directors} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Directors> retrieveDataDirectors(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Directors.CONTENT_URI,
				Directors.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Directors>) Directors.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Directors} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Directors} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataDirectorsAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_DIRECTORS,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link Director} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Director} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link Director} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Director> retrieveDataDirector(final String where, final String[] whereArgs, final String order, final boolean join) {
		final Cursor c = this.mContentResolver.query(Director.CONTENT_URI,
				Director.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<Director>) Director.buildArrayFromCursor(c, join);
	
	}
	
	/**
	 * Get {@link Director} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * @param join Tells if the {@link Director} objects returned have to get his children's
	 * arrays filled thanks to the database.
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataDirectorAsync(final String where, final String[] whereArgs, final String order, final boolean join, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_DIRECTORS_DIRECTOR,
				where, whereArgs, order, join, listener);
	}
	
	/**
	 * Get {@link DirectorLink} objects from the database thanks to standard SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * 
	 * @return An {@link ArrayList} of the {@link DirectorLink} objects fetch in the database,
	 * corresponding to the sent request.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<DirectorLink> retrieveDataDirectorLink(final String where, final String[] whereArgs, final String order) {
		final Cursor c = this.mContentResolver.query(DirectorLink.CONTENT_URI,
				DirectorLink.DATABASE_TABLE_FIELDS_NAMES,
				where,
				whereArgs,
				order);
	
		return (ArrayList<DirectorLink>) DirectorLink.buildArrayFromCursor(c, false);
	
	}
	
	/**
	 * Get {@link DirectorLink} objects from the database asynchronously thanks to standard
	 * SQL selectors.
	 * 
	 * @param where A filter declaring which rows to return, formatted as an SQL WHERE
	 * clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
	 * 
	 * @param whereArgs You may include ?s in selection, which will be replaced by the values
	 * from selectionArgs, in the order that they appear in the selection. The values will be
	 * bound as Strings.
	 * 
	 * @param order How to order the rows, formatted as an SQL ORDER BY clause (excluding the
	 * ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * 
	 * 
	 * @param listener The {@link OnDataListener} that will receive the {@link ArrayList} of fetched
	 * objects.
	 * 
	 * @return The request id generated by the {@link DataManager}.
	 * 
	 * @see cancelRequest() To cancel this request before the response come.
	 */
	public synchronized int retrieveDataDirectorLinkAsync(final String where, final String[] whereArgs, final String order, final OnDataListener listener){
		return startDatabaseAsyncAccess(NetflixProvider.CODE_DIRECTORS_DIRECTOR_DIRECTORLINK,
				where, whereArgs, order, false, listener);
	}	




    /**
     * Retrieve the {@link NewReleases}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getNewReleases(final int policy, final OnDataListener datacacheListener,  final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_NEWRELEASES;
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_NEWRELEASES, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link MovieImage}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param url Image's URL
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getMovieImage(final int policy, final OnDataListener datacacheListener, final String url, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_MOVIEIMAGE, url);
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_MOVIEIMAGE, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Top100}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getTop100(final int policy, final OnDataListener datacacheListener,  final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_TOP100;
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_TOP100, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link TopGenre}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param gid The Genre's ID you are looking for
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getTopGenre(final int policy, final OnDataListener datacacheListener, final int gid, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("gid", String.valueOf(gid));
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_TOPGENRE;
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_TOPGENRE, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



	/**
     * Retrieve the {@link Autocomplete}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter is optional butif you want to get the response back you have to subscribe to the
     * request's response by using the function {@link DataManager#addOnDataListener(int requestId, OnDataListener listener)}.
     * Since there is no listener for the request, the response is supposed to be stored in a cache. It is then removed once delivered.
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param term The term of the content you are looking for
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED).
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getAutocomplete(final OnDataListener datacacheListener, final String oauth_consumer_key, final String term, final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("term", term);
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_AUTOCOMPLETE;
		

		int requestId = mServiceHelper.launchRequest(options, NetflixService.WEBSERVICE_AUTOCOMPLETE, params, NetflixService.class, __url, complexOptionsNetwork);
		
		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }


	/**
     * Retrieve the {@link CatalogTitles}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter is optional butif you want to get the response back you have to subscribe to the
     * request's response by using the function {@link DataManager#addOnDataListener(int requestId, OnDataListener listener)}.
     * Since there is no listener for the request, the response is supposed to be stored in a cache. It is then removed once delivered.
     * 
     * @param max_results Maximum number of results expected. If this parameter is not present, the default value is 25.
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param start_index Start index of the result (to paginate the results). This value is linked to max_result
     * 
     * @param term The term of the content you are looking for
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED).
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getCatalogTitles(final OnDataListener datacacheListener, final int max_results, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final int start_index, final String term, final String oauth_signature, final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("max_results", String.valueOf(max_results));
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("start_index", String.valueOf(start_index));
		params.put("term", term);
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_CATALOGTITLES;
		

		int requestId = mServiceHelper.launchRequest(options, NetflixService.WEBSERVICE_CATALOGTITLES, params, NetflixService.class, __url, complexOptionsNetwork);
		
		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }


    /**
     * Retrieve the {@link People}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param person_id Person's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getPeople(final int policy, final OnDataListener datacacheListener, final int person_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_PEOPLE, person_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_PEOPLE, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Filmography}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param person_id Person's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getFilmography(final int policy, final OnDataListener datacacheListener, final int person_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_FILMOGRAPHY, person_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_FILMOGRAPHY, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Movie}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param movie_id Movie's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getMovie(final int policy, final OnDataListener datacacheListener, final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_MOVIE, movie_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_MOVIE, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Synopsis}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param movie_id Movie's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getSynopsis(final int policy, final OnDataListener datacacheListener, final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_SYNOPSIS, movie_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_SYNOPSIS, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Cast}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param movie_id Movie's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getCast(final int policy, final OnDataListener datacacheListener, final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_CAST, movie_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_CAST, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



    /**
     * Retrieve the {@link Directors}
     * 
     * @param policy Give the policy context of the request using CACHE and/or NETWORK. Accepted values are : 
     * {@link DataManager#TYPE_NETWORK}, {@link DataManager#TYPE_CACHE}, {@link DataManager#TYPE_CACHE_THEN_NETWORK}
     * and {@link DataManager#TYPE_NETWORK_OTHERWISE_CACHE}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter IS NEEDED in case of Datacache access (<code>TYPE_CACHE</code>, <code>TYPE_CACHE_THEN_NETWORK</code>
     * and <code>TYPE_NETWORK_OTHERWISE_CACHE</code>). This listener won't be used to send DataLib's response.
     * So, the addOnRequestFinishedListener call is still needed.
     * 
     * @param movie_id Movie's ID
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: <code>OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED</code>).
     * 
     * @param complexOptionsCache a {@link ComplexOptions} you want to use when processing the cache request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     * 
     * @return Returns the request id generated by the DataLib.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getDirectors(final int policy, final OnDataListener datacacheListener, final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature, final int options, ComplexOptions complexOptionsCache, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_DIRECTORS, movie_id+"");
		
        int requestId = launchRequest(mServiceHelper, policy, datacacheListener, params, options, __url, NetflixService.WEBSERVICE_DIRECTORS, NetflixService.class, complexOptionsCache, complexOptionsNetwork);

		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }



// Start of user code NetflixDataManager
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}
