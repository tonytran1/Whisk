package com.example.team3.whisk;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.List;

/**     File name: StoreService.java
 *
 *      Helper class to download XML from URL and get it ready for being parsed
 *      by XMLPullParser.
 *
 *      @author Team 3
 *      @version 1.00
 */
public class StoreService extends Service {
    private static  final String TAG = "StoreService";

    AppLocationService appLocationService;
    String searchCity = null;
    String searchState = null;
    List<Store> storeList;

    public StoreService() {
        storeList = null;
    }

    public List<Store> getStore() {

        appLocationService = new AppLocationService(
                StoreService.this);

        if (isNetworkAvailable()) {
            Log.i(TAG, "starting download Task");
            DownloadTask download = new DownloadTask();
            String apiURL = obtainURL(searchCity, searchState);
            download.execute(apiURL);
        }

        storeList =  StoreXmlPullParser.getListFromFile(StoreService.this);
        return storeList;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
    private class DownloadTask extends AsyncTask<String, Void, Void> {

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
    }

    /**     This method obtains the URL for parsing to obtain nearby stores.
     *
     *      @param city Contains the city.
     *      @param state Contains the state.
     *      @return url Which will contain the XML that will be parsed by XMLPullParser.
     */
    public String obtainURL(String city, String state) {
        String cityReplace = city.replace(" ", "%20");
        String URL = "http://www.supermarketapi.com/api.asmx/StoresByCityState?APIKEY=6471b24741&SelectedCity=San%20Francisco&SelectedState=CA";
        return URL;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                StoreService.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        StoreService.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
