package com.example.team3.whisk;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**     File name: StoreXmlPullParser.java
 *
 *      This class parses the XML obtained by the URL for store locations of nearby stores
 *      containing the ingredient selected.
 *
 *      @author Team 3
 *      @version 1.00
 */
public class StoreXmlPullParser {

    static final String KEY_STORE = "Store";
    static final String KEY_STORENAME = "Storename";
    static final String KEY_ADDRESS = "Address";
    static final String KEY_CITY = "City";
    static final String KEY_STATE = "State";
    static final String KEY_Zip = "Zip";
    static final String KEY_PHONE = "Phone";
    static final String KEY_STOREID = "StoreId";

    public static List<Store> getListFromFile(Context ctx) {

        // List of Store that we will return
        List<Store> storeList;
        storeList = new ArrayList<Store>();

        // temp holder for current Store while parsing
        Store curStore = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("StoresByCityState.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // point the parser to our file.
            xpp.setInput(reader);

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (storeList.size() < 7) {

                // Get the current tag
                String tagName = xpp.getName();

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if (tagName.equalsIgnoreCase(KEY_STORE)) {
                            // If we are starting a new <Store> block we need
                            //a new Store object to represent it
                            curStore = new Store();
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;
                    }

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_STORE)) {
                            // if </Store> then we are done with current Store
                            // add it to the list.

                            storeList.add(curStore);

                        } else if (tagName.equalsIgnoreCase(KEY_STORENAME)) {
                            // if </Storename> use setStorename() on curStore
                            curStore.setStorename(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ADDRESS)) {
                            // if </Address> use setAddress() on curStore
                            curStore.setAddress(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_CITY)) {
                            // if </City> use setCity() on curStore
                            curStore.setCity(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_STATE)) {
                            // if </State> use setState() on curStore
                            curStore.setState(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_Zip)) {
                            // if </Zip> use setZip() on curStore
                            curStore.setZip(curText);
                        } else if (tagName.equals(KEY_PHONE)) {
                            // if </Phone> use setPhone() on curStore
                            curStore.setPhone(curText);
                        } else if (tagName.equals(KEY_STOREID)) {
                            // if </StoreID> use setStoreId() on curStore
                            curStore.setStoreId(curText);
                        }
                        break;
                    default:
                        break;
                }
                //move on to next iteration
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return storeList;
    }
}
