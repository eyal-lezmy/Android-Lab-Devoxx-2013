package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.filmography.*;
// Start of user code FilmographyParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class FilmographyParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = FilmographyParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Filmography
	private static final int H1 = 64;
	private static final int FILMOGRAPHY = 65;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM = 66;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_ID = 67;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_TITLE = 68;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_BOX_ART = 69;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_LINK = 70;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_RELEASE_YEAR = 71;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_CATEGORY = 72;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_RUNTIME = 73;
	private static final int FILMOGRAPHY_FILMOGRAPHY_ITEM_AVERAGE_RATING = 74;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Filmography filmography;
	private Filmography_item filmography_item;
	private FilmographyLink filmographyLink;
	private FilmographyCategory filmographyCategory;

    public FilmographyParser() {
	filmography = new Filmography();
	filmography.filmography_item = new ArrayList<Filmography_item>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return filmography;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("filmography")) {
					mState = FILMOGRAPHY;
					
			    }
			    break;
			
			
			case FILMOGRAPHY:
			
			    if (qName.equals("filmography_item")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
			        filmography_item = new Filmography_item();
			        filmography_item.filmographyLink = new ArrayList<FilmographyLink>();
			        filmography_item.filmographyCategory = new ArrayList<FilmographyCategory>();
					
			    }
			    break;
			
			case FILMOGRAPHY_FILMOGRAPHY_ITEM:
			
			    if (qName.equals("id")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_ID;
					
			    }
				else if (qName.equals("title")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_TITLE;
					filmography_item.attrTitleShort = attributes.getValue("short");
					filmography_item.attrTitleRegular = attributes.getValue("regular");
			    }
				else if (qName.equals("box_art")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_BOX_ART;
					filmography_item.attrBox_artSmall = attributes.getValue("small");
					filmography_item.attrBox_artMedium = attributes.getValue("medium");
					filmography_item.attrBox_artLarge = attributes.getValue("large");
			    }
				else if (qName.equals("link")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_LINK;
			        filmographyLink = new FilmographyLink();
					filmographyLink.attrHref = attributes.getValue("href");
					filmographyLink.attrRel = attributes.getValue("rel");
					filmographyLink.attrTitle = attributes.getValue("title");
			    }
				else if (qName.equals("release_year")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_RELEASE_YEAR;
					
			    }
				else if (qName.equals("category")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_CATEGORY;
			        filmographyCategory = new FilmographyCategory();
					filmographyCategory.attrScheme = attributes.getValue("scheme");
					filmographyCategory.attrLabel = attributes.getValue("label");
					filmographyCategory.attrTerm = attributes.getValue("term");
			    }
				else if (qName.equals("runtime")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_RUNTIME;
					
			    }
				else if (qName.equals("average_rating")) {
					mState = FILMOGRAPHY_FILMOGRAPHY_ITEM_AVERAGE_RATING;
					
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
					filmography.error = mBuilder.toString();
			    }
			    break;
			case FILMOGRAPHY:
			    if (qName.equals("filmography")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case FILMOGRAPHY_FILMOGRAPHY_ITEM:
			    if (qName.equals("filmography_item")) {
			        mState = FILMOGRAPHY;
					filmography.filmography_item.add(filmography_item);
			    }
			    break;
			
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_ID:
			    if (qName.equals("id")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.id = mBuilder.toString();
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_TITLE:
			    if (qName.equals("title")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.title = mBuilder.toString();
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_BOX_ART:
			    if (qName.equals("box_art")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.box_art = mBuilder.toString();
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_LINK:
			    if (qName.equals("link")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.filmographyLink.add(filmographyLink);
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_RELEASE_YEAR:
			    if (qName.equals("release_year")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.release_year = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_CATEGORY:
			    if (qName.equals("category")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.filmographyCategory.add(filmographyCategory);
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_RUNTIME:
			    if (qName.equals("runtime")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.runtime = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case FILMOGRAPHY_FILMOGRAPHY_ITEM_AVERAGE_RATING:
			    if (qName.equals("average_rating")) {
			        mState = FILMOGRAPHY_FILMOGRAPHY_ITEM;
					filmography_item.average_rating = Float.parseFloat(mBuilder.toString());
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

// Start of user code FilmographyParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


