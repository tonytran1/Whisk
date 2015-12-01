package com.example.team3.whisk;

/**
 * Created by Tony on 11/10/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NutritionAdapter extends BaseAdapter
{
    private ArrayList<IngredientNutritionResponse> rItem;
    private Context rContext;

    public NutritionAdapter(ArrayList<IngredientNutritionResponse> rItem, Context rContext)
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
        View rowView = inflater.inflate(R.layout.content_output_nutrition, parent, false);

        IngredientNutritionResponse item = (IngredientNutritionResponse) getItem(position);

        TextView title = (TextView) rowView.findViewById(R.id.NutritionTitle);
        title.setText(item.getItem_name());


        TextView fat = (TextView) rowView.findViewById(R.id.fat);
        try {
            fat.setText(item.getNf_total_fat() + "g");
        } catch (NullPointerException e) {
            fat.setText("N/A");
        }

        TextView sugar = (TextView) rowView.findViewById(R.id.sugar);
        try {
            sugar.setText(item.getNf_sugars() + "g");
        } catch (NullPointerException e) {
            sugar.setText("N/A");
        }

        TextView carb = (TextView) rowView.findViewById(R.id.carbs);
        try {
            carb.setText(item.getNf_total_carbohydrate() + "g");
        } catch (NullPointerException e) {
            carb.setText("N/A");
        }

        TextView fiber = (TextView) rowView.findViewById(R.id.fiber);
        try {
            fiber.setText(item.getNf_dietary_fiber() + "g");
        } catch (NullPointerException e) {
            fiber.setText("N/A");
        }

        TextView protein = (TextView) rowView.findViewById(R.id.protein);
        try {
            protein.setText(item.getNf_protein() + "g");
        } catch (NullPointerException e) {
            protein.setText("N/A");
        }

        TextView cholest = (TextView) rowView.findViewById(R.id.cholesterol);
        try {
            cholest.setText(item.getNf_cholesterol() + "mg");
        } catch (NullPointerException e) {
            cholest.setText("N/A");
        }

        TextView sodium = (TextView) rowView.findViewById(R.id.sodium);
        try {
            sodium.setText(item.getNf_sodium() + "mg");
        } catch (NullPointerException e) {
            sodium.setText("N/A");
        }

        TextView calcium = (TextView) rowView.findViewById(R.id.calcium);
        try {
            calcium.setText(item.getNf_calcium_dv() + "%");
        } catch (NullPointerException e) {
            calcium.setText("N/A");
        }

        TextView vitA = (TextView) rowView.findViewById(R.id.vitA);
        try {
            vitA.setText(item.getNf_vitamin_a_dv() + "%");
        } catch (NullPointerException e) {
            vitA.setText("N/A");
        }

        TextView vitC = (TextView) rowView.findViewById(R.id.vitC);
        try {
            vitC.setText(item.getNf_vitamin_c_dv() + "%");
        } catch (NullPointerException e) {
            vitC.setText("N/A");
        }

        return rowView;
    }

}

