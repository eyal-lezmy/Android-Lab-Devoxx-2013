/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.lib.data.parser;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.ParseException;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.util.Out;

/**
 * {@link GenericParser} is a class who handles the parsing of a network request result depending on the parsing format previously asked by the request.
 * 
 * @author Eyal LEZMY
 */
public class GenericParser {

    protected static final int PARSE_TYPE_SAX = 1;
    protected static final int PARSE_TYPE_JSON = 2;
    protected static final int PARSE_TYPE_IMAGE = 3;

    private static final String TAG = GenericParser.class.getSimpleName();
    private GenericHandler mHandler;

    /**
     * Constructor
     * @param handler the business model object <code>DefaultHandler</code> implementation.
     * */
    public GenericParser(final GenericHandler handler) {
        mHandler = handler;
    }

    /**
     * Parse a xml data
     * 
     * @param xml a String that contains the xml data
     * */
    public void parseSheet(final String xml) throws ParseException {
        parseSheet(xml, PARSE_TYPE_SAX);
    }

    public void parseSheet(final Object content, final int parseType) throws ParseException {

        //Sax method used for XML
        if (parseType == PARSE_TYPE_SAX) {

            //we convert the content to String
            String xml = new String((byte[]) content);
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                final SAXParser sp = factory.newSAXParser();
                final XMLReader xr = sp.getXMLReader();

                final InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));

                //we set the SAX DefaultHandler
                xr.setContentHandler((DefaultHandler) mHandler);

                Out.v(TAG, "start parsing SAX");

                xr.parse(is);

                Out.v(TAG, "end parsing SAX");

            } catch (final Exception e) {
                e.printStackTrace();
                throw new ParseException("Parsing error");
            }

        } else if (parseType == PARSE_TYPE_JSON) {

            mHandler.parse(content);

        } else if (parseType == PARSE_TYPE_IMAGE) {

            mHandler.parse(content);

        }

    }
}
