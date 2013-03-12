package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.top100.*;
// Start of user code Top100Parser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class Top100Parser extends DefaultHandler implements GenericHandler {

    private static final String TAG = Top100Parser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Top100
	private static final int RSS = 14;
	private static final int RSS_CHANNEL = 15;
	private static final int RSS_CHANNEL_TITLE = 16;
	private static final int RSS_CHANNEL_TTL = 17;
	private static final int RSS_CHANNEL_LINK = 18;
	private static final int RSS_CHANNEL_DESCRIPTION = 19;
	private static final int RSS_CHANNEL_LANGUAGE = 20;
	private static final int RSS_CHANNEL_ITEM = 21;
	private static final int RSS_CHANNEL_ITEM_TITLE = 22;
	private static final int RSS_CHANNEL_ITEM_LINK = 23;
	private static final int RSS_CHANNEL_ITEM_DESCRIPTION = 24;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Top100 top100;
	private ItemTop100 itemTop100;

    public Top100Parser() {
	top100 = new Top100();
	top100.itemTop100 = new ArrayList<ItemTop100>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return top100;
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
			        itemTop100 = new ItemTop100();
					
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
					top100.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_TTL:
			    if (qName.equals("ttl")) {
			        mState = RSS_CHANNEL;
					top100.ttl = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case RSS_CHANNEL_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL;
					top100.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL;
					top100.description = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_LANGUAGE:
			    if (qName.equals("language")) {
			        mState = RSS_CHANNEL;
					top100.language = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM:
			    if (qName.equals("item")) {
			        mState = RSS_CHANNEL;
					top100.itemTop100.add(itemTop100);
			    }
			    break;
			
			case RSS_CHANNEL_ITEM_TITLE:
			    if (qName.equals("title")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTop100.title = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_LINK:
			    if (qName.equals("link")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTop100.link = mBuilder.toString();
			    }
			    break;
			case RSS_CHANNEL_ITEM_DESCRIPTION:
			    if (qName.equals("description")) {
			        mState = RSS_CHANNEL_ITEM;
					itemTop100.description = mBuilder.toString();
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

// Start of user code Top100Parser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


