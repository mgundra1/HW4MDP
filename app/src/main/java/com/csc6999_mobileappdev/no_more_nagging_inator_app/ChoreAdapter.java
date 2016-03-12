package com.csc6999_mobileappdev.no_more_nagging_inator_app;

/**
 * Created by srish on 07-03-2016.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nomorenagginginator.R;

public class ChoreAdapter extends ArrayAdapter<Chore> {

    private ArrayList<Chore> chores;
    private Context adapterContext;

    public ChoreAdapter(Context context, ArrayList<Chore> items) {
            super(context, R.layout.chore_list_item, items);
            adapterContext = context;
            this.chores = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
    	try {
            Chore chore = chores.get(position);

            if (v == null) {
            		LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            		v = vi.inflate(R.layout.chore_list_item, null);
            }

            TextView choreName = (TextView) v.findViewById(R.id.textChoreName);
            TextView repetition = (TextView) v.findViewById(R.id.textRepetition);

        	choreName.setText(chore.getChoreName());
            repetition.setText(chore.getRepetition());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		e.getCause();
    	}
            return v;
    }
}

