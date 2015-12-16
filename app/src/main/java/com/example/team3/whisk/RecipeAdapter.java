package com.example.team3.whisk;

import android.content.Context;
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

/**     File name: RecipeAdapter.java
 *
 *      This class provides an adapter for formatting the output of the recipe list obtained
 *      after searching from the home screen.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class RecipeAdapter extends BaseAdapter
{
    private List<EdamamPOJO.HitsEntity> rItem;
    private Context rContext;

    public RecipeAdapter(List<EdamamPOJO.HitsEntity> rItem, Context rContext)
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
        EdamamPOJO.HitsEntity item = (EdamamPOJO.HitsEntity) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
        Picasso.with(rContext).load(item.getRecipe().getImage()).into(thumbnail);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(item.getRecipe().getLabel());

        String serv = "Servings: "+ (int) (item.getRecipe().getYield());
        int cal =  (int) (item.getRecipe().getCalories()/item.getRecipe().getYield());

        TextView calories = (TextView) rowView.findViewById(R.id.calories);
        calories.setText("Calories: " + cal);

        TextView servings = (TextView) rowView.findViewById(R.id.servings);
        servings.setText(serv);

        return rowView;
    }

}
