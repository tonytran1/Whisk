package com.example.team3.whisk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhi on 11/8/15.
 */
public class RecipeAdapter extends BaseAdapter
{

    String recipeName;
    SQLiteDatabase recipeDB;

    private List<EdamamResponse.HitsEntity> rItem;
    private Context rContext;

    public RecipeAdapter(List<EdamamResponse.HitsEntity> rItem, Context rContext)
    {
        this.rItem = rItem;
        this.rContext = rContext;
    }

    @Override
    public int getCount() {
        return rItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) rContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_recipe, parent, false);
        EdamamResponse.HitsEntity item = (EdamamResponse.HitsEntity) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
        Picasso.with(rContext).load(item.getRecipe().getImage()).into(thumbnail);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(item.getRecipe().getLabel());

        TextView ingredients = (TextView) rowView.findViewById(R.id.ingredients);
        ingredients.setText(obtainNutrition(item.getRecipe()).toString());
        //ingredients.setText(item.getRecipe().getIngredientLines().toString());

        return rowView;
    }

    public ArrayList<String> obtainNutrition(EdamamResponse.HitsEntity.RecipeEntity recipe)
    {
        ArrayList<String> recipeNutrition = new ArrayList<String>();
        DecimalFormat df = new DecimalFormat("0.00");

        EdamamResponse.HitsEntity.RecipeEntity.TotalNutrientsEntity nutrition = recipe.getTotalNutrients();

        try {
            recipeNutrition.add("Fat: " + df.format(nutrition.getFAT().getQuantity()) + nutrition.getFAT().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Sugar: " + df.format(nutrition.getSUGAR().getQuantity()) + nutrition.getSUGAR().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Protein: " + df.format(nutrition.getPROCNT().getQuantity()) + nutrition.getPROCNT().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Cholesterol: " + df.format(nutrition.getCHOLE().getQuantity()) + nutrition.getCHOLE().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Sodium: " + df.format(nutrition.getNA().getQuantity()) + nutrition.getNA().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Calcium: " + df.format(nutrition.getCA().getQuantity()) + nutrition.getCA().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Vitamin A: " + df.format(recipe.getTotalDaily().getVITA_RAE().getQuantity()) + recipe.getTotalDaily().getVITA_RAE().getUnit() + "\n");
        }   catch(NullPointerException e){}

        try {
            recipeNutrition.add("Vitamin C: " + df.format(recipe.getTotalDaily().getVITC().getQuantity()) + recipe.getTotalDaily().getVITC().getUnit());
        }   catch(NullPointerException e){}

        return recipeNutrition;
    }
}
