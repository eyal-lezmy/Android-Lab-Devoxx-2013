package fr.eyal.datalib.sample.netflix.data.service;

import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.lib.data.processor.Processor;
import fr.eyal.lib.data.service.DataLibService;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.datalib.sample.netflix.data.config.NewReleasesWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.MovieImageWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.Top100WebConfig;
import fr.eyal.datalib.sample.netflix.data.config.TopGenreWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.AutocompleteWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.CatalogTitlesWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.PeopleWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.FilmographyWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.MovieWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.SynopsisWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.CastWebConfig;
import fr.eyal.datalib.sample.netflix.data.config.DirectorsWebConfig;
import fr.eyal.datalib.sample.netflix.data.parser.NewReleasesParser;
import fr.eyal.datalib.sample.netflix.data.parser.MovieImageParser;
import fr.eyal.datalib.sample.netflix.data.parser.Top100Parser;
import fr.eyal.datalib.sample.netflix.data.parser.TopGenreParser;
import fr.eyal.datalib.sample.netflix.data.parser.AutocompleteParser;
import fr.eyal.datalib.sample.netflix.data.parser.CatalogTitlesParser;
import fr.eyal.datalib.sample.netflix.data.parser.PeopleParser;
import fr.eyal.datalib.sample.netflix.data.parser.FilmographyParser;
import fr.eyal.datalib.sample.netflix.data.parser.MovieParser;
import fr.eyal.datalib.sample.netflix.data.parser.SynopsisParser;
import fr.eyal.datalib.sample.netflix.data.parser.CastParser;
import fr.eyal.datalib.sample.netflix.data.parser.DirectorsParser;
// Start of user code NetflixService imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

public class NetflixService extends DataLibService {

    //different Service's possible actions
	public static final int WEBSERVICE_NEWRELEASES = 1; 
	public static final int WEBSERVICE_MOVIEIMAGE = 2; 
	public static final int WEBSERVICE_TOP100 = 3; 
	public static final int WEBSERVICE_TOPGENRE = 4; 
	public static final int WEBSERVICE_AUTOCOMPLETE = 5; 
	public static final int WEBSERVICE_CATALOGTITLES = 6; 
	public static final int WEBSERVICE_PEOPLE = 7; 
	public static final int WEBSERVICE_FILMOGRAPHY = 8; 
	public static final int WEBSERVICE_MOVIE = 9; 
	public static final int WEBSERVICE_SYNOPSIS = 10; 
	public static final int WEBSERVICE_CAST = 11; 
	public static final int WEBSERVICE_DIRECTORS = 12;

    public NetflixService() {
        super();
    }

    @Override
    public void launchProcessor(final int processorType, final DataLibRequest request) {

        Processor processor = new Processor(this, mHeaders);
        GenericHandler handler = null;

        //We initiate the specific workers
        switch (processorType) {

			case WEBSERVICE_NEWRELEASES:
			    NewReleasesWebConfig.applyToRequest(request, NewReleasesWebConfig.getInstance());
				handler = new NewReleasesParser();
				break;
			case WEBSERVICE_MOVIEIMAGE:
			    MovieImageWebConfig.applyToRequest(request, MovieImageWebConfig.getInstance());
			    handler = new MovieImageParser(request.complexOptions);
				break;
			case WEBSERVICE_TOP100:
			    Top100WebConfig.applyToRequest(request, Top100WebConfig.getInstance());
				handler = new Top100Parser();
				break;
			case WEBSERVICE_TOPGENRE:
			    TopGenreWebConfig.applyToRequest(request, TopGenreWebConfig.getInstance());
				handler = new TopGenreParser();
				break;
			case WEBSERVICE_AUTOCOMPLETE:
			    AutocompleteWebConfig.applyToRequest(request, AutocompleteWebConfig.getInstance());
				handler = new AutocompleteParser();
				break;
			case WEBSERVICE_CATALOGTITLES:
			    CatalogTitlesWebConfig.applyToRequest(request, CatalogTitlesWebConfig.getInstance());
				handler = new CatalogTitlesParser();
				break;
			case WEBSERVICE_PEOPLE:
			    PeopleWebConfig.applyToRequest(request, PeopleWebConfig.getInstance());
				handler = new PeopleParser();
				break;
			case WEBSERVICE_FILMOGRAPHY:
			    FilmographyWebConfig.applyToRequest(request, FilmographyWebConfig.getInstance());
				handler = new FilmographyParser();
				break;
			case WEBSERVICE_MOVIE:
			    MovieWebConfig.applyToRequest(request, MovieWebConfig.getInstance());
				handler = new MovieParser();
				break;
			case WEBSERVICE_SYNOPSIS:
			    SynopsisWebConfig.applyToRequest(request, SynopsisWebConfig.getInstance());
				handler = new SynopsisParser();
				break;
			case WEBSERVICE_CAST:
			    CastWebConfig.applyToRequest(request, CastWebConfig.getInstance());
				handler = new CastParser();
				break;
			case WEBSERVICE_DIRECTORS:
			    DirectorsWebConfig.applyToRequest(request, DirectorsWebConfig.getInstance());
				handler = new DirectorsParser();
				break;
			default:
            	return;
        }

		//we check the presence of the ACCESS_NETWORK_STATE
        PackageManager pm = getPackageManager();
        if (pm.checkPermission("android.permission.ACCESS_NETWORK_STATE", getPackageName()) == PackageManager.PERMISSION_GRANTED) {

            NetworkInfo infos = mConnectivityManager.getActiveNetworkInfo();
            boolean isConnected = (infos != null && infos.isConnected());
            processor.start(request, isConnected, handler, this);

        } else {
        	throw new SecurityException("android.permission.ACCESS_NETWORK_STATE needed. Add it to te manifest to be able to use this function");
        }
    }

// Start of user code NetflixService
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


}

