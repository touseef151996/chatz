package com.google.firebase.app.chatz;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by touseef on 1/29/2018.
 */

public class NameAdapter extends ArrayAdapter<Names> {
    private int bgcolor;
public NameAdapter(Context context, ArrayList<Names> objects,int bgcolor){
    super(context,0,objects);
    this.bgcolor=bgcolor;
}
 @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //changing data in the word objects displaying in the listview
        Names w=getItem(position);
        TextView t1= (TextView) listItemView.findViewById(R.id.english);
        t1.setText(w.getS1());
        TextView t2=(TextView)listItemView.findViewById(R.id.hindi);
        t2.setText(w.getS2());
        ImageView v=(ImageView) listItemView.findViewById(R.id.list_item_icon);
        v.setImageResource(w.getImageResourceId());
        View container=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),bgcolor);
        container.setBackgroundColor(color);
        return listItemView;
    }
}
