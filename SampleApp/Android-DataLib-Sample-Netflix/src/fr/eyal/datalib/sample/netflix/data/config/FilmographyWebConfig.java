package fr.eyal.datalib.sample.netflix.data.config;

import fr.eyal.lib.data.service.DataLibWebConfig;
import fr.eyal.lib.data.service.model.DataLibRequest;
// Start of user code FilmographyConfig imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

public class FilmographyWebConfig extends DataLibWebConfig {

    protected static FilmographyWebConfig sInstance; //Singleton of the WebConfig

    public static FilmographyWebConfig getInstance() {
        synchronized (FilmographyWebConfig.class) {
	        if (sInstance == null) {
	            sInstance = new FilmographyWebConfig();
	        }
		}
        return (FilmographyWebConfig) sInstance;
    }

    private FilmographyWebConfig() {}

    private int httpType = DataLibRequest.HTTP_REST_GET;
	private int requestOptions = DataLibRequest.OPTION_NO_OPTION;
    private int parseType = DataLibRequest.PARSE_TYPE_SAX_XML;

    /**
     * Get the HTTP type o the request
     * @return the corresponding
     */
    @Override
    public int getHttpType() {
        return httpType;
    }

    @Override
    public int getParseType() {
        return parseType;
    }

    @Override
    public int getRequestOptions() {
        return requestOptions;
    }

// Start of user code FilmographyConfig
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}

