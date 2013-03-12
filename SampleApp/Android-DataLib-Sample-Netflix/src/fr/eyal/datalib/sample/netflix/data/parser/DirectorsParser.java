package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.directors.*;
// Start of user code DirectorsParser imports
// You can add here your personal imports
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code


public class DirectorsParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = DirectorsParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//Directors
	private static final int H1 = 98;
	private static final int PEOPLE = 99;
	private static final int PEOPLE_PERSON = 100;
	private static final int PEOPLE_PERSON_ID = 101;
	private static final int PEOPLE_PERSON_NAME = 102;
	private static final int PEOPLE_PERSON_BIO = 103;
	private static final int PEOPLE_PERSON_LINK = 104;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private Directors directors;
	private Director director;
	private DirectorLink directorLink;

    public DirectorsParser() {
	directors = new Directors();
	directors.director = new ArrayList<Director>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return directors;
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
			        director = new Director();
			        director.directorLink = new ArrayList<DirectorLink>();
					
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
			        directorLink = new DirectorLink();
					directorLink.attrHref = attributes.getValue("href");
					directorLink.attrRel = attributes.getValue("rel");
					directorLink.attrTitle = attributes.getValue("title");
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
					directors.error = mBuilder.toString();
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
					directors.director.add(director);
			    }
			    break;
			
			case PEOPLE_PERSON_ID:
			    if (qName.equals("id")) {
			        mState = PEOPLE_PERSON;
					director.id = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_NAME:
			    if (qName.equals("name")) {
			        mState = PEOPLE_PERSON;
					director.name = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_BIO:
			    if (qName.equals("bio")) {
			        mState = PEOPLE_PERSON;
					director.bio = mBuilder.toString();
			    }
			    break;
			case PEOPLE_PERSON_LINK:
			    if (qName.equals("link")) {
			        mState = PEOPLE_PERSON;
					director.directorLink.add(directorLink);
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

// Start of user code DirectorsParser
// You can add here your personal content
// DO NOT MODIFY THE GENERATED COMMENTS "Start of user code" and "End of user code

}


