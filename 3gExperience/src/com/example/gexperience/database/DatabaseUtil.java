package com.example.gexperience.database;

import java.util.ArrayList;
import java.util.List;

import com.example.gexperience.datamodel.Occupation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseUtil {
	
	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;
	Context context;

	public DatabaseUtil(Context ctx) {
		dbHelper = new ExperienceSQLiteOpenHelper(ctx);
		context = ctx;
	}
	
	public void open(){
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Whether the data already fetched from server or not
	 * @return
	 */
	public boolean isDbSynchronizedByServerData(){
		Cursor cursor = database.rawQuery("SELECT * FROM "+  ExperienceSQLiteOpenHelper.TABLE_AREAS, null);
		if( cursor!=null && cursor.getCount()>0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * @desc Deletes all table contents for database sync with backend. This is usually needed at the
	 * beginning of a month.
	 */
	public void deleteTableContentsForDbSync(){
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_AREAS);
		database.execSQL("VACUUM");
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_LOCATIONS);
		database.execSQL("VACUUM");
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_PROMOTERS);
		database.execSQL("VACUUM");
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS);
		database.execSQL("VACUUM");
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_PACKAGES);
		database.execSQL("VACUUM");
		database.execSQL("DELETE FROM " + ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS);
		database.execSQL("VACUUM");
	}
	
	
	
	public boolean saveArea(int id, String title){
		ContentValues cv = new ContentValues();
		cv.put("area_id", id);
		cv.put("title", title);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_AREAS, null, cv)>0 ){
			return true;
		}
		return false;
	}
	
	public boolean saveLocation(int id, int areaId, int teamId, String title){
		ContentValues cv = new ContentValues();
		cv.put("location_id", id);
		cv.put("area_id", areaId);
		cv.put("team_id", teamId);
		cv.put("title", title);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_LOCATIONS, null, cv)>0 ){
			return true;
		}
		return false;
	}
	
	public boolean savePromoter(int id, int teamId, String teamName, String promoName, String promoCode){
		ContentValues cv = new ContentValues();
		cv.put("promoter_id", id);
		cv.put("team_id", teamId);
		cv.put("team_name", teamName);
		cv.put("promoter_name", promoName);
		cv.put("promoter_code", promoCode);
		//Log.i("Promo Codeeeeeee",promoCode);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_PROMOTERS, null, cv)>0 ){
			return true;
		}
		return false;
	}
	
	public boolean saveOccupation(int id, String title){
		ContentValues cv = new ContentValues();
		cv.put("occupation_id", id);
		cv.put("title", title);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS, null, cv)>0 ){
			return true;
		}
		return false;
	}
	
	public boolean savePackage(int id, String title){
		ContentValues cv = new ContentValues();
		cv.put("package_id", id);
		cv.put("title", title);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_PACKAGES, null, cv)>0 ){
			return true;
		}
		return false;
	}
	
	public boolean saveMobileBrand(int id, String title){
		ContentValues cv = new ContentValues();
		cv.put("mobile_brand_id", id);
		cv.put("title", title);
		if( database.insert(ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS, null, cv)>0 ){
			return true;
		}
		return false;
	}

	public List<String> getSpinnerItems(String tblName){
		List<String> titles = new ArrayList<String>();
		String qry = "SELECT ";
		
		if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS) ){
				qry += ExperienceSQLiteOpenHelper.OCCUPATION_TITLE+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS;
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_PACKAGES)){
				qry += ExperienceSQLiteOpenHelper.PACKAGE_TITLE+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_PACKAGES;
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS)){
				qry += ExperienceSQLiteOpenHelper.MOBILE_BRAND_TITLE+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS;
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_AREAS)){
			qry += ExperienceSQLiteOpenHelper.AREA_TITLE+
			" FROM "+ExperienceSQLiteOpenHelper.TABLE_AREAS;
		}
		
		Cursor cursor = database.rawQuery(qry, null);
		
		cursor.moveToFirst();
		if(cursor.getCount()>0){
			do{
				titles.add(cursor.getString(0));
			}while(cursor.moveToNext());
		}		
		cursor.close();
		return titles;
	}	
	
	public int getId(String tblName, String titleValue){
		String qry = "SELECT ";
		
		if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS) ){
				qry += ExperienceSQLiteOpenHelper.OCCUPATION_ID+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS+
				" WHERE "+ExperienceSQLiteOpenHelper.OCCUPATION_TITLE+
				"='"+titleValue+"'";
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_PACKAGES)){
				qry += ExperienceSQLiteOpenHelper.PACKAGE_ID+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_PACKAGES+
				" WHERE "+ExperienceSQLiteOpenHelper.PACKAGE_TITLE+
				"='"+titleValue+"'";
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS)){
				qry += ExperienceSQLiteOpenHelper.MOBILE_BRAND_ID+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS+
				" WHERE "+ExperienceSQLiteOpenHelper.MOBILE_BRAND_TITLE+
				"='"+titleValue+"'";
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_PROMOTERS)){
			qry += ExperienceSQLiteOpenHelper.PROMOTER_ID+
					" FROM "+ExperienceSQLiteOpenHelper.TABLE_PROMOTERS+
					" WHERE "+ExperienceSQLiteOpenHelper.PROMOTER_CODE+
					"='"+titleValue+"'";
		}else if(tblName.equals(ExperienceSQLiteOpenHelper.TABLE_LOCATIONS)){
			qry += ExperienceSQLiteOpenHelper.LOCATION_ID+
					" FROM "+ExperienceSQLiteOpenHelper.TABLE_LOCATIONS+
					" WHERE "+ExperienceSQLiteOpenHelper.LOCATION_TITLE+
					"='"+titleValue+"'";
		}
		
		Cursor cursor = database.rawQuery(qry, null);
		
		cursor.moveToFirst();
		if(cursor.getCount()>0){
			return cursor.getInt(0);
		}		
		cursor.close();
		return 0;
	}
	
	public List<String> getAreaList(String promoterCode){
		List<String> areas = new ArrayList<String>();
		List<Integer> areaIds = new ArrayList<Integer>();
		
		//first select teamid, then areaid, then arealist
		int teamId = 0;
		Cursor cursor = database.rawQuery("SELECT "+ExperienceSQLiteOpenHelper.PROMOTER_TEAM_ID+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_PROMOTERS+
				" WHERE "+ExperienceSQLiteOpenHelper.PROMOTER_CODE+
				"='"+promoterCode+"'", null);
		
		cursor.moveToFirst();
		if(cursor.getCount()>0){
			teamId = cursor.getInt(0);
		}		
		cursor.close();
		
		if( teamId>0 ){		
			//now finding the area ids from team id
			Cursor curFindAreaIds = database.rawQuery("SELECT "+ExperienceSQLiteOpenHelper.LOCATION_AREA_ID+
					" FROM "+ExperienceSQLiteOpenHelper.TABLE_LOCATIONS+
					" WHERE "+ExperienceSQLiteOpenHelper.LOCATION_TEAM_ID+
					"="+teamId+"", null);
			curFindAreaIds.moveToFirst();
			if( curFindAreaIds.getCount()>0 ){
				do{
					areaIds.add(curFindAreaIds.getInt(0));
				}while(curFindAreaIds.moveToNext());
			}
			curFindAreaIds.close();
			
			if( !areaIds.isEmpty() ){
				//now finding the area list from area ids
				String qry = "SELECT "+ExperienceSQLiteOpenHelper.AREA_TITLE+
						" FROM "+ExperienceSQLiteOpenHelper.TABLE_AREAS+" WHERE ";
						
				boolean isFirst = true;
				
				for(Integer arId: areaIds){
					if( isFirst ){
						qry += ExperienceSQLiteOpenHelper.AREA_ID + "="+arId;
						isFirst = false;
					}else{
						qry += " OR "+ExperienceSQLiteOpenHelper.AREA_ID + "="+arId;
					}		
				}
				
				Cursor curFinal = database.rawQuery(qry, null);
				curFinal.moveToFirst();
				if( curFinal.getCount()>0 ){
					do{
						areas.add(curFinal.getString(0));
					}while(curFinal.moveToNext());
				}
			}
		}		
		return areas;
	}
	
	public List<String> getLocationList(String selectedArea){
		int areaId = 0;
		List<String> locations = new ArrayList<String>();
				
		Cursor cursor = database.rawQuery("SELECT "+ExperienceSQLiteOpenHelper.AREA_ID+
		" FROM "+ExperienceSQLiteOpenHelper.TABLE_AREAS+
		" WHERE "+ExperienceSQLiteOpenHelper.AREA_TITLE+"='"+
		selectedArea+"'", null);
		
		cursor.moveToFirst();
		if( cursor.getCount()>0){
			do{
				areaId = cursor.getInt(0);
				//Log.i("Area titleeee", cursor.getString(1));
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		Cursor cursor2 = database.rawQuery("SELECT "+ExperienceSQLiteOpenHelper.LOCATION_TITLE+
				" FROM "+ExperienceSQLiteOpenHelper.TABLE_LOCATIONS+
				" WHERE "+ExperienceSQLiteOpenHelper.AREA_ID+"="+
				Integer.toString(areaId), null);
	
		cursor2.moveToFirst();
		if(cursor2.getCount()>0){
			do{
				locations.add(cursor2.getString(0));
			}while(cursor2.moveToNext());
		}		
		cursor2.close();
		return locations;
	}
}
