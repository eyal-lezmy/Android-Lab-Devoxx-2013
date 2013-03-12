package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.newreleases.*;
// Start of user code NewReleasesParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class NewReleasesParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = NewReleasesParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//NewReleases
	private static final int RSS = 1;
	private static final int RSS_CHANNEL = 2;
	private static final int RSS_CHANNEL_TITLE = 3;
	private static final int RSS_CHANNEL_TTL = 4;
	private static final int RSS_CHANNEL_LINK = 5;
	private static final int RSS_CHANNEL_DESCRIPTION = 6;
	private static final int RSS_CHANNEL_LANGUAGE = 7;
	private static final int RSS_CHANNEL_ITEM = 8;
	private static final int RSS_CHANNEL_ITEM_TITLE = 9;
	private static final int RSS_CHANNEL_ITEM_LINK = 10;
	private static final int RSS_CHANNEL_ITEM_DESCRIPTION = 11;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private NewReleases newReleases;
	private ItemNewRelease itemNewRelease;

    public NewReleasesParser() {
	newReleases = new NewReleases();
	newReleases.itemNewRelease = new ArrayList<ItemNewRelease>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return newReleases;
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
			        itemNewRelease = new ItemNewRelease();
					
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
					newReleases.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_TTL:
			    if (qName.equals("ttl")) {
			        mState = RSS_CHANNEL;
					newReleases.ttl = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case RSS_CHANNEL_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL;
					newReleases.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL;
					newReleases.description = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_LANGUAGE:
			    if (qName.equals("language")) {
			        mState = RSS_CHANNEL;
					newReleases.language = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM:
			    if (qName.equals("item")) {
			        mState = RSS_CHANNEL;
					newReleases.itemNewRelease.add(itemNewRelease);
			    }
			    break;
			
			case RSS_CHANNEL_ITEM_TITLE:
			    if (qName.equals("title")) {
			        mState = RSS_CHANNEL_ITEM;
					itemNewRelease.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL_ITEM;
					itemNewRelease.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL_ITEM;
					itemNewRelease.description = mBuilder.toString();
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

// Start of user code NewReleasesParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


