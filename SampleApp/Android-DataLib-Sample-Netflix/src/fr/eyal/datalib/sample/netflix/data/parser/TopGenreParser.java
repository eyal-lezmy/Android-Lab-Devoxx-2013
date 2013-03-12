package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.topgenre.*;
// Start of user code TopGenreParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class TopGenreParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = TopGenreParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//TopGenre
	private static final int RSS = 26;
	private static final int RSS_CHANNEL = 27;
	private static final int RSS_CHANNEL_TITLE = 28;
	private static final int RSS_CHANNEL_TTL = 29;
	private static final int RSS_CHANNEL_LINK = 30;
	private static final int RSS_CHANNEL_DESCRIPTION = 31;
	private static final int RSS_CHANNEL_LANGUAGE = 32;
	private static final int RSS_CHANNEL_ITEM = 33;
	private static final int RSS_CHANNEL_ITEM_TITLE = 34;
	private static final int RSS_CHANNEL_ITEM_LINK = 35;
	private static final int RSS_CHANNEL_ITEM_DESCRIPTION = 36;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private TopGenre topGenre;
	private ItemTopGenre itemTopGenre;

    public TopGenreParser() {
	topGenre = new TopGenre();
	topGenre.itemTopGenre = new ArrayList<ItemTopGenre>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return topGenre;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("rss")) {
					mState = RSS;
					
			    }
			    break;
			
			case RSS:
			
			    if (qName.equals("channel")) {
					mState = RSS_CHANNEL;
					
			    }
			    break;
			
			case RSS_CHANNEL:
			
			    if (qName.equals("title")) {
					mState = RSS_CHANNEL_TITLE;
					
			    }
				else if (qName.equals("ttl")) {
					mState = RSS_CHANNEL_TTL;
					
			    }
				else if (qName.equals("link")) {
					mState = RSS_CHANNEL_LINK;
					
			    }
				else if (qName.equals("description")) {
					mState = RSS_CHANNEL_DESCRIPTION;
					
			    }
				else if (qName.equals("language")) {
					mState = RSS_CHANNEL_LANGUAGE;
					
			    }
				else if (qName.equals("item")) {
			        mState = RSS_CHANNEL_ITEM;
			        itemTopGenre = new ItemTopGenre();
					
			    }
			    break;
			
			case RSS_CHANNEL_ITEM:
			
			    if (qName.equals("title")) {
					mState = RSS_CHANNEL_ITEM_TITLE;
					
			    }
				else if (qName.equals("link")) {
					mState = RSS_CHANNEL_ITEM_LINK;
					
			    }
				else if (qName.equals("description")) {
					mState = RSS_CHANNEL_ITEM_DESCRIPTION;
					
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
			
			case RSS:
			    if (qName.equals("rss")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case RSS_CHANNEL:
			    if (qName.equals("channel")) {
			        mState = RSS;
			    }
			    break;
				
			case RSS_CHANNEL_TITLE:
			    if (qName.equals("title")) {
			        mState = RSS_CHANNEL;
					topGenre.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_TTL:
			    if (qName.equals("ttl")) {
			        mState = RSS_CHANNEL;
					topGenre.ttl = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case RSS_CHANNEL_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL;
					topGenre.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL;
					topGenre.description = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_LANGUAGE:
			    if (qName.equals("language")) {
			        mState = RSS_CHANNEL;
					topGenre.language = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM:
			    if (qName.equals("item")) {
			        mState = RSS_CHANNEL;
					topGenre.itemTopGenre.add(itemTopGenre);
			    }
			    break;
			
			case RSS_CHANNEL_ITEM_TITLE:
			    if (qName.equals("title")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTopGenre.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTopGenre.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTopGenre.description = mBuilder.toString();
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

// Start of user code TopGenreParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


