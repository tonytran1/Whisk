package com.example.team3.whisk;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**     File name: ProductActivity.java
 *
 *      Helper class to download XML from URL and get it ready for being parsed
 *      by XMLPullParser.
 *
 *      This activity obtains the ingredients from the recipe and goes to two SuperMarket API calls to get
 *      the address of eligible stores obtaining the selected ingredient.
 *      The Google Map API will later be used to obtain geocoding information to display on the
 *      google map.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class ProductActivity extends Activity {

    private static final String TAG = "ProductActivity";

    ListView storeListView;
    StoreAdapter storeAdapter;

    ListView itemListView;
    ProductAdapter productAdapter;

    List<Product> productTempList = new ArrayList<Product>();
    List<Product> productResultList = new ArrayList<Product>();
    List<Store> storeResultList = new ArrayList<Store>();

    List<String> productSearchArr = new ArrayList<String>();

    List<Store> storePassParam;

    StoreService storeService;

    String city = null;
    String state = null;
    String productName = new String();
    DownloadTaskProduct downloadProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.store_list);
//        storeService = new StoreService();
//        List<Store> storeList = storeService.getStore();
        setContentView(R.layout.store_list);


        productSearchArr.add("chicken");
        productSearchArr.add("beef");
        productSearchArr.add("apple");
        productSearchArr.add("banana");
        productSearchArr.add("orange");
        productSearchArr.add("chocolate");
        productSearchArr.add("chip");

        //Get reference to our ListView
        storeListView = (ListView) findViewById(R.id.storeList);
        itemListView = (ListView) findViewById(R.id.productList);

        /**
         * Set the click listener to launch the browser when a row is clicked.
         */
        if (isNetworkAvailable()) {
            Log.i("ProductActivity", "starting download Task");

            DownloadTaskStore downloadStore;
            downloadStore = new DownloadTaskStore();
            downloadStore.execute("http://www.supermarketapi.com/api.asmx/StoresByCityState?APIKEY=6471b24741&SelectedCity=San%20Francisco&SelectedState=CA");
        } else {

            //test the product list from parsing xml file
            productAdapter = new ProductAdapter(ProductActivity.this, -1, productResultList);
            itemListView.setAdapter(productAdapter);

           /* storeAdapter = new StoreAdapter(ProductActivity.this, -1,storeResultList);
            storeListView.setAdapter(storeAdapter);*/
        }

    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
    private class DownloadTaskProduct extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... arg0) {
            //Download the file
            try {
                Downloader.DownloadFromUrl(arg0[0],
                        openFileOutput("COMMERCIAL_SearchForItem.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            productAdapter = new ProductAdapter(ProductActivity.this, -1, ProductXmlPullParser.getListFromFile(ProductActivity.this));
            //itemListView.setAdapter(productAdapter);
        }

    }
        /**     This method obtains the URL for parsing to check for ingredients in nearby stores.
        *
        *      @param searchItem Contains the ingredient being searched.
        *      @return url Which will contain the XML that will be parsed by XMLPullParser.
        */
        public String obtainProductURL(String searchItem) {
            String replacedItem = searchItem.replace(" ", "%20");

            String URL = "http://www.supermarketapi.com/api.asmx/SearchByProductName?APIKEY=6471b24741&ItemName="
                    + replacedItem;
            return URL;
        }

        private class DownloadTaskStore extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... arg0) {
                //Download the file
                try {
                    storeResultList = StoreXmlPullParser.getListFromFile(ProductActivity.this);
                    Downloader.DownloadFromUrl(arg0[0],
                            openFileOutput("StoresByCityState.xml", Context.MODE_PRIVATE));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                for (int i = 0; i < storeResultList.size() ; i++) {
                    downloadProduct = new DownloadTaskProduct();
                    downloadProduct.execute("http://www.supermarketapi.com/api.asmx/COMMERCIAL_SearchForItem?APIKEY=6471b24741" +
                            "&StoreId=" + storeResultList.get(i).getStoreId() +
                            "&ItemName=" + "chicken");
                    productResultList = ProductXmlPullParser.getListFromFile(ProductActivity.this);
                    if (productResultList.isEmpty()) {
                        storeResultList.remove(i);
                    }else{
                        storeResultList.get(i).setItemName(productResultList.get(0).getItemName());
                    }
                }
                storeAdapter = new StoreAdapter(ProductActivity.this, -1, storeResultList);
                storeListView.setAdapter(storeAdapter);
            }
    }

    /**     This method obtains the URL for parsing to obtain nearby stores.
     *
     *      @param city Contains the city.
     *      @param state Contains the state.
     *      @return url Which will contain the XML that will be parsed by XMLPullParser.
     */
    public String obtainStoreURL(String city, String state) {
        String URL = "http://www.supermarketapi.com/api.asmx/StoresByCityState?APIKEY=6471b24741&SelectedCity=San%20Francisco&SelectedState=CA";
        return URL;
    }
}