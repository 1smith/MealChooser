// ------------------------------------ DBADapter.java ---------------------------------------------

package com.example.mealchoser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_Breakfast = "breakfast";
	public static final String KEY_Lunch = "lunch";
	public static final String KEY_Dinner = "dinner";
	public static final String KEY_Dessert = "desssert";
	public static final String KEY_Lactose = "lactose";
	public static final String KEY_Vege = "vege";
	public static final String KEY_Gluten = "gluten";
	public static final String KEY_Peanut = "peanut";
	public static final String KEY_Title = "title";
	public static final String KEY_Difficulty = "difficulty";
	public static final String KEY_Recipe = "Recipe";
	
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_Breakfast = 1;
	public static final int COL_Lunch = 2;
	public static final int COL_Dinner = 3;
	public static final int COL_Dessert = 4;
	public static final int COL_Lactose = 5;
	public static final int COL_Vege = 6;
	public static final int COL_Gluten = 7;
	public static final int COL_Peanut = 8;
	public static final int COL_Title = 9;
	public static final int COL_Difficulty = 10;
	public static final int COL_Recipe = 11;

	public String s;
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_Breakfast, KEY_Lunch, KEY_Dinner, KEY_Dessert, KEY_Lactose, KEY_Vege, KEY_Gluten, KEY_Peanut, KEY_Title, KEY_Difficulty, KEY_Recipe};
	
	// DB info: it's name, and the table we are using (just one).
	public final static String DATABASE_NAME = "MyDb";
	public final static String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 16;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_Breakfast + " boolean not null, "
			+ KEY_Lunch + " boolean not null, "
			+ KEY_Dinner + " boolean not null,"
			+ KEY_Dessert + " boolean not null,"
			+ KEY_Lactose + " boolean not null,"
			+ KEY_Vege + " boolean not null,"
			+ KEY_Gluten + " boolean not null,"
			+ KEY_Peanut + " boolean not null,"
			+ KEY_Title + " varchar2(35) not null,"
			+ KEY_Difficulty + " integer not null,"
			+ KEY_Recipe + " varchar2(500) not null"
			
			// Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
			myDBHelper = new DatabaseHelper(context);
		
	}
	
	// Open the database connection.
	public DBAdapter open() {
		//db = context.getApplicationInfo().name + ""
		try {
					
		db = myDBHelper.getWritableDatabase();
		/*
		InputStream myinput = context.getAssets().open("MyDb");

	    // Path to the just created empty db
	    String outfilename = "data/data/com.example.mealchoser/databases/" + "MyDb";

	    //Open the empty db as the output stream
	    OutputStream myoutput;
	    myoutput = new FileOutputStream("data/data/com.example.mealchoser/databases/MyDb");


	    // transfer byte to inputfile to outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = myinput.read(buffer))>0)
	    {
	        myoutput.write(buffer,0,length);
	    }

	    //Close the streams
	    myoutput.flush();
	    myoutput.close();
	    myinput.close();
	    */
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();

	}
	
	public SQLiteDatabase getDB(){
		return db;
	}
	
	
	// Add a new set of values to the database.
	public long insertRow(boolean breakfast, boolean lunch, boolean dinner, boolean dessert, boolean lactose, boolean vege, boolean gluten, boolean peanut, String title, int difficulty, String recipe) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_Breakfast, breakfast);
		initialValues.put(KEY_Lunch, lunch);
		initialValues.put(KEY_Dinner, dinner);
		initialValues.put(KEY_Dessert, dessert);
		initialValues.put(KEY_Lactose, lactose);
		initialValues.put(KEY_Vege, vege);
		initialValues.put(KEY_Gluten, gluten);
		initialValues.put(KEY_Peanut, peanut);
		initialValues.put(KEY_Title, title);
		initialValues.put(KEY_Difficulty, difficulty);
		initialValues.put(KEY_Recipe, recipe);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor findRecipe(String breakfast, String lunch, String dinner, String dessert, String lactose, String vege, String gluten, String peanut){
		s = "Select _id, title, difficulty, recipe from mainTable ";
		if(breakfast.equals("1"))
			s += "WHERE breakfast = " + breakfast+ " ";
		else if(lunch.equals("1"))
			s += "WHERE lunch = " + lunch+ " ";
		else if(dinner.equals("1"))
			s += "WHERE Dinner = " + dinner+ " ";
		else if(dessert.equals("1"))
			s += "WHERE Desssert = " + dessert+ " ";
		
		if(lactose.equals("1"))
			s += "AND Lactose = " + lactose + " ";
		if(vege.equals("1"))
			s += "AND vege = " + vege+ " ";
		if(gluten.equals("1"))
			s += "AND Gluten = " + gluten+ " ";
		if(peanut.equals("1"))
			s += "AND Peanut = " + peanut+ " ";
		
		/*
		Cursor c =  db.rawQuery("Select _id, title, difficulty, recipe from mainTable "
				+ "WHERE breakfast = ? AND lunch = ? AND Dinner = ? AND Desssert = ? AND Lactose = ? "
				+ "AND vege = ? AND Gluten = ? AND Peanut = ?", 
				new String[] {breakfast, lunch, dinner, dessert, lactose, vege, gluten, peanut});
		*/
		Cursor c = db.rawQuery(s, null);
		
		
		return c;
	}
	
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, boolean breakfast, boolean lunch, boolean dinner, boolean dessert, boolean lactose, boolean vege, boolean gluten, boolean peanut, String title, int difficulty, String recipe) {
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_Breakfast, breakfast);
		newValues.put(KEY_Lunch, lunch);
		newValues.put(KEY_Dinner, dinner);
		newValues.put(KEY_Dessert, dessert);
		newValues.put(KEY_Lactose, lactose);
		newValues.put(KEY_Vege, vege);
		newValues.put(KEY_Gluten, gluten);
		newValues.put(KEY_Peanut, peanut);
		newValues.put(KEY_Title, title);
		newValues.put(KEY_Difficulty, difficulty);
		newValues.put(KEY_Recipe, recipe);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public Cursor checkSize(){
		Cursor c =  db.rawQuery("SELECT * FROM " + "mainTable", null);
		return c;
	}
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}




}
