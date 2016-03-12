package com.csc6999_mobileappdev.no_more_nagging_inator_app;

/**
 * Created by srish on 08-03-2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

import com.example.nomorenagginginator.R;

public class HomePageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        initDestructButton();
        initChildrenButton();
        initChoresButton();
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
                Intent intent = new Intent(HomePageActivity.this, ChildrenListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initChoresButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonChores);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ChoresListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}

