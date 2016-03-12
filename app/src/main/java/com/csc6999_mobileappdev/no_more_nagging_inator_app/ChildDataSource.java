package com.csc6999_mobileappdev.no_more_nagging_inator_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import android.database.SQLException;
import java.util.ArrayList;
import android.text.format.Time;

/**
 * Created by srish on 07-03-2016.
 */
@SuppressWarnings("deprecation")
public class ChildDataSource {
    private SQLiteDatabase database;
	private DBHelper dbHelper;

	public ChildDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean insertChild(Child c)
	{
		boolean didSucceed = false;
		try {
			ContentValues initialValues = new ContentValues();

			initialValues.put("childname", c.getChildName());
			initialValues.put("phonenumber", c.getPhoneNumber());

            // Storing image in database
            Bitmap img =c.getPicture();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

			if(img != null	) img.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            //String encodedImage = Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT);
            //initialValues.put("picture", encodedImage);

            initialValues.put("image", byteArray.toByteArray());
			initialValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));

			didSucceed = database.insert("children", null, initialValues) > 0;

		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	public boolean updateChild(Child c)
	{
		boolean didSucceed = false;
		try {
			Long rowId = Long.valueOf(c.getChildID());
			ContentValues updateValues = new ContentValues();

			updateValues.put("childname", c.getChildName());
			updateValues.put("phonenumber", c.getPhoneNumber());

            // updating image in database
            Bitmap img =c.getPicture();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            updateValues.put("image", byteArray.toByteArray());

			updateValues.put("birthday", String.valueOf(c.getBirthday().toMillis(false)));

			didSucceed = database.update("children", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	public int getLastChildId() {
		int lastId = -1;
		try
		{
			String query = "Select MAX(_id) from children";
			Cursor cursor = database.rawQuery(query, null);

			cursor.moveToFirst();
			lastId = cursor.getInt(0);
			cursor.close();
		}
		catch (Exception e) {
			lastId = -1;
		}
		return lastId;
	}

	public ArrayList<String> getChildName() {
		ArrayList<String> childNames = new ArrayList<String>();
		try {
			String query = "Select childname from children";
			Cursor cursor = database.rawQuery(query, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

                childNames.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
            childNames = new ArrayList<String>();
		}
		return childNames;
	}

	public ArrayList<Child> getChildren() {
		ArrayList<Child> contacts = new ArrayList<Child>();
		try {
			String query = "SELECT  * FROM children";
			Cursor cursor = database.rawQuery(query, null);

			Child newChild;
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
                newChild = new Child();
                newChild.setChildID(cursor.getInt(0));
                newChild.setChildName(cursor.getString(1));
                newChild.setPhoneNumber(cursor.getString(2));

                byte[] img=cursor.getBlob(3);
                Bitmap bitmapImg= BitmapFactory.decodeByteArray(img, 0, img.length);
                newChild.setPicture(bitmapImg);

				Time t = new Time();
				t.set(Long.valueOf(cursor.getString(4)));
                newChild.setBirthday(t);

				contacts.add(newChild);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			contacts = new ArrayList<Child>();
		}
		return contacts;
	}

	public Child getSpecificChild(int childId) {
		Child child = new Child();

			String query = "SELECT  * FROM children WHERE _id =" + childId;
			Cursor cursor = database.rawQuery(query, null);

		if (cursor.moveToFirst()) {
            child.setChildID(cursor.getInt(0));
            child.setChildName(cursor.getString(1));
            child.setPhoneNumber(cursor.getString(2));

            byte[] img=cursor.getBlob(3);
            Bitmap bitmapImg= BitmapFactory.decodeByteArray(img, 0, img.length);
            child.setPicture(bitmapImg);

			Time t = new Time();
			t.set(Long.valueOf(cursor.getString(4)));
            child.setBirthday(t);
        }
		cursor.close();

		return child;
	}

	public boolean deleteChild(int childId) {
		boolean didDelete = false;
		try {
			didDelete = database.delete("children", "_id=" + childId, null) > 0;
		}
		catch (Exception e) {
			//Do nothing -return value already set to false
		}
		return didDelete;
	}
}
