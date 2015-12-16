package com.example.team3.whisk;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**     File name: ProductXmlPullParser.java
 *
 *      This class parses the XML obtained by the URL to obtain the product searched
 *      by keyword.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class ProductXmlPullParser {
    
    static final String KEY_PRODUCT = "Product_Commercial";
    static final String KEY_ITEMNAME = "Itemname";
    static final String KEY_ITEMDESCRIPTION = "ItemDescription";
    static final String KEY_ITEMCATEGORY = "ItemCategory";
    static final String KEY_ITEMID = "ItemID";
    static final String KEY_IMAGE_URL = "ItemImage";
    static final String KEY_AISLENUMBER = "AisleNumber";
    static final String KEY_PRICING = "Pricing";

    public static List<Product> getListFromFile(Context ctx) {

        // List of Product that we will return
        List<Product> productList;
        productList = new ArrayList<Product>();

        // temp holder for current Product while parsing
        Product curProduct = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("COMMERCIAL_SearchForItem.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // point the parser to our file.
            xpp.setInput(reader);

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (productList.size()<1) {

                // Get the current tag
                String tagName = xpp.getName();

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if (tagName.equalsIgnoreCase(KEY_PRODUCT)) {
                            // If we are starting a new <Product> block we need
                            //a new Product object to represent it
                            curProduct = new Product();
                        }
                        break;
                    }
                    case XmlPullParser.TEXT: {
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;
                    }

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_PRODUCT)) {
                            // if </Product> then we are done with current Product
                            // add it to the list.
                            productList.add(curProduct);
                        } else if (tagName.equalsIgnoreCase(KEY_ITEMNAME)) {
                            // if </Itemname> use setItemName() on curProduct
                            curProduct.setItemName(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ITEMDESCRIPTION)) {
                            // if </ItemDescription> use setItemDescription() on curProduct
                            curProduct.setItemDescription(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ITEMCATEGORY)) {
                            // if </ItemCategory> use setItemCategory() on curProduct
                            curProduct.setItemCategory(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ITEMID)) {
                            // if </ItemID> use setItemID() on curProduct
                            curProduct.setItemID(curText);
                        }else if (tagName.equalsIgnoreCase(KEY_IMAGE_URL)) {
                            // if </ItemImage> use setItemImage() on curProduct
                            curProduct.setItemImage(curText);
                        } else if (tagName.equals(KEY_AISLENUMBER)) {
                            // if </AisleNumber> use setAisleNumber() on curProduct
                            curProduct.setAisleNumber(curText);
                        } else if (tagName.equals(KEY_PRICING)) {
                            curProduct.setPricing(curText);
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
        return productList;
    }
}
