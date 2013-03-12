package fr.eyal.datalib.sample.netflix.data.config;

import fr.eyal.lib.data.service.DataLibWebConfig;
import fr.eyal.lib.data.service.model.DataLibRequest;
// Start of user code CatalogTitlesConfig imports
import fr.eyal.lib.data.communication.rest.HttpRESTMethod;
// End of user code

public class CatalogTitlesWebConfig extends DataLibWebConfig {

    protected static CatalogTitlesWebConfig sInstance; //Singleton of the WebConfig

    public static CatalogTitlesWebConfig getInstance() {
        synchronized (CatalogTitlesWebConfig.class) {
	        if (sInstance == null) {
	            sInstance = new CatalogTitlesWebConfig();
	        }
		}
        return (CatalogTitlesWebConfig) sInstance;
    }

    private CatalogTitlesWebConfig() {}

    private int httpType = DataLibRequest.HTTP_REST_GET;
	private int requestOptions = 
			DataLibRequest.OPTION_DATABASE_CACHE_DISABLED;
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

// Start of user code CatalogTitlesConfig
    public String getHttpTypeName(){
    	switch (httpType) {
    	
		case DataLibRequest.HTTP_REST_GET:
			return HttpRESTMethod.GET.getMethodName();
		case DataLibRequest.HTTP_REST_DELETE:
			return HttpRESTMethod.DELETE.getMethodName();
		case DataLibRequest.HTTP_REST_HEAD:
			return HttpRESTMethod.HEAD.getMethodName();
		case DataLibRequest.HTTP_REST_POST:
			return HttpRESTMethod.POST.getMethodName();
		case DataLibRequest.HTTP_REST_PUT:
			return HttpRESTMethod.PUT.getMethodName();
		
		default:
			return null;
		}
    }
// End of user code

}

