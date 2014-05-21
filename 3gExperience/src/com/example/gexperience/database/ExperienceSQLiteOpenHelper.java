package com.example.gexperience.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExperienceSQLiteOpenHelper extends SQLiteOpenHelper {
		
	private static final String TAG = "DataBaseUtil";
	private static final String DATABASE_NAME = "3g_experience";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_AREAS = "areas";	
	public static final String TABLE_LOCATIONS = "location";
	public static final String TABLE_PROMOTERS = "promoters";
	public static final String TABLE_OCCUPATIONS = "occupations";
	public static final String TABLE_PACKAGES = "packages";
	public static final String TABLE_MOBILE_BRANDS = "mobile_brands";
		
	
	/****** Following are column names *********/
	public static final String AREA_ID = "area_id";
	public static final String AREA_TITLE = "title";
	
	public static final String LOCATION_ID = "location_id";
	public static final String LOCATION_AREA_ID = "area_id";
	public static final String LOCATION_TEAM_ID = "team_id";
	public static final String LOCATION_TITLE = "title";
	
	public static final String PROMOTER_ID = "promoter_id";
	public static final String PROMOTER_TEAM_ID = "team_id";
	public static final String PROMOTER_TEAM_NAME = "team_name";
	public static final String PROMOTER_PROMOTER_NAME = "promoter_name";
	public static final String PROMOTER_CODE = "promoter_code";
	
	public static final String OCCUPATION_ID = "occupation_id";
	public static final String OCCUPATION_TITLE = "title";
	
	public static final String PACKAGE_ID = "package_id";
	public static final String PACKAGE_TITLE = "title";
	
	public static final String MOBILE_BRAND_ID = "mobile_brand_id";
	public static final String MOBILE_BRAND_TITLE = "title";	
	
	
//	private static final String SETTINGS_ID = "id";
//	private static final String SETTINGS_VARIABLE_NAME = "variable_name";
//	private static final String SETTINGS_VARIABLE_VALUE = "variable_value";	
//	
//	private static final String CREATE_TABLE_SETTINGS = "create table "+ TABLE_SETTINGS +
//			" ( "+ SETTINGS_ID+" integer, "+ SETTINGS_VARIABLE_NAME+" text not null,"+
//			SETTINGS_VARIABLE_VALUE+ " text not null);";
	
	private static final String CREATE_TABLE_AREAS = "create table "+ TABLE_AREAS +
			" ("+AREA_ID+" INTEGER PRIMARY KEY, "+AREA_TITLE+" text not null);";
	
	private static final String CREATE_TABLE_LOCATIONS = "create table "+ TABLE_LOCATIONS +
			" ("+LOCATION_ID+" INTEGER PRIMARY KEY, "+LOCATION_AREA_ID+"" +
					" INTEGER NOT NULL, "+LOCATION_TEAM_ID+
					" INTEGER NOT NULL, "+LOCATION_TITLE+" text not null);";
	
	private static final String CREATE_TABLE_PROMOTERS = "create table "+ TABLE_PROMOTERS +
			" ("+PROMOTER_ID+" INTEGER PRIMARY KEY, "+PROMOTER_TEAM_ID+
			" INTEGER NOT NULL, "+PROMOTER_TEAM_NAME+" TEXT NOT NULL,"+
			PROMOTER_PROMOTER_NAME+" text not null, "+PROMOTER_CODE+
			"text not null);";
	
	private static final String CREATE_TABLE_OCCUPATIONS = "create table "+ TABLE_OCCUPATIONS +
			" ("+OCCUPATION_ID+" INTEGER PRIMARY KEY, "+OCCUPATION_TITLE+" text not null);";
	
	private static final String CREATE_TABLE_PACKAGES = "create table "+ TABLE_PACKAGES +
			" ("+PACKAGE_ID+" INTEGER PRIMARY KEY, "+PACKAGE_TITLE+" text not null);";
	
	private static final String CREATE_TABLE_MOBILE_BRANDS = "create table "+ TABLE_MOBILE_BRANDS +
			" ("+MOBILE_BRAND_ID+" INTEGER PRIMARY KEY, "+MOBILE_BRAND_TITLE+" text not null);";
	

	//constructor
	public ExperienceSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_AREAS);
		db.execSQL(CREATE_TABLE_LOCATIONS);
		db.execSQL(CREATE_TABLE_PROMOTERS);
		db.execSQL(CREATE_TABLE_OCCUPATIONS);
		db.execSQL(CREATE_TABLE_PACKAGES);
		db.execSQL(CREATE_TABLE_MOBILE_BRANDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
