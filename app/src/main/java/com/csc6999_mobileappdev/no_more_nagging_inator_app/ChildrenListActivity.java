package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nomorenagginginator.R;

import java.util.ArrayList;

/**
 * Created by srish on 05-03-2016.
 */
public class ChildrenListActivity extends ListActivity{

	ChildAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childrenlist);

        initDestructButton();
        initChildrenButton();
        initChoresButton();
        initAddChildButton();
    }

    @Override
    public void onResume() {
        super.onResume();

        ChildDataSource ds = new ChildDataSource(this);
        ds.open();
        final ArrayList<Child> children = ds.getChildren();
        ds.close();

        if (children.size() > 0) {

            adapter = new ChildAdapter(this, children);
            setListAdapter(adapter);
            ListView listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked,
                                        int position, long id) {
                    Child selectedChild = children.get(position);

                        Intent intent = new Intent(ChildrenListActivity.this, ChildActivity.class);
                        intent.putExtra("childid", selectedChild.getChildID());
                        startActivity(intent);
                }
            });
        }
        else {
            Intent intent = new Intent(ChildrenListActivity.this, ChildActivity.class);
            startActivity(intent);
        }
    }

    private void initDestructButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonDestruct);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri packageUri = Uri.parse("package:com.csc6999_mobileappdev.no_more_nagging_inator_app");
                Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                startActivity(uninstallIntent);
            }
        });
    }

    private void initChildrenButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonChildren);
        list.setEnabled(false);
    }

    private void initChoresButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonChores);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenListActivity.this, ChoresListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initAddChildButton() {
		Button newChild = (Button) findViewById(R.id.buttonAdd);
        newChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(ChildrenListActivity.this, ChildActivity.class);
    			startActivity(intent);
            }
        });
	}
}
