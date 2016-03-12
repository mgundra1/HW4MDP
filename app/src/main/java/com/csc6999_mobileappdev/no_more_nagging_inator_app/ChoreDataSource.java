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
public class ChoreDataSource {
    private SQLiteDatabase database;
	private DBHelper dbHelper;

	public ChoreDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean insertChore(Chore c)
	{
		boolean didSucceed = false;
		try {
			ContentValues initialValues = new ContentValues();

			initialValues.put("chorename", c.getChoreName());
			initialValues.put("duration", c.getDuration());
            initialValues.put("repetition", c.getRepetition());
			initialValues.put("child_name", c.getDuration());

			didSucceed = database.insert("chores", null, initialValues) > 0;

		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	public boolean updateChore(Chore c)
	{
		boolean didSucceed = false;
		try {
			Long rowId = Long.valueOf(c.getChoreID());
			ContentValues updateValues = new ContentValues();

			updateValues.put("chorename", c.getChoreName());
			updateValues.put("duration", c.getDuration());
			updateValues.put("repetition", c.getRepetition());
			updateValues.put("child_name", c.getAssignedChildName());

			didSucceed = database.update("chores", updateValues, "_id=" + rowId, null) > 0;
		}
		catch (Exception e) {
			//Do Nothing -will return false if there is an exception
		}
		return didSucceed;
	}

	public int getLastChoreId() {
		int lastId = -1;
		try
		{
			String query = "Select MAX(_id) from chores";
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

	public ArrayList<String> getChoreName() {
		ArrayList<String> choresNames = new ArrayList<String>();
		try {
			String query = "Select chorename from chores";
			Cursor cursor = database.rawQuery(query, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

                choresNames.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
            choresNames = new ArrayList<String>();
		}
		return choresNames;
	}

	public ArrayList<Chore> getChores() {
		ArrayList<Chore> chores = new ArrayList<Chore>();
		try {
			String query = "SELECT  * FROM chores";
			Cursor cursor = database.rawQuery(query, null);

			Chore newChore;
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				newChore = new Chore();
				newChore.setChoreID(cursor.getInt(0));
				newChore.setChoreName(cursor.getString(1));
				newChore.setDuration(cursor.getString(2));
				newChore.setRepetition(cursor.getString(3));
				newChore.setAssignedChildName(cursor.getString(4));

				chores.add(newChore);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			chores = new ArrayList<Chore>();
		}
		return chores;
	}

	public Chore getSpecificChore(int choreId) {
		Chore chore = new Chore();

			String query = "SELECT  * FROM chores WHERE _id =" + choreId;
			Cursor cursor = database.rawQuery(query, null);

		if (cursor.moveToFirst()) {
            chore.setChoreID(cursor.getInt(0));
            chore.setChoreName(cursor.getString(1));
            chore.setDuration(cursor.getString(2));
            chore.setRepetition(cursor.getString(3));
            chore.setAssignedChildName(cursor.getString(4));
        }
		cursor.close();

		return chore;
	}
}
