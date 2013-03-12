package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.synopsis.*;
// Start of user code SynopsisParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class SynopsisParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = SynopsisParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Synopsis
	private static final int H1 = 87;
	private static final int SYNOPSIS = 88;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Synopsis synopsis;

    public SynopsisParser() {
	synopsis = new Synopsis();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return synopsis;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("synopsis")) {
					mState = SYNOPSIS;
					
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
					synopsis.error = mBuilder.toString();
			    }
			    break;
			case SYNOPSIS:
			    if (qName.equals("synopsis")) {
			        mState = UNKNOWN;
					synopsis.synopsis = mBuilder.toString();
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

// Start of user code SynopsisParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


