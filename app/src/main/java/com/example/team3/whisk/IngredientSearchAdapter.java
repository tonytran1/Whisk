package com.example.team3.whisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;


/**     File name: IngredientSearchAdapter.java
 *
 *      This class provides an adapter for showing a list from the Nutritionix API response.
 *
 *      The response contains a list of similar ingredients that the user may like to see nutritional
 *      information on.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class IngredientSearchAdapter extends BaseAdapter
{
    private List<IngredientSearchPOJO.HitsEntity> rItem;
    private Context rContext;

    public IngredientSearchAdapter(List<IngredientSearchPOJO.HitsEntity> rItem, Context rContext)
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
        View rowView = inflater.inflate(R.layout.ingredient_entity, parent, false);

        TableLayout hide = (TableLayout) rowView.findViewById(R.id.nutritionList);
        hide.setVisibility(View.GONE);

        IngredientSearchPOJO.HitsEntity item = (IngredientSearchPOJO.HitsEntity) getItem(position);

        TextView ingredients = (TextView) rowView.findViewById(R.id.brandTitle);
        ingredients.setText(item.getFields().getBrand_name()+" "+item.getFields().getItem_name());
        return rowView;
    }
}
