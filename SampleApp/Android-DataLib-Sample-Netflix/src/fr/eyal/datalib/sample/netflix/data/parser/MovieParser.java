package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.movie.*;
// Start of user code MovieParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class MovieParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = MovieParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Movie
	private static final int H1 = 76;
	private static final int CATALOG_TITLE = 77;
	private static final int CATALOG_TITLE_ID = 78;
	private static final int CATALOG_TITLE_TITLE = 79;
	private static final int CATALOG_TITLE_BOX_ART = 80;
	private static final int CATALOG_TITLE_LINK = 81;
	private static final int CATALOG_TITLE_RELEASE_YEAR = 82;
	private static final int CATALOG_TITLE_CATEGORY = 83;
	private static final int CATALOG_TITLE_RUNTIME = 84;
	private static final int CATALOG_TITLE_AVERAGE_RATING = 85;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Movie movie;
	private MovieLink movieLink;
	private MovieCategory movieCategory;

    public MovieParser() {
	movie = new Movie();
	movie.movieLink = new ArrayList<MovieLink>();
	movie.movieCategory = new ArrayList<MovieCategory>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return movie;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("catalog_title")) {
					mState = CATALOG_TITLE;
					
			    }
			    break;
			
			
			case CATALOG_TITLE:
			
			    if (qName.equals("id")) {
					mState = CATALOG_TITLE_ID;
					
			    }
				else if (qName.equals("title")) {
					mState = CATALOG_TITLE_TITLE;
					movie.attrTitleShort = attributes.getValue("short");
					movie.attrTitleRegular = attributes.getValue("regular");
			    }
				else if (qName.equals("box_art")) {
					mState = CATALOG_TITLE_BOX_ART;
					movie.attrBox_artSmall = attributes.getValue("small");
					movie.attrBox_artMedium = attributes.getValue("medium");
					movie.attrBox_artLarge = attributes.getValue("large");
			    }
				else if (qName.equals("link")) {
			        mState = CATALOG_TITLE_LINK;
			        movieLink = new MovieLink();
					movieLink.attrHref = attributes.getValue("href");
					movieLink.attrRel = attributes.getValue("rel");
					movieLink.attrTitle = attributes.getValue("title");
			    }
				else if (qName.equals("release_year")) {
					mState = CATALOG_TITLE_RELEASE_YEAR;
					
			    }
				else if (qName.equals("category")) {
			        mState = CATALOG_TITLE_CATEGORY;
			        movieCategory = new MovieCategory();
					movieCategory.attrScheme = attributes.getValue("scheme");
					movieCategory.attrLabel = attributes.getValue("label");
					movieCategory.attrTerm = attributes.getValue("term");
			    }
				else if (qName.equals("runtime")) {
					mState = CATALOG_TITLE_RUNTIME;
					
			    }
				else if (qName.equals("average_rating")) {
					mState = CATALOG_TITLE_AVERAGE_RATING;
					
			    }
			    break;

            default:
                // do nothing
                break;
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        mBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {

        switch (mState) {
			
			case H1:
			    if (qName.equals("h1")) {
			        mState = UNKNOWN;
					movie.error = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLE:
			    if (qName.equals("catalog_title")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case CATALOG_TITLE_ID:
			    if (qName.equals("id")) {
			        mState = CATALOG_TITLE;
					movie.id = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLE_TITLE:
			    if (qName.equals("title")) {
			        mState = CATALOG_TITLE;
					movie.title = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLE_BOX_ART:
			    if (qName.equals("box_art")) {
			        mState = CATALOG_TITLE;
					movie.box_art = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLE_LINK:
			    if (qName.equals("link")) {
			        mState = CATALOG_TITLE;
					movie.movieLink.add(movieLink);
			    }
			    break;
			case CATALOG_TITLE_RELEASE_YEAR:
			    if (qName.equals("release_year")) {
			        mState = CATALOG_TITLE;
					movie.release_year = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLE_CATEGORY:
			    if (qName.equals("category")) {
			        mState = CATALOG_TITLE;
					movie.movieCategory.add(movieCategory);
			    }
			    break;
			case CATALOG_TITLE_RUNTIME:
			    if (qName.equals("runtime")) {
			        mState = CATALOG_TITLE;
					movie.runtime = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLE_AVERAGE_RATING:
			    if (qName.equals("average_rating")) {
			        mState = CATALOG_TITLE;
					movie.average_rating = Float.parseFloat(mBuilder.toString());
			    }
			    break;

            default:
                // do nothing
                break;
        }
    }

    @Override
    public void parse(final Object content) {
        // TODO Auto-generated method stub
    }

// Start of user code MovieParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


