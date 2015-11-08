package com.example.team3.whisk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by abhi on 11/8/15.
 */
public class FilterHome extends Home {

    ArrayList<String> param = new ArrayList<String>();

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
        Intent data = new Intent(FilterHome.this, Filter.class);
        data.putExtra("list", param);
        data.putExtra("search", search);
        startActivity(data);
    }
}
