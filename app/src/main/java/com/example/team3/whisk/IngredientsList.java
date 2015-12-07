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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IngredientsList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText ingredientName;
    Button addButton;
    Button saveButton;
    ListView ingredientsList;
    ArrayAdapter arrayAdapter;
    SQLiteDatabase recipeDB;
    String url ="";
    String ingredient = "";
    String[] array = new String[20];
    ArrayList<String> ingredientList = new ArrayList<String>();
    TextView textView;
    TextView savetittle;
    TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ingredientName = (EditText)findViewById(R.id.editText);
        ingredientsList = (ListView)findViewById(R.id.ingredientList);
        addButton = (Button)findViewById(R.id.addButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        textView = (TextView)findViewById(R.id.ingredientTitle);
        savetittle = (TextView)findViewById(R.id.saveIngredientTittle);
        message = (TextView)findViewById(R.id.blankIngredientTittle);
        ingredientName.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        savetittle.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);
        recipeDB.execSQL("CREATE TABLE IF NOT EXISTS ingredients (id INTEGER PRIMARY KEY, ingredientName VARCHAR)");
        updateListView();
        deleteListView();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void changeView (View view){

        ingredientsList.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        savetittle.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        ingredientName.setVisibility(View.VISIBLE);


    }


    public void saveIngredients(View view){


        ingredient = ingredientName.getText().toString();
        if (ingredient.equals("")){
            Toast.makeText(this, "Please Enter The Ingredient", Toast.LENGTH_LONG).show();
            return;
        }
        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);

        String selectQuery = "SELECT * FROM ingredients WHERE ingredientName = '"+ingredient+"'";

        try{
            Cursor c = recipeDB.rawQuery(selectQuery, null);
            int ingredientNameIndex = c.getColumnIndex("ingredientName");
            c.moveToFirst();
            String savedIngredient = c.getString(ingredientNameIndex);
            //url.add(c.getString(recipeURLIndex));

            if (savedIngredient != null) {
                Toast.makeText(this, "Already Saved", Toast.LENGTH_LONG).show();
                return;
            }



        }catch (Exception e){

            try {


                String sql = "INSERT INTO ingredients (ingredientName) VALUES ('"+ingredient+"')";
                recipeDB.execSQL(sql);
                Toast.makeText(this, "Ingredient Saved", Toast.LENGTH_LONG).show();
                ingredientName.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
                savetittle.setVisibility(View.GONE);
                ingredientsList.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                updateListView();
            }catch (Exception ex) {

                Toast.makeText(this, "Not able to save", Toast.LENGTH_LONG).show();
            }

        }



    }


    public void updateListView() {

        String selectQuery = "SELECT * FROM ingredients";

        try {
            Cursor c = recipeDB.rawQuery(selectQuery, null);
            if (c.getCount() == 0){
                textView.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
                Toast.makeText(IngredientsList.this, "Save some Ingredients!", Toast.LENGTH_LONG).show();
            }
            int ingredientNameIndex = c.getColumnIndex("ingredientName");

            ingredientList.clear();
            c.moveToFirst();

            while (c != null) {

                ingredientList.add(c.getString(ingredientNameIndex));
                c.moveToNext();
            }
            arrayAdapter.notifyDataSetChanged();



        } catch (Exception e) {

            e.printStackTrace();

        }


        ListView listView = (ListView) findViewById(R.id.ingredientList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        listView.setAdapter(arrayAdapter);

    }



    public void deleteListView() {

        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);

        String selectQuery = "SELECT * FROM ingredients";
        ListView listView = (ListView) findViewById(R.id.ingredientList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ingredient = ingredientList.get(position);
                showAlert();
                return true;
            }
        });
    }



    public void showAlert() {


        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Ingredient")
                .setMessage("Do you want to delete this ingredient?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFavorites();
                        updateListView();
                        return;
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    public void deleteFavorites(){
        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);
        String sql = "DELETE FROM ingredients WHERE ingredientName = '"+ingredient+"'";
        recipeDB.execSQL(sql);
        Toast.makeText(this, "Ingredient Deleted", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.ingredients_list, menu);
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
