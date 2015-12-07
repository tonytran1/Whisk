package com.example.team3.whisk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class OutputNutrition extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private String appID = "d310d646";
    final private String apiKey = "bd677882f63f10d913d8e5447489e947";
    private String itemID;
    private View view;
    private IngredientNutritionResponse responseObj;
    private String url;
    private Gson gson;
    private static AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_nutrition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            itemID = bundle.getString("itemID");
        }

        client = new AsyncHttpClient();

        client.get(OutputNutrition.this, obtainURL(itemID), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);
                gson = new Gson();
                responseObj = gson.fromJson(responseStr, IngredientNutritionResponse.class);
                setView(responseObj);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast toast = Toast.makeText(OutputNutrition.this, "Error, could not resolve URL", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


    public String obtainURL(String itemID) {
        url = "https://api.nutritionix.com/v1_1/item?id=" + itemID + "&appId=" + appID + "&appKey=" + apiKey;
        return url;
    }

    public void setView(IngredientNutritionResponse responseObj) {
        TextView title = (TextView) findViewById(R.id.NutritionTitle);
        title.setText(responseObj.getItem_name());

        IngredientNutritionResponse item = responseObj;

        TextView fat = (TextView) findViewById(R.id.fat);
        try {
            fat.setText(item.getNf_total_fat() + "g");
        } catch (NullPointerException e) {
            fat.setText("N/A");
        }

        TextView sugar = (TextView) findViewById(R.id.sugar);
        try {
            sugar.setText(item.getNf_sugars() + "g");
        } catch (NullPointerException e) {
            sugar.setText("N/A");
        }

        TextView fiber = (TextView) findViewById(R.id.fiber);
        try {
            fiber.setText(item.getNf_dietary_fiber() + "g");
        } catch (NullPointerException e) {
            fiber.setText("N/A");
        }

        TextView carb = (TextView) findViewById(R.id.carbs);
        try {
            carb.setText(item.getNf_total_carbohydrate() + "g");
        } catch (NullPointerException e) {
            carb.setText("N/A");
        }

        TextView protein = (TextView) findViewById(R.id.protein);
        try {
            protein.setText(item.getNf_protein() + "g");
        } catch (NullPointerException e) {
            protein.setText("N/A");
        }

        TextView cholest = (TextView) findViewById(R.id.cholesterol);
        try {
            cholest.setText(item.getNf_cholesterol() + "mg");
        } catch (NullPointerException e) {
            cholest.setText("N/A");
        }

        TextView sodium = (TextView) findViewById(R.id.sodium);
        try {
            sodium.setText(item.getNf_sodium() + "mg");
        } catch (NullPointerException e) {
            sodium.setText("N/A");
        }

        TextView calcium = (TextView) findViewById(R.id.calcium);
        try {
            calcium.setText(item.getNf_calcium_dv() + "%");
        } catch (NullPointerException e) {
            calcium.setText("N/A");
        }

        TextView vitA = (TextView) findViewById(R.id.vitA);
        try {
            vitA.setText(item.getNf_vitamin_a_dv() + "%");
        } catch (NullPointerException e) {
            vitA.setText("N/A");
        }

        TextView vitC = (TextView) findViewById(R.id.vitC);
        try {
            vitC.setText(item.getNf_vitamin_c_dv() + "%");
        } catch (NullPointerException e) {
            vitC.setText("N/A");
        }
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
        getMenuInflater().inflate(R.menu.output_nutrition, menu);
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

            Intent intent = new Intent(getApplicationContext(), TimerDennis.class);
            startActivity(intent);

        } else if (id == R.id.nav_preferences) {

            Intent intent = new Intent(getApplicationContext(), Preferences.class);
            startActivity(intent);

        } else if (id == R.id.nav_ingredients) {
            Intent intent = new Intent(getApplicationContext(), IngredientsList.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
