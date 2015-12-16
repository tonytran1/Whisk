package com.example.team3.whisk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**     File name: MapsActivity.java
 *
 *      This activity allows for pinning the google map of locations of nearby stores that contains selected
 *      ingredients.
 *
 *      This class uses the selected ingredient to obtain an address using XML Parsing
 *      from the SuperMarket API. After obtaining the address from SuperMarket API, the activity then
 *      uses the address to obtain geocoding information by parsing the Google Map API for viewing
 *      on the integrated google map inside the application.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static AsyncHttpClient client;
    private String url;
    private MapsPOJO responseObj;
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
    Store store = new Store();

    String item ="";
    DownloadTaskProduct downloadProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            item = bundle.getString("item");
        }

        productSearchArr.add("chicken");
        productSearchArr.add("beef");
        productSearchArr.add("apple");
        productSearchArr.add("banana");
        productSearchArr.add("orange");
        productSearchArr.add("chocolate");
        productSearchArr.add("chip");

        //Get reference to our ListView
        //storeListView = (ListView) findViewById(R.id.storeList);
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
            productAdapter = new ProductAdapter(MapsActivity.this, -1, productResultList);
            itemListView.setAdapter(productAdapter);

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


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
            productAdapter = new ProductAdapter(MapsActivity.this, -1, ProductXmlPullParser.getListFromFile(MapsActivity.this));
            //itemListView.setAdapter(productAdapter);
        }

    }



    private class DownloadTaskStore extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... arg0) {
            //Download the file
            try {
                Downloader.DownloadFromUrl(arg0[0],
                        openFileOutput("StoresByCityState.xml", Context.MODE_PRIVATE));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }


    /**
     *      Manipulates the map once available.
     *      This callback is triggered when the map is ready to be used.
     *      This is where we can add markers or lines, add listeners or move the camera. In this case,
     *      we just add a marker near Sydney, Australia.
     *      If Google Play services is not installed on the device, the user will be prompted to install
     *      it inside the SupportMapFragment. This method will only be triggered once the user has
     *      installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        storeResultList = StoreXmlPullParser.getListFromFile(MapsActivity.this);
        for (int i = 0; i < storeResultList.size() ; i++) {
            downloadProduct = new DownloadTaskProduct();
            downloadProduct.execute("http://www.supermarketapi.com/api.asmx/COMMERCIAL_SearchForItem?APIKEY=6471b24741" +
                    "&StoreId=" + storeResultList.get(i).getStoreId() +
                    "&ItemName=" +item);
            productResultList = ProductXmlPullParser.getListFromFile(MapsActivity.this);
            if (productResultList.isEmpty()) {
                storeResultList.remove(i);
            }else{
                storeResultList.get(i).setAisleNumber(productResultList.get(0).getAisleNumber());
            }
        }
        client = new AsyncHttpClient();
        mMap = googleMap;

        ArrayList list = new ArrayList();
        for (int j=0; j<storeResultList.size(); j++){
            String completeAddress = storeResultList.get(j).getAddress().toString()+"," + storeResultList.get(j).getCity().toString()+"," + storeResultList.get(j).getState().toString();
            list.add(completeAddress);
        }

        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            client.get(MapsActivity.this, obtainURL(list.get(i).toString()), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String responseStr = new String(responseBody);
                    Gson gson = new Gson();
                    responseObj = gson.fromJson(responseStr, MapsPOJO.class);
                    double lat = responseObj.getResults().get(0).getGeometry().getLocation().getLat();
                    double lng = responseObj.getResults().get(0).getGeometry().getLocation().getLng();

                    // Add a marker in Sydney and move the camera
                    LatLng location = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(location).title(storeResultList.get(finalI).getStorename()).snippet(storeResultList.get(finalI).getAddress() + "\n" + storeResultList.get(finalI).getAisleNumber()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11.5f));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast toast = Toast.makeText(MapsActivity.this, "Error, could not resolve URL", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    /**     This method will add in the required strings for obtaining the URL that contains the
     *      JSON that will be parsed.
     *
     *      @param address Contains the address obtained from parsing SuperMarket API.
     *      @return url Which will contain the JSON that will be parsed by GSON.
     */
    public String obtainURL(String address) {
        url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=AIzaSyD7dsCutDdpMuuR0PeKP3p6IaAM0esmRlw";
        return url;
    }
}
