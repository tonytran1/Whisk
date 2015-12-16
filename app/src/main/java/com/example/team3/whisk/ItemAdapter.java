package com.example.team3.whisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**     File name: ItemAdapter.java
 *
 *      Adapter class that is responsible for holding the list of sites after they
 *      get parsed out of XML and building row views to display them on the screen.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class ItemAdapter extends BaseAdapter {

    private ArrayList<String> rItem;
    private Context rContext;

    public ItemAdapter(ArrayList<String> rItem, Context rContext)
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
        View rowView = inflater.inflate(R.layout.activity_item_list, parent, false);

        TextView nameTxt = (TextView) rowView.findViewById(R.id.nameTxt);
        nameTxt.setText(rItem.get(position));
        return rowView;
    }
}
