package com.csc6999_mobileappdev.no_more_nagging_inator_app;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by srish on 07-03-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "nomorenagginginator_app.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String CREATE_TABLE_CHILDREN =
          "create table children (_id integer primary key autoincrement, "
                  + "childname text not null, "
                  + "phonenumber text, "
				  + "image blob, "
                  + "birthday text);";

    private static final String CREATE_TABLE_CHORES =
            "create table chores (_id integer primary key autoincrement, "
                    + "chorename text not null, "
                    + "duration text, "
                    + "repetition text, "
                    + "child_name text not null); ";

/*    private static final String CREATE_TABLE_CHORES =
            "create table chores (_id integer primary key autoincrement, "
                    + "chorename text not null, "
					+ "duration integer, "
                    + "repitition text), "
                    + "childid integer, "
					+ "FOREIGN KEY (childid) REFERENCES children(_id);";*/

	  public DBHelper(Context context) {
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		    database.execSQL(CREATE_TABLE_CHILDREN);
          database.execSQL(CREATE_TABLE_CHORES);

          Log.v("version", "Version number is"+database.getVersion());
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(DBHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
		  db.execSQL("DROP TABLE IF EXISTS children");
		  db.execSQL("DROP TABLE IF EXISTS chores");
	    onCreate(db);
	  }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}

