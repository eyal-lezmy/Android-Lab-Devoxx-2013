package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.people.*;
// Start of user code PeopleParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class PeopleParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = PeopleParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//People
	private static final int H1 = 58;
	private static final int PERSON = 59;
	private static final int PERSON_ID = 60;
	private static final int PERSON_NAME = 61;
	private static final int PERSON_LINK = 62;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private People people;
	private PeopleLink peopleLink;

    public PeopleParser() {
	people = new People();
	people.peopleLink = new ArrayList<PeopleLink>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return people;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("person")) {
					mState = PERSON;
					
			    }
			    break;
			
			
			case PERSON:
			
			    if (qName.equals("id")) {
					mState = PERSON_ID;
					
			    }
				else if (qName.equals("name")) {
					mState = PERSON_NAME;
					
			    }
				else if (qName.equals("link")) {
			        mState = PERSON_LINK;
			        peopleLink = new PeopleLink();
					peopleLink.attrHref = attributes.getValue("href");
					peopleLink.attrRel = attributes.getValue("rel");
					peopleLink.attrTitle = attributes.getValue("title");
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
					people.error = mBuilder.toString();
			    }
			    break;
			case PERSON:
			    if (qName.equals("person")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case PERSON_ID:
			    if (qName.equals("id")) {
			        mState = PERSON;
					people.id = mBuilder.toString();
			    }
			    break;
			case PERSON_NAME:
			    if (qName.equals("name")) {
			        mState = PERSON;
					people.name = mBuilder.toString();
			    }
			    break;
			case PERSON_LINK:
			    if (qName.equals("link")) {
			        mState = PERSON;
					people.peopleLink.add(peopleLink);
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

// Start of user code PeopleParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


