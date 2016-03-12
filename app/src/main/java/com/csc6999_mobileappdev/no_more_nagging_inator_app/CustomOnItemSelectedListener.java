package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.example.nomorenagginginator.R;

/**
 * Created by srish on 10-03-2016.
 */

 public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Chore currentChore;

    public CustomOnItemSelectedListener(Spinner spinner, Chore currentChore)
    {
        this.spinner = spinner;
        this.currentChore = currentChore;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
    {

        switch (spinner.getId())
        {
            case R.id.spinner1:
                currentChore.setAssignedChildName(String.valueOf(spinner.getSelectedItem()));
                break;
            case R.id.spinner2:
                currentChore.setRepetition(String.valueOf(spinner.getSelectedItem()));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}