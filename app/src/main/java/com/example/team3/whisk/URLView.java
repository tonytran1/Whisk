package com.example.team3.whisk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**     File name: URLView.java
 *
 *      This class provides the web viewing activity.
 *
 *      The URL of the web view is obtained by parsing Edamam's API response for the selected recipe.
 *      The web view allows the user to access the URL directly from within the application. From here,
 *      the user can view nutritional information via Nutritionix API or search stores for the ingredients of the recipe
 *      via SuperMarket API using the ingredients obtained from the recipe.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class URLView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private String appID = "16de0c54";
    final private String apiKey = "e87e735d38bbcb5732adbd79dea587dc";
    private WebView webView;
    private ArrayList<String> ingredient;
    private ArrayList<String> ingredientText;
    private ArrayList<String> nutrition;
    private ArrayList<String> ingredientID = new ArrayList<String>();
    private ArrayList<String> ingredientName = new ArrayList<String>();
    private ArrayList<String> food = new ArrayList<String>();
    private IngredientSearchPOJO responseObj;
    private String recipeURL;
    private String recipeName;
    private String JSONURL;
    private int count = 0;
    private Gson gson;
    private static AsyncHttpClient client;
    SQLiteDatabase recipeDB;
    String preference = "";
    ArrayList<String> url = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ur_lview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);
        recipeDB.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER PRIMARY KEY, recipeIngredientText VARCHAR, recipeIngredient VARCHAR, recipeName VARCHAR, recipeNutrition VARCHAR, recipeURL VARCHAR, foodList VARCHAR)");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            food = bundle.getStringArrayList("foodList");
            recipeName = bundle.getString("recipeName");
            ingredient = bundle.getStringArrayList("recipeIngredient");
            ingredientText = bundle.getStringArrayList("recipeIngredientText");
            nutrition = bundle.getStringArrayList("recipeNutrition");
            recipeURL = bundle.getString("recipeURL");
        }
        setTitle("Recipe");
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(recipeURL);
    }

    /**     This method obtains the URL containing the ingredient nutritional information.
     *
     *      @param ingredient Contains the ingredient that will be searched.
     *      @return url Which will contain the JSON that will be parsed by GSON.
     */
    public String obtainURL(String ingredient)
    {
        JSONURL = "https://api.nutritionix.com/v1_1/search/"+ingredient+"?results=0%3A20&cal_min=0&cal_max=50000&fields=item_name%2Cbrand_name%2Citem_id%2Cbrand_id&appId=" + appID + "&appKey=" + apiKey;
        return JSONURL;
    }

    /**     When the nutrition is pressed on, this method will activate into Nutritionix API calls
     *      which will contain all the required nutritional information from each ingredient.
     *
     *      @param view
     */
    public void onNutritionClick(View view)
    {
        ingredientID.clear();
        ingredientName.clear();

        client = new AsyncHttpClient();

        for (int i = 0; i < ingredient.size(); i++)
        {
            client.get(URLView.this, obtainURL(ingredient.get(i)), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String responseStr = new String(responseBody);
                    gson = new Gson();
                    responseObj = gson.fromJson(responseStr, IngredientSearchPOJO.class);
                    ingredientName.add(responseObj.getHits().get(0).getFields().getItem_name());
                    ingredientID.add(responseObj.getHits().get(0).getFields().getItem_id());

                    if (count == (ingredient.size() - 1) )
                    {
                        System.out.println("itemID = " + ingredientID.toString());
                        Intent intent = new Intent(URLView.this, IngredientNutritionView.class);
                        intent.putExtra("recipeIngredient", ingredient);
                        intent.putStringArrayListExtra("foodList", food);
                        intent.putExtra("recipeNutrition", nutrition);
                        intent.putExtra("ingredientName", ingredientName);
                        intent.putExtra("ingredientID", ingredientID);
                        intent.putExtra("ingredientText", ingredientText);
                        intent.putExtra("recipeURL", recipeURL);
                        intent.putExtra("recipeName", recipeName);
                        startActivity(intent);
                    }
                    count++;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast toast = Toast.makeText(URLView.this, "Error, could not resolve URL", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    public void onMapsClick(View view)
    {
        // CHANGE THIS TO ProductActivity.class
        Intent intent = new Intent(URLView.this, ItemActivity.class);
        intent.putStringArrayListExtra("foodList", food);
        startActivity(intent);
    }

    public void saveFavorites(){


        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);

        String selectQuery = "SELECT * FROM favorites WHERE recipeURL = '"+recipeURL+"'";

        try{
            Cursor c = recipeDB.rawQuery(selectQuery, null);
            int recipeURLIndex = c.getColumnIndex("recipeURL");
            c.moveToFirst();
            String savedURL = c.getString(recipeURLIndex);
            //url.add(c.getString(recipeURLIndex));

            if (savedURL != null) {
                Toast.makeText(this, "Already Saved", Toast.LENGTH_LONG).show();
                return;
            }



        }catch (Exception e){

            try {

                Gson gson = new Gson();

                String ingredientsText = "";
                String ingredients = "";
                String nutritions = "";
                String foods = "";
                ingredients = gson.toJson(ingredient);
                ingredientsText = gson.toJson(ingredientText);
                nutritions = gson.toJson(nutrition);
                foods = gson.toJson(food);
                if (nutritions != null){
                    nutritions = "";
                }
                else if(ingredients != null){
                    ingredients = "";
                }
                else if (ingredientsText != null){
                    ingredientsText = "";
                }else if (foods != null){
                    foods = "";
                }

                String sql = "INSERT INTO favorites (recipeIngredientText, recipeIngredient, recipeName, recipeNutrition, recipeURL, foodList) VALUES ('"+ingredientsText+"', '"+ingredients+"', '"+recipeName+"', '"+nutritions+"', '"+recipeURL+"', '"+foods+"')";
                recipeDB.execSQL(sql);
                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show();

            }catch (Exception ex) {

                Toast.makeText(this, "Not able to save", Toast.LENGTH_LONG).show();
            }

        }



    }


    public void showAlert(View view) {


        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Save Recipe")
                .setMessage("Do you want to save this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        saveFavorites();
                        return;
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ur_lview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        } else if (id == R.id.nav_timer) {

            Intent intent = new Intent(getApplicationContext(), Timer.class);
            startActivity(intent);

        } else if (id == R.id.nav_preferences) {

            Intent intent = new Intent(getApplicationContext(), Preferences.class);
            startActivity(intent);

        } else if (id == R.id.nav_ingredients) {
            Intent intent = new Intent(getApplicationContext(), SavedIngredientsList.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
