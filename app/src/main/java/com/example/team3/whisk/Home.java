package com.example.team3.whisk;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<String> param = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Eula(this).show();


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
        EditText editText = (EditText)findViewById(R.id.search);

        return editText.getText().toString();
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

            Intent intent = new Intent(getApplicationContext(), Timer.class);
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
