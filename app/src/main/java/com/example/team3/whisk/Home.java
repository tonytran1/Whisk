package com.example.team3.whisk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<String> param = new ArrayList<String>();
    private MultiSelectionSpinner multiSelectionSpinner;
    private EditText editText;
    List<String> ingredientList = new ArrayList<>();
    boolean checked;
    SQLiteDatabase recipeDB;
    TextView listText;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Eula(this).show();
        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);
        recipeDB.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER PRIMARY KEY, recipeIngredientText VARCHAR, recipeIngredient VARCHAR, recipeName VARCHAR, recipeNutrition VARCHAR, recipeURL VARCHAR)");
        recipeDB.execSQL("CREATE TABLE IF NOT EXISTS ingredients (id INTEGER PRIMARY KEY, ingredientName VARCHAR)");
        listText = (TextView) findViewById(R.id.listText);
        textView = (TextView) findViewById(R.id.textView);
//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            // Build the alert dialog
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Location Services Not Active");
//            builder.setMessage("Please enable Location Services and GPS");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    // Show location settings when the user acknowledges the alert dialog
//                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(intent);
//                }
//            });
//            Dialog alertDialog = builder.create();
//            alertDialog.setCanceledOnTouchOutside(false);
//            alertDialog.show();
//        }
        editText = (EditText) findViewById(R.id.searchText);
        selectIngredients();
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.search);
        if (ingredientList.size() != 0) {
            multiSelectionSpinner.setItems(ingredientList);
        }
        multiSelectionSpinner.setVisibility(View.GONE);
        listText.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void selectIngredients(){

        recipeDB = this.openOrCreateDatabase("Preferences", MODE_PRIVATE, null);
        String selectQuery = "SELECT * FROM ingredients";
        Cursor c = recipeDB.rawQuery(selectQuery, null);

        int ingredientNameIndex = c.getColumnIndex("ingredientName");
        ingredientList.clear();
        c.moveToFirst();
        int i = c.getCount();

        for (int j=0; j<i; j++){

            ingredientList.add(c.getString(ingredientNameIndex));
            c.moveToNext();

        }
    }

    public void viewChange(View view){

        checked = ((CheckBox) view).isChecked();
        if (checked) {
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            listText.setVisibility(View.VISIBLE);
            multiSelectionSpinner.setVisibility(View.VISIBLE);
            selectIngredients();
            if (ingredientList.size() != 0) {
                multiSelectionSpinner.setItems(ingredientList);
            }
            else {
                Toast.makeText(this, "First Enter The Ingredients In The List", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), IngredientsList.class);
                startActivity(intent);
            }
        }
        else{
            listText.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            multiSelectionSpinner.setVisibility(View.GONE);
        }

    }

    public void selectItem(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId())
        {
            case R.id.dairy:
                if(checked)
                {param.add("&health=dairy-free");}
                else
                {param.remove("&health=dairy-free");}
                break;
            case R.id.vegan:
                if(checked)
                {param.add("&health=vegan");}
                else
                {param.remove("&health=vegan");}
                break;
            case R.id.soy:
                if(checked)
                {param.add("&health=soy-free");}
                else
                {param.remove("&health=soy-free");}
                break;
            case R.id.fish:
                if(checked)
                {param.add("&health=fish-free");}
                else
                {param.remove("&health=fish-free");}
                break;
            case R.id.tnuts:
                if(checked)
                {param.add("&health=tree-nut-free");}
                else
                {param.remove("&health=tree-nut-free");}
                break;
            case R.id.wheat:
                if(checked)
                {param.add("&health=wheat-free");}
                else
                {param.remove("&health=wheat-free");}
                break;
            case R.id.sodium:
                if(checked)
                {param.add("&diet=low-sodium");}
                else
                {param.remove("&diet=low-sodium");}
                break;
            case R.id.fiber:
                if(checked)
                {param.add("&diet=high-fiber");}
                else
                {param.remove("&diet=high-fiber");}
                break;
            case R.id.fat:
                if(checked)
                {param.add("&diet=low-fat");}
                else
                {param.remove("&diet=low-fat");}
                break;
            case R.id.carb:
                if(checked)
                {param.add("&diet=low-carb");}
                else
                {param.remove("&diet=low-carb");}
                break;
            case R.id.paleo:
                if(checked)
                {param.add("&health=paleo");}
                else
                {param.remove("&health=paleo");}
                break;
            case R.id.eggs:
                if(checked)
                {param.add("&health=egg-free");}
                else
                {param.remove("&health=egg-free");}
                break;
            case R.id.protein:
                if(checked)
                {param.add("&diet=high-protein");}
                else
                {param.remove("&diet=high-protein");}
                break;
            case R.id.pnuts:
                if(checked)
                {param.add("&health=peanut-free");}
                else
                {param.remove("&health=peanut-free");}
                break;
            default:
                // do nothing
        }
    }

    public String obtainSearch()
    {
        if (checked) {
            return multiSelectionSpinner.getSelectedItemsAsString();

        }
        else{
            EditText editText = (EditText)findViewById(R.id.searchText);

            return editText.getText().toString();

        }

    }

    public void onSubmit(View view)
    {
        String search = obtainSearch();
        Intent data = new Intent(Home.this, Filter.class);
        data.putExtra("list", param);
        data.putExtra("search", search);
        startActivity(data);
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
        getMenuInflater().inflate(R.menu.home, menu);
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
