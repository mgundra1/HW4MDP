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

public class ChildAdapter extends ArrayAdapter<Child> {

    private ArrayList<Child> children;
    private Context adapterContext;

    public ChildAdapter(Context context, ArrayList<Child> items) {
            super(context, R.layout.child_list_item, items);
            adapterContext = context;
            this.children = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
    	try {
            Child child = children.get(position);

            if (v == null) {
            		LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            		v = vi.inflate(R.layout.child_list_item, null);
            }

            TextView childName = (TextView) v.findViewById(R.id.textChildName);
            TextView phoneNumber = (TextView) v.findViewById(R.id.textPhoneNumber);

        	childName.setText(child.getChildName());
            phoneNumber.setText(child.getPhoneNumber());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		e.getCause();
    	}
            return v;
    }
}

