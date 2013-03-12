package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.catalogtitles.*;
// Start of user code CatalogTitlesParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class CatalogTitlesParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = CatalogTitlesParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//CatalogTitles
	private static final int H1 = 43;
	private static final int CATALOG_TITLES = 44;
	private static final int CATALOG_TITLES_NUMBER_OF_RESULTS = 45;
	private static final int CATALOG_TITLES_START_INDEX = 46;
	private static final int CATALOG_TITLES_RESULTS_PER_PAGE = 47;
	private static final int CATALOG_TITLES_CATALOG_TITLE = 48;
	private static final int CATALOG_TITLES_CATALOG_TITLE_ID = 49;
	private static final int CATALOG_TITLES_CATALOG_TITLE_TITLE = 50;
	private static final int CATALOG_TITLES_CATALOG_TITLE_BOX_ART = 51;
	private static final int CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR = 52;
	private static final int CATALOG_TITLES_CATALOG_TITLE_CATEGORY = 53;
	private static final int CATALOG_TITLES_CATALOG_TITLE_LINK = 54;
	private static final int CATALOG_TITLES_CATALOG_TITLE_RUNTIME = 55;
	private static final int CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING = 56;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private CatalogTitles catalogTitles;
	private CatalogTitle catalogTitle;
	private CatalogTitleCategory catalogTitleCategory;
	private CatalogTitleLink catalogTitleLink;

    public CatalogTitlesParser() {
	catalogTitles = new CatalogTitles();
	catalogTitles.catalogTitle = new ArrayList<CatalogTitle>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return catalogTitles;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("catalog_titles")) {
					mState = CATALOG_TITLES;
					
			    }
			    break;
			
			
			case CATALOG_TITLES:
			
			    if (qName.equals("number_of_results")) {
					mState = CATALOG_TITLES_NUMBER_OF_RESULTS;
					
			    }
				else if (qName.equals("start_index")) {
					mState = CATALOG_TITLES_START_INDEX;
					
			    }
				else if (qName.equals("results_per_page")) {
					mState = CATALOG_TITLES_RESULTS_PER_PAGE;
					
			    }
				else if (qName.equals("catalog_title")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
			        catalogTitle = new CatalogTitle();
			        catalogTitle.catalogTitleCategory = new ArrayList<CatalogTitleCategory>();
			        catalogTitle.catalogTitleLink = new ArrayList<CatalogTitleLink>();
					
			    }
			    break;
			
			case CATALOG_TITLES_CATALOG_TITLE:
			
			    if (qName.equals("id")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_ID;
					
			    }
				else if (qName.equals("title")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_TITLE;
					catalogTitle.attrTitleShort = attributes.getValue("short");
					catalogTitle.attrTitleRegular = attributes.getValue("regular");
			    }
				else if (qName.equals("box_art")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_BOX_ART;
					catalogTitle.attrBox_artSmall = attributes.getValue("small");
					catalogTitle.attrBox_artMedium = attributes.getValue("medium");
					catalogTitle.attrBox_artLarge = attributes.getValue("large");
			    }
				else if (qName.equals("release_year")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR;
					
			    }
				else if (qName.equals("category")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE_CATEGORY;
			        catalogTitleCategory = new CatalogTitleCategory();
					catalogTitleCategory.attrLabel = attributes.getValue("label");
					catalogTitleCategory.attrTerm = attributes.getValue("term");
			    }
				else if (qName.equals("link")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE_LINK;
			        catalogTitleLink = new CatalogTitleLink();
					catalogTitleLink.attrHref = attributes.getValue("href");
					catalogTitleLink.attrRel = attributes.getValue("rel");
					catalogTitleLink.attrTitle = attributes.getValue("title");
			    }
				else if (qName.equals("runtime")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_RUNTIME;
					
			    }
				else if (qName.equals("average_rating")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING;
					
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
					catalogTitles.error = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES:
			    if (qName.equals("catalog_titles")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case CATALOG_TITLES_NUMBER_OF_RESULTS:
			    if (qName.equals("number_of_results")) {
			        mState = CATALOG_TITLES;
					catalogTitles.number_of_results = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_START_INDEX:
			    if (qName.equals("start_index")) {
			        mState = CATALOG_TITLES;
					catalogTitles.start_index = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_RESULTS_PER_PAGE:
			    if (qName.equals("results_per_page")) {
			        mState = CATALOG_TITLES;
					catalogTitles.results_per_page = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE:
			    if (qName.equals("catalog_title")) {
			        mState = CATALOG_TITLES;
					catalogTitles.catalogTitle.add(catalogTitle);
			    }
			    break;
			
			case CATALOG_TITLES_CATALOG_TITLE_ID:
			    if (qName.equals("id")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.id = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_TITLE:
			    if (qName.equals("title")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.title = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_BOX_ART:
			    if (qName.equals("box_art")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.box_art = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR:
			    if (qName.equals("release_year")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.release_year = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_CATEGORY:
			    if (qName.equals("category")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.catalogTitleCategory.add(catalogTitleCategory);
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_LINK:
			    if (qName.equals("link")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.catalogTitleLink.add(catalogTitleLink);
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_RUNTIME:
			    if (qName.equals("runtime")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.runtime = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING:
			    if (qName.equals("average_rating")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.average_rating = Float.parseFloat(mBuilder.toString());
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

// Start of user code CatalogTitlesParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


