package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nomorenagginginator.R;

/**
 * Created by srish on 08-03-2016.
 */
public class ChoreActivity extends FragmentActivity{
    private Chore currentChore;
    private Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore);

		initDestructButton();
		initChildrenButton();
		initChoresButton();
        addItemsOnSpinner1();

        initSaveButton();
        initToggleButton();
        initTextChangedEvents();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        	initChore(extras.getInt("choreid"));
        }
        else {
            currentChore = new Chore();
        }

		addListenerOnSpinnerItemSelection();
        setForEditing(false);
    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        ChildDataSource ds = new ChildDataSource(this);
        ds.open();
        final ArrayList<Child> children = ds.getChildren();
        ds.close();

        List<String> list = new ArrayList<String>();

        for (Child child: children)
            list.add(child.getChildName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (this,
                    android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener(spinner1, currentChore));

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener(spinner2, currentChore));
    }

    private void initToggleButton() {
		final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
		editToggle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setForEditing(editToggle.isChecked());
			}

		});
	}

	private void initSaveButton() {
		Button saveButton = (Button) findViewById(R.id.buttonSave);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard();
				ChoreDataSource ds = new ChoreDataSource(ChoreActivity.this);
				ds.open();

				boolean wasSuccessful = false;
				if (currentChore.getChoreID() == -1) {
					wasSuccessful = ds.insertChore(currentChore);
					int newId = ds.getLastChoreId();
					currentChore.setChoreID(newId);
				} else {
					wasSuccessful = ds.updateChore(currentChore);
				}
				ds.close();

				if (wasSuccessful) {
					ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
					editToggle.toggle();
					setForEditing(false);
				}
			}
		});
	}

	private void initTextChangedEvents(){
		final EditText choreName = (EditText) findViewById(R.id.editName);
        choreName.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentChore.setChoreName(choreName.getText().toString());
			}

			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
				//  Auto-generated method stub

			}

			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				//  Auto-generated method stub
			}
		});

		final EditText duration = (EditText) findViewById(R.id.editDuration);
		duration.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                currentChore.setDuration(duration.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                //  Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //  Auto-generated method stub
            }
        });

	}

	private void setForEditing(boolean enabled) {
		EditText editName = (EditText) findViewById(R.id.editName);
		EditText editDuration = (EditText) findViewById(R.id.editDuration);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

		Button buttonSave = (Button) findViewById(R.id.buttonSave);

		editName.setEnabled(enabled);
		editDuration.setEnabled(enabled);
		spinner1.setEnabled(enabled);
        spinner2.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

		if (enabled) {
			editName.requestFocus();
		}
		else {
			ScrollView s = (ScrollView) findViewById(R.id.scrollView3);
			s.fullScroll(ScrollView.FOCUS_UP);
			s.clearFocus();
		}
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText editName = (EditText) findViewById(R.id.editName);
		imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
		EditText editDuration = (EditText) findViewById(R.id.editDuration);
		imm.hideSoftInputFromWindow(editDuration.getWindowToken(), 0);
	}

	private void initChore(int id) {

		ChoreDataSource ds = new ChoreDataSource(ChoreActivity.this);

		ds.open();
		currentChore = ds.getSpecificChore(id);
		ds.close();

		EditText editName = (EditText) findViewById(R.id.editName);
		EditText editDuration = (EditText) findViewById(R.id.editDuration);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

		editName.setText(currentChore.getChoreName());
		editDuration.setText(currentChore.getDuration());
        spinner1.setSelection(getIndex(spinner1, currentChore.getAssignedChildName()));
        spinner2.setSelection(getIndex(spinner1, currentChore.getRepetition()));
	}

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
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
				Intent intent = new Intent(ChoreActivity.this, ChildrenListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void initChoresButton() {
		ImageButton list = (ImageButton) findViewById(R.id.imageButtonChores);
		list.setEnabled(false);
		/*list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ChoreActivity.this, ChoresListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
	}
}

