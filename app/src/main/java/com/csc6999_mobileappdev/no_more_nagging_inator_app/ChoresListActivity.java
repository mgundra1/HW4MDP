package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nomorenagginginator.R;

import java.util.ArrayList;

/**
 * Created by srish on 08-03-2016.
 */
public class ChoresListActivity extends ListActivity{

    ChoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        initDestructButton();
        initChildrenButton();
        initChoresButton();
        initAddChoreButton();
    }

    @Override
    public void onResume() {
        super.onResume();

        ChoreDataSource ds = new ChoreDataSource(this);
        ds.open();
        final ArrayList<Chore> chores = ds.getChores();
        ds.close();

        if (chores.size() > 0) {
            adapter = new ChoreAdapter(this, chores);
            setListAdapter(adapter);
            ListView listView = getListView();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked,
                                        int position, long id) {
                    Chore selectedChore = chores.get(position);
                    Intent intent = new Intent(ChoresListActivity.this, ChoreActivity.class);
                    intent.putExtra("choreid", selectedChore.getChoreID());
                    startActivity(intent);
                }
        });
        }
        else {
            Intent intent = new Intent(ChoresListActivity.this, ChoreActivity.class);
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
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoresListActivity.this, ChildrenListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initChoresButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonChores);
        list.setEnabled(false);
    }

    private void initAddChoreButton() {
        Button newChore = (Button) findViewById(R.id.buttonAdd);
        newChore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoresListActivity.this, ChoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
