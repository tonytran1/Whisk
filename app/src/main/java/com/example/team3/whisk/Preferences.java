package com.example.team3.whisk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Preferences extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayList<String> food = new ArrayList<String>();
    ArrayList<String> foodText = new ArrayList<String>();
    EdamamResponse responseObj;
    RecipeAdapter adapter;
    String recipeName;
    Gson gson;
    ArrayAdapter arrayAdapter;
    String responseStr;
    SQLiteDatabase recipeDB;
    String search;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*listView = (ListView) findViewById(R.id.recipeList);
        gson = new Gson();
        updateListView();
        responseObj = gson.fromJson(responseStr, EdamamResponse.class);
        adapter = new RecipeAdapter(responseObj.getHits(), Preferences.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        Intent intent = new Intent(Preferences.this, Recipe.class);
                        EdamamResponse.HitsEntity.RecipeEntity recipe = responseObj.getHits().get(position).getRecipe();
                        foodText.clear();
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            food.add((String) recipe.getIngredients().get(i).getFood());
                        }
                        for (int i = 0; i < recipe.getIngredients().size(); i++) {
                            foodText.add((String) recipe.getIngredients().get(i).getText());
                        }
                        recipeName = recipe.getLabel();
                        intent.putExtra("recipeIngredientText", foodText);
                        intent.putExtra("recipeIngredient", food);
                        intent.putExtra("recipeName", recipeName);
                        //intent.putExtra("recipeNutrition", obtainNutrition(recipe));
                        startActivity(intent);
                    }
                }
        );*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public String updateListView() {

        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);

        String selectQuery = "SELECT * FROM favorites";

        try {
            if (recipeDB.equals(null)){

                Toast.makeText(this, "Database empty", Toast.LENGTH_LONG).show();

            }

            Cursor c = recipeDB.rawQuery(selectQuery, null);

            int preferenceIndex = c.getColumnIndex("preference");
            int idIndex = c.getColumnIndex("id");
            c.moveToFirst();
            search = c.getString(preferenceIndex);
            c.moveToNext();
            int i = 0;
            while (c != null) {
                search = search + "," + c.getString(preferenceIndex);
                c.moveToNext();
            }
            arrayAdapter.notifyDataSetChanged();

            return responseStr;


        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;
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
        getMenuInflater().inflate(R.menu.preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
