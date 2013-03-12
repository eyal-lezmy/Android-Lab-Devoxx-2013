package fr.eyal.datalib.sample.netflix.data.service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import android.content.Context;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.service.ServiceHelper;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.lib.data.service.model.ComplexOptions;
// Start of user code NetflixServiceHelper imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class NetflixServiceHelper extends ServiceHelper {

	    public static final String URL_NEWRELEASES = "http://dvd.netflix.com/NewReleasesRSS";
	    public static final String URL_MOVIEIMAGE = "{0}";
	    public static final String URL_TOP100 = "http://dvd.netflix.com/Top100RSS";
	    public static final String URL_TOPGENRE = "http://dvd.netflix.com/Top25RSS";
	    public static final String URL_AUTOCOMPLETE = "http://api-public.netflix.com/catalog/titles/autocomplete";
	    public static final String URL_CATALOGTITLES = "http://api-public.netflix.com/catalog/titles";
	    public static final String URL_PEOPLE = "http://api-public.netflix.com/catalog/people/{0}";
	    public static final String URL_FILMOGRAPHY = "http://api-public.netflix.com/catalog/people/{0}/filmography";
	    public static final String URL_MOVIE = "http://api-public.netflix.com/catalog/titles/movies/{0}";
	    public static final String URL_SYNOPSIS = "http://api-public.netflix.com/catalog/titles/movies/{0}/synopsis";
	    public static final String URL_CAST = "http://api-public.netflix.com/catalog/titles/movies/{0}/cast";
	    public static final String URL_DIRECTORS = "http://api-public.netflix.com/catalog/titles/movies/{0}/directors";
	

    protected NetflixServiceHelper(final Context context) {
        super(context);
    }

    /**
     * Get the instance of the {@link NetflixServiceHelper}
     * 
     * @param context The context of execution. Any Context can be put here, the application context
     * will be automatically used for the {@link NetflixServiceHelper}
     * 
     * @return Returns the singleton
     */
    public static NetflixServiceHelper getInstance(final Context context) {
        synchronized (NetflixServiceHelper.class) {
			if (sInstance == null) {
            	sInstance = new NetflixServiceHelper(context.getApplicationContext());
        	}
		}
        return (NetflixServiceHelper) sInstance;
    }

    /**
     * Get the instance of the {@link NetflixServiceHelper}
     *  
     * @return Returns the singleton <b>only if</b> the instance have already been create by the call to 
     * <code>{@linkplain NetflixServiceHelper#getInstance(Context)}</code>. If it has not been called, this 
     * function returns <b>null</b>
     */
    public static NetflixServiceHelper getInstance() {
		if (sInstance == null) {
        	return null;
    	}
        return (NetflixServiceHelper) sInstance;
    }




    /**
     * Start {@link NewReleases} request
     * 
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getNewReleases( final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_NEWRELEASES;
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_NEWRELEASES, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link MovieImage} request
     * 
     * @param url Image's URL. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getMovieImage(final String url,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_MOVIEIMAGE, url);
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_MOVIEIMAGE, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Top100} request
     * 
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getTop100( final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_TOP100;
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_TOP100, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link TopGenre} request
     * 
     * @param gid The Genre's ID you are looking for. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getTopGenre(final int gid,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("gid", String.valueOf(gid));
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_TOPGENRE;
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_TOPGENRE, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Autocomplete} request
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param term The term of the content you are looking for. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getAutocomplete(final String oauth_consumer_key, final String term,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("term", term);
		
		//we prepare the request's url
		final String __url =  NetflixServiceHelper.URL_AUTOCOMPLETE;
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_AUTOCOMPLETE, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link CatalogTitles} request
     * 
     * @param max_results Maximum number of results expected. If this parameter is not present, the default value is 25.. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param start_index Start index of the result (to paginate the results). This value is linked to max_result. To disable the adding of it, just define it as null
     * @param term The term of the content you are looking for. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getCatalogTitles(final int max_results, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final int start_index, final String term, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
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
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_CATALOGTITLES, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link People} request
     * 
     * @param person_id Person's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getPeople(final int person_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_PEOPLE, person_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_PEOPLE, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Filmography} request
     * 
     * @param person_id Person's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getFilmography(final int person_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_FILMOGRAPHY, person_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_FILMOGRAPHY, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Movie} request
     * 
     * @param movie_id Movie's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getMovie(final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_MOVIE, movie_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_MOVIE, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Synopsis} request
     * 
     * @param movie_id Movie's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getSynopsis(final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_SYNOPSIS, movie_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_SYNOPSIS, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Cast} request
     * 
     * @param movie_id Movie's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getCast(final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_CAST, movie_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_CAST, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

    /**
     * Start {@link Directors} request
     * 
     * @param movie_id Movie's ID. To disable the adding of it, just define it as null
     * @param oauth_consumer_key The OAuth consumer key of the developer. To disable the adding of it, just define it as null
     * @param oauth_nonce A random string. To disable the adding of it, just define it as null
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1. To disable the adding of it, just define it as null
     * @param oauth_timestamp The current timestamp. To disable the adding of it, just define it as null
     * @param oauth_signature The request's signature computed thanks to the specific OAuth process. To disable the adding of it, just define it as null
     * 
     * @param complexOptionsNetwork a {@link ComplexOptions} you want to use when processing the network request. 
     * Set this parameter to <code>null</code> if you prefer to ignore this value.
     *
     * @return the request's id. If the request have already been sent, it returns the id previously generated. Otherwise it provide a new one that have to be stored.
     * 
     * @throws UnsupportedEncodingException
     */
	public int getDirectors(final int movie_id, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String oauth_signature,  final int options, ComplexOptions complexOptionsNetwork) throws UnsupportedEncodingException {
		
		//we prepare the parameters
		final ParameterMap params = new ParameterMap();
		params.put("oauth_consumer_key", oauth_consumer_key);
		params.put("oauth_nonce", oauth_nonce);
		params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
		params.put("oauth_signature", oauth_signature);
		
		//we prepare the request's url
		final String __url = MessageFormat.format(NetflixServiceHelper.URL_DIRECTORS, movie_id+"");
		
        
        return launchRequest(options, NetflixService.WEBSERVICE_DIRECTORS, params, NetflixService.class, __url, complexOptionsNetwork);
    }
    

// Start of user code NetflixServiceHelper
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


}

