package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.autocomplete.*;
// Start of user code AutocompleteParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class AutocompleteParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = AutocompleteParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Autocomplete
	private static final int H1 = 38;
	private static final int AUTOCOMPLETE = 39;
	private static final int AUTOCOMPLETE_AUTOCOMPLETE_ITEM = 40;
	private static final int AUTOCOMPLETE_AUTOCOMPLETE_ITEM_TITLE = 41;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Autocomplete autocomplete;
	private Autocomplete_item autocomplete_item;

    public AutocompleteParser() {
	autocomplete = new Autocomplete();
	autocomplete.autocomplete_item = new ArrayList<Autocomplete_item>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return autocomplete;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("autocomplete")) {
					mState = AUTOCOMPLETE;
					
			    }
			    break;
			
			
			case AUTOCOMPLETE:
			
			    if (qName.equals("autocomplete_item")) {
			        mState = AUTOCOMPLETE_AUTOCOMPLETE_ITEM;
			        autocomplete_item = new Autocomplete_item();
					
			    }
			    break;
			
			case AUTOCOMPLETE_AUTOCOMPLETE_ITEM:
			
			    if (qName.equals("title")) {
					mState = AUTOCOMPLETE_AUTOCOMPLETE_ITEM_TITLE;
					autocomplete_item.attrTitleShort = attributes.getValue("short");
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
					autocomplete.error = mBuilder.toString();
			    }
			    break;
			case AUTOCOMPLETE:
			    if (qName.equals("autocomplete")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case AUTOCOMPLETE_AUTOCOMPLETE_ITEM:
			    if (qName.equals("autocomplete_item")) {
			        mState = AUTOCOMPLETE;
					autocomplete.autocomplete_item.add(autocomplete_item);
			    }
			    break;
			
			case AUTOCOMPLETE_AUTOCOMPLETE_ITEM_TITLE:
			    if (qName.equals("title")) {
			        mState = AUTOCOMPLETE_AUTOCOMPLETE_ITEM;
					autocomplete_item.title = mBuilder.toString();
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

// Start of user code AutocompleteParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


