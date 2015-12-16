package com.example.team3.whisk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**     File name: StoreAdapter.java
 *
 *      Adapter class that is responsible for outputting the list of stores and its information
 *      after parsing SuperMarket API.
 *
 *      @author Team 3
 *      @version 1.00
 */
public class StoreAdapter extends ArrayAdapter<Store> {

    public StoreAdapter(Context ctx, int textViewResourceId, List<Store> pList) {
        super(ctx, textViewResourceId, pList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        Log.i("xml", "getView pos = " + pos);
        if(null == row){
            //No recycled View, we have to inflate one.
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout)inflater.inflate(R.layout.activity_store_list, null);
        }

        //Get our View References
        TextView nameTxt = (TextView)row.findViewById(R.id.nameTxt);
        TextView aboutTxt = (TextView)row.findViewById(R.id.addressTxt);
        TextView idTxt = (TextView)row.findViewById(R.id.idTxt);
        TextView cityTxt = (TextView)row.findViewById(R.id.cityTxt);

        //Set the relavent text in our TextViews
        nameTxt.setText(getItem(pos).getStorename());
        aboutTxt.setText(getItem(pos).getAddress());
        idTxt.setText(getItem(pos).getItemName());
        cityTxt.setText(getItem(pos).getCity());

        return row;
    }
}
