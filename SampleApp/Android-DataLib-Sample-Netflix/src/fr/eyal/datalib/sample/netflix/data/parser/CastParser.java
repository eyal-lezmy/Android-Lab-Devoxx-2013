package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.cast.*;
// Start of user code CastParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class CastParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = CastParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Cast
	private static final int H1 = 90;
	private static final int PEOPLE = 91;
	private static final int PEOPLE_PERSON = 92;
	private static final int PEOPLE_PERSON_ID = 93;
	private static final int PEOPLE_PERSON_NAME = 94;
	private static final int PEOPLE_PERSON_BIO = 95;
	private static final int PEOPLE_PERSON_LINK = 96;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Cast cast;
	private CastPerson castPerson;
	private CastLink castLink;

    public CastParser() {
	cast = new Cast();
	cast.castPerson = new ArrayList<CastPerson>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return cast;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			
			    if (qName.equals("h1")) {
					mState = H1;
					
			    }
				else if (qName.equals("people")) {
					mState = PEOPLE;
					
			    }
			    break;
			
			
			case PEOPLE:
			
			    if (qName.equals("person")) {
			        mState = PEOPLE_PERSON;
			        castPerson = new CastPerson();
			        castPerson.castLink = new ArrayList<CastLink>();
					
			    }
			    break;
			
			case PEOPLE_PERSON:
			
			    if (qName.equals("id")) {
					mState = PEOPLE_PERSON_ID;
					
			    }
				else if (qName.equals("name")) {
					mState = PEOPLE_PERSON_NAME;
					
			    }
				else if (qName.equals("bio")) {
					mState = PEOPLE_PERSON_BIO;
					
			    }
				else if (qName.equals("link")) {
			        mState = PEOPLE_PERSON_LINK;
			        castLink = new CastLink();
					castLink.attrHref = attributes.getValue("href");
					castLink.attrRel = attributes.getValue("rel");
					castLink.attrTitle = attributes.getValue("title");
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
					cast.error = mBuilder.toString();
			    }
			    break;
			case PEOPLE:
			    if (qName.equals("people")) {
			        mState = UNKNOWN;
			    }
			    break;
				
			case PEOPLE_PERSON:
			    if (qName.equals("person")) {
			        mState = PEOPLE;
					cast.castPerson.add(castPerson);
			    }
			    break;
			
			case PEOPLE_PERSON_ID:
			    if (qName.equals("id")) {
			        mState = PEOPLE_PERSON;
					castPerson.id = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_NAME:
			    if (qName.equals("name")) {
			        mState = PEOPLE_PERSON;
					castPerson.name = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_BIO:
			    if (qName.equals("bio")) {
			        mState = PEOPLE_PERSON;
					castPerson.bio = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_LINK:
			    if (qName.equals("link")) {
			        mState = PEOPLE_PERSON;
					castPerson.castLink.add(castLink);
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

// Start of user code CastParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


