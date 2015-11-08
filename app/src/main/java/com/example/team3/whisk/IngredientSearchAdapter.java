package com.example.team3.whisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class IngredientSearchAdapter extends BaseAdapter
{
    private List<IngredientSearchResponse.HitsEntity> rItem;
    private Context rContext;

    public IngredientSearchAdapter(List<IngredientSearchResponse.HitsEntity> rItem, Context rContext)
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
        IngredientSearchResponse.HitsEntity item = (IngredientSearchResponse.HitsEntity) getItem(position);

        TextView ingredients = (TextView) rowView.findViewById(R.id.brandTitle);
        ingredients.setText(item.getFields().getBrand_name()+" "+item.getFields().getItem_name());
        return rowView;
    }
}
