package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.net.URI;
import android.text.format.Time;
import android.widget.ToggleButton;

import com.example.nomorenagginginator.R;

/**
 * Created by srish on 08-03-2016.
 */
public class ChildActivity extends FragmentActivity implements DatePickerDialog.SaveDateListener {

    private ImageView image;
    private static final int RESULT_LOAD_IMAGE=1;
    private Child currentChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

		initDestructButton();
		initChildrenButton();
		initChoresButton();
        initUploadButton();
        initSaveButton();
        initToggleButton();
        initTextChangedEvents();
        initChangeDateButton();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        	initChild(extras.getInt("childid"));
        }
        else {
            currentChild = new Child();
        }
        setForEditing(false);
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
				ChildDataSource ds = new ChildDataSource(ChildActivity.this);
				ds.open();

				boolean wasSuccessful = false;
				if (currentChild.getChildID()==-1) {
					wasSuccessful = ds.insertChild(currentChild);
					int newId = ds.getLastChildId();
					currentChild.setChildID(newId);
				}
				else {
					wasSuccessful = ds.updateChild(currentChild);
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
		final EditText childName = (EditText) findViewById(R.id.editName);
        childName.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentChild.setChildName(childName.getText().toString());
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

		final EditText phoneNumber = (EditText) findViewById(R.id.editPhone);
		phoneNumber.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentChild.setPhoneNumber(phoneNumber.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}
		});

		phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
	}

	private void setForEditing(boolean enabled) {
		EditText editName = (EditText) findViewById(R.id.editName);
		EditText editPhone = (EditText) findViewById(R.id.editPhone);

        Button buttonUpload = (Button) findViewById(R.id.btnUpload);
		Button buttonChange = (Button) findViewById(R.id.btnBirthday);
		Button buttonSave = (Button) findViewById(R.id.buttonSave);

		editName.setEnabled(enabled);
		editPhone.setEnabled(enabled);
        buttonUpload.setEnabled(enabled);
		buttonChange.setEnabled(enabled);
		buttonSave.setEnabled(enabled);

		if (enabled) {
			editName.requestFocus();
		}
		else {
			ScrollView s = (ScrollView) findViewById(R.id.scrollView1);
			s.fullScroll(ScrollView.FOCUS_UP);
			s.clearFocus();
		}
	}

	private void initChangeDateButton() {
		Button changeDate = (Button) findViewById(R.id.btnBirthday);
		changeDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
		    	FragmentManager fm = getSupportFragmentManager();
		        DatePickerDialog datePickerDialog = new DatePickerDialog();
		        datePickerDialog.show(fm, "DatePick");
			}
		});
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		EditText editName = (EditText) findViewById(R.id.editName);
		imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
		EditText editPhone = (EditText) findViewById(R.id.editPhone);
		imm.hideSoftInputFromWindow(editPhone.getWindowToken(), 0);
	}

	private void initChild(int id) {

		ChildDataSource ds = new ChildDataSource(ChildActivity.this);

		ds.open();
		currentChild = ds.getSpecificChild(id);
		ds.close();

		EditText editName = (EditText) findViewById(R.id.editName);
		EditText editPhone = (EditText) findViewById(R.id.editPhone);
		ImageView imageView = (ImageView)findViewById(R.id.childImage);
		TextView birthDay = (TextView) findViewById(R.id.textBirthday);

		editName.setText(currentChild.getChildName());
		editPhone.setText(currentChild.getPhoneNumber());
        imageView.setImageBitmap(currentChild.getPicture());
		birthDay.setText(DateFormat.format("MM/dd/yyyy", currentChild.getBirthday().toMillis(false)).toString());
	}

	@Override
	public void didFinishDatePickerDialog(Time selectedTime) {
		TextView birthDay = (TextView) findViewById(R.id.textBirthday);
		birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime.toMillis(false)).toString());
	}

    private void initUploadButton() {
        //image = (ImageView)findViewById(R.id.childImage);
        Button imgbtn = (Button) findViewById(R.id.btnUpload);
        imgbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
				//Bitmap img = ((BitmapDrawable)image.getDrawable()).getBitmap();
			}
		});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
		image = (ImageView)findViewById(R.id.childImage);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode== RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
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
				Intent intent = new Intent(ChildActivity.this, ChildrenListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void initChoresButton() {
		ImageButton list = (ImageButton) findViewById(R.id.imageButtonChores);
		list.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChildActivity.this, ChoresListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
}
