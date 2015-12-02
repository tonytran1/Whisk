package com.example.team3.whisk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Preferences extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayList<String> food = new ArrayList<String>();
    ArrayList<String> foodText = new ArrayList<String>();
    ArrayList<String> recipeName = new ArrayList<String>();
    ArrayList<String> recipeURL = new ArrayList<String>();
    ArrayList<String> ingredient = new ArrayList<String>();
    ArrayList<String> ingredientText = new ArrayList<String>();
    ArrayList<String> nutrition = new ArrayList<String>();
    EdamamResponse responseObj;
    RecipeAdapter adapter;
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

        updateListView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void updateListView() {

        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);

        String selectQuery = "SELECT * FROM favorites";
        try {

            Cursor c = recipeDB.rawQuery(selectQuery, null);
            if (c == null){

                Toast.makeText(Preferences.this, "No Results! Press back and save some Recipes!", Toast.LENGTH_LONG).show();
                return;

            }
            int recipeIngredientTextIndex = c.getColumnIndex("recipeIngredientText");
            int recipeIngredientIndex = c.getColumnIndex("recipeIngredient");
            int recipeNameIndex = c.getColumnIndex("recipeName");
            int recipeNutritionIndex = c.getColumnIndex("recipeNutrition");
            int recipeURLIndex = c.getColumnIndex("recipeURL");
            int idIndex = c.getColumnIndex("id");

            recipeName.clear();
            recipeURL.clear();
            ingredientText.clear();
            ingredient.clear();
            nutrition.clear();
            c.moveToFirst();

            while (c != null) {

                recipeName.add(c.getString(recipeNameIndex));
                recipeURL.add(c.getString(recipeURLIndex));
                ingredientText.add(c.getString(recipeIngredientTextIndex));
                ingredient.add(c.getString(recipeIngredientIndex));
                nutrition.add(c.getString(recipeNutritionIndex));

                c.moveToNext();
            }
            arrayAdapter.notifyDataSetChanged();



        } catch (Exception e) {

            Toast.makeText(Preferences.this, "No Results! Press back and Save some Recipes!", Toast.LENGTH_LONG).show();
        }


        ListView listView = (ListView) findViewById(R.id.recipeList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeName);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //recipeId = arrayId[position];
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                ingredientText = gson.fromJson(ingredientText.get(position), type);
                ingredient = gson.fromJson(ingredient.get(position), type);
                nutrition = gson.fromJson(nutrition.get(position), type);

                Intent intent = new Intent(getApplicationContext(), URLView.class);
                intent.putExtra("recipeURL", recipeURL.get(position));
                intent.putExtra("recipeName", recipeName.get(position));
                intent.putExtra("recipeIngredientText", ingredientText);
                intent.putExtra("recipeIngredient", ingredient);
                intent.putExtra("recipeNutrition", nutrition);

                //intent.putExtra("recipeId", recipeId);
                startActivity(intent);

            }
        });
    }


    public void home(View view){
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
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

        } else if (id == R.id.nav_save) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
