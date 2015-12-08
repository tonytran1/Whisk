package com.example.team3.whisk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class IngredientNutritionView extends AppCompatActivity {

    final private String appID = "d310d646";
    final private String apiKey = "bd677882f63f10d913d8e5447489e947";
    private ArrayList<String> ingredientName = new ArrayList<String>();
    private ArrayList<String> ingredientID = new ArrayList<String>();
    private ArrayList<String> recipeNutrition = new ArrayList<String>();
    private ArrayList<String> recipeIngredient = new ArrayList<String>();
    private ArrayList<String> ingredientText = new ArrayList<String>();
    private ArrayList<String> food = new ArrayList<String>();
    private ArrayList<IngredientNutritionResponse> responseObj = new ArrayList<IngredientNutritionResponse>();
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
        for (int i = 0; i < ingredientID.size(); i++) {
            client.get(IngredientNutritionView.this, obtainURL(ingredientID.get(i)), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String responseStr = new String(responseBody);
                    gson = new Gson();
                    responseObj.add(gson.fromJson(responseStr, IngredientNutritionResponse.class));

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
                    Toast toast = Toast.makeText(IngredientNutritionView.this, "Error, could not resolve URL (Here)", Toast.LENGTH_LONG);
                    toast.show();
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

    public void onRecipeClick(View view)
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
}
