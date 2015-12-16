package com.example.team3.whisk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**     File name: IngredientNutritionView
 *
 *      This class provides the activity for nutritional facts. The activity obtains nutritional
 *      information from each ingredient for the recipe selected
 *
 *      @author Team 3
 *      @version 1.00
 */

public class IngredientNutritionView extends AppCompatActivity {

    final private String appID = "16de0c54";
    final private String apiKey = "e87e735d38bbcb5732adbd79dea587dc";
    private ArrayList<String> ingredientName = new ArrayList<String>();
    private ArrayList<String> ingredientID = new ArrayList<String>();
    private ArrayList<String> recipeNutrition = new ArrayList<String>();
    private ArrayList<String> recipeIngredient = new ArrayList<String>();
    private ArrayList<String> ingredientText = new ArrayList<String>();
    private ArrayList<String> food = new ArrayList<String>();
    private ArrayList<IngredientNutritionPOJO> responseObj = new ArrayList<IngredientNutritionPOJO>();
    private String url;
    private String ingredient;
    private String recipeName;
    private String recipeURL;
    private int count = 0;
    private View view;
    private ListView listView;
    private Gson gson;
    private static AsyncHttpClient client;
    private NutritionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_nutrition_view);
        listView = (ListView) findViewById(R.id.nutritionList);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            ingredientName = bundle.getStringArrayList("ingredientName");
            food = bundle.getStringArrayList("foodList");
            ingredientID = bundle.getStringArrayList("ingredientID");
            recipeNutrition = bundle.getStringArrayList("recipeNutrition");
            recipeIngredient = bundle.getStringArrayList("recipeIngredient");
            ingredientText = bundle.getStringArrayList("ingredientText");
            recipeName = bundle.getString("recipeName");
            recipeURL = bundle.getString("recipeURL");
        }

        setTitle("Nutritional Facts");

        client = new AsyncHttpClient();
        // For loop for each ingredient from recipe.
        for (int i = 0; i < ingredientID.size(); i++) {
            // Obtain response from URL and parse with GSON.
            client.get(IngredientNutritionView.this, obtainURL(ingredientID.get(i)), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String responseStr = new String(responseBody);
                    gson = new Gson();
                    responseObj.add(gson.fromJson(responseStr, IngredientNutritionPOJO.class));

                    if (count == (ingredientID.size() - 1) )
                    {
                        adapter = new NutritionAdapter(responseObj, IngredientNutritionView.this);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(
                                new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                                        Intent intent = new Intent(IngredientNutritionView.this, SearchIngredient.class);
                                        ingredient = responseObj.get(position).getItem_name();
                                        intent.putExtra("food", ingredient);
                                        startActivity(intent);
                                    }
                                }
                        );
                    }
                    count++;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    Toast toast = Toast.makeText(IngredientNutritionView.this, "Error, could not resolve URL (Here)", Toast.LENGTH_LONG);
//                    toast.show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingredient_nutrition_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    /**     This method will add in the required strings for obtaining the URL that contains the
     *      JSON that will be parsed.
     *
     *      @param itemID Contains the Nutritionix API item ID associated with each ingredient.
     *      @return url Which will contain the JSON that will be parsed by GSON.
     */
    public String obtainURL(String itemID) {
        url = "https://api.nutritionix.com/v1_1/item?id=" + itemID + "&appId=" + appID + "&appKey=" + apiKey;
        return url;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(IngredientNutritionView.this, URLView.class);
        intent.putExtra("recipeIngredient", recipeIngredient);
        intent.putExtra("recipeNutrition", recipeNutrition);
        intent.putExtra("ingredientText", ingredientText);
        intent.putExtra("recipeName", recipeName);
        intent.putExtra("recipeURL", recipeURL);
        intent.putExtra("foodList", food);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

/*    public void onRecipeClick(View view)
    {
        Intent intent = new Intent(IngredientNutritionView.this, URLView.class);
        intent.putExtra("recipeIngredient", recipeIngredient);
        intent.putExtra("recipeNutrition", recipeNutrition);
        intent.putExtra("ingredientText", ingredientText);
        intent.putExtra("recipeName", recipeName);
        intent.putExtra("recipeURL", recipeURL);
        intent.putExtra("foodList", food);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/
}
