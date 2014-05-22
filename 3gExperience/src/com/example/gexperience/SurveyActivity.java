package com.example.gexperience;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gexperience.database.DatabaseUtil;
import com.example.gexperience.database.ExperienceSQLiteOpenHelper;
import com.example.gexperience.datamodel.Occupation;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.os.Build;

public class SurveyActivity extends ActionBarActivity {
	
	Button btnSubmit, btnReset;
	DatabaseUtil dbUtil;
	Spinner spnOccupation, spnPackage, spnMobileBrand, spnNewPackage, spnMobileTypes;
	ProgressDialog progressDialog;
	String serverResponse;
	JSONObject jDataObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey);
		
		populate_spinners();
		
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnReset = (Button)findViewById(R.id.btnReset);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(SurveyActivity.this).create();

		        alertDialog.setTitle("Confirmation");
		        alertDialog.setMessage("Are you sure all the given data are valid?");
		        alertDialog.setCancelable(true);
		        
		        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {							
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if( validate_data() ){
							//uploading data
							new SurveyUploader().execute();
						}else{
							show_alert_dialog("Validation Error", "Please fill the form properly");
						}
					}
				});
		        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {							
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		        alertDialog.show();				
			}
		});		
		
		btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(SurveyActivity.this).create();

		        alertDialog.setTitle("Confirmation");
		        alertDialog.setMessage("Reset will erase all the given data, are you sure you wish to reset the form?");
		        alertDialog.setCancelable(true);
		        
		        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {							
					@Override
					public void onClick(DialogInterface dialog, int which) {
						reset_form();
					}
				});
		        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {							
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		        alertDialog.show();
				
			}
		});
	}
	
	public void reset_form(){		
		((EditText)findViewById(R.id.edtName)).setText("");
		((EditText)findViewById(R.id.edtMobileNo)).setText("");
		((EditText)findViewById(R.id.edtAge)).setText("");
		((EditText)findViewById(R.id.edtRecharge)).setText("");
	}
	
	
	public boolean validate_data(){
		boolean validated = true;
		if( ((EditText)findViewById(R.id.edtName)).getText().toString().trim().length()<3 ){
			((EditText)findViewById(R.id.edtName)).setError("Required");
			validated = false;
		}
		if( ((EditText)findViewById(R.id.edtMobileNo)).getText().toString().trim().length()!=11 ){
			((EditText)findViewById(R.id.edtMobileNo)).setError("Must be 11 digits");
			validated = false;			
		}
		if( ((EditText)findViewById(R.id.edtAge)).getText().toString().trim().length()<2 ){
			((EditText)findViewById(R.id.edtAge)).setError("Age should be at least 10 years");
			validated = false;
		}
		if(((EditText)findViewById(R.id.edtRecharge)).getText().toString().trim().length()<1){
			((EditText)findViewById(R.id.edtRecharge)).setError("Required");
			validated = false;
		}
		return validated;
	}
	/**
	 * 
	 */
	public void populate_spinners(){
		List<String> occupations, packages, mobileBrands, mobileTypes, newPackages;
		ArrayAdapter<String> adapterOccupation, adapterPackage, adapterMobileBrand;
		ArrayAdapter<String> adapterNewPackage, adapterMobileType;
		
		
		dbUtil = new DatabaseUtil(SurveyActivity.this);
		dbUtil.open();
		
		occupations = dbUtil.getSpinnerItems(ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS);
		packages = dbUtil.getSpinnerItems(ExperienceSQLiteOpenHelper.TABLE_PACKAGES);
		mobileBrands = dbUtil.getSpinnerItems(ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS);
		dbUtil.close();
		
		mobileTypes = new ArrayList<String>();
		mobileTypes.add("Regular Phone");
		mobileTypes.add("Smart Phone");		
		
		spnOccupation = (Spinner)findViewById(R.id.spnOccupation);
		spnPackage = (Spinner)findViewById(R.id.spnPackageTypes);
		spnNewPackage = (Spinner)findViewById(R.id.spnNewPackage);
		spnMobileBrand = (Spinner)findViewById(R.id.spnMobileBrand);
		spnMobileTypes = (Spinner)findViewById(R.id.spnMobileType);
		
		adapterOccupation = new ArrayAdapter<String>(SurveyActivity.this, android.R.layout.simple_spinner_item, occupations);
		adapterOccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnOccupation.setAdapter(adapterOccupation);
		
		adapterPackage = new ArrayAdapter<String>(SurveyActivity.this, android.R.layout.simple_spinner_item, packages);
		adapterPackage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnPackage.setAdapter(adapterPackage);
		
		//New package should have the none option since user may not buy a package instantly
		newPackages = new ArrayList<String>();
		newPackages.add("None");
		
		for(String pckg: packages){
			newPackages.add(pckg);
		}
		
		
		adapterNewPackage = new ArrayAdapter<String>(SurveyActivity.this, android.R.layout.simple_spinner_item, newPackages);
		adapterNewPackage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnNewPackage.setAdapter(adapterNewPackage);
		
		adapterMobileBrand = new ArrayAdapter<String>(SurveyActivity.this, android.R.layout.simple_spinner_item, mobileBrands);
		adapterMobileBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnMobileBrand.setAdapter(adapterMobileBrand);
		
		adapterMobileType = new ArrayAdapter<String>(SurveyActivity.this, android.R.layout.simple_spinner_item, mobileTypes);
		adapterMobileType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnMobileTypes.setAdapter(adapterMobileType);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.survey, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void show_alert_dialog(String title, String msg){
		AlertDialog alertDialog = new AlertDialog.Builder(SurveyActivity.this).create();
    	alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {							
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
        alertDialog.show();
	}
	
	private class SurveyUploader extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(SurveyActivity.this, "Please Wait", "Uploading survey details...", true, false);
		}

		@Override
		protected String doInBackground(Void... params) {
			String taskResult = "nothing!";
			
			//making it empty otherwise it may cause problem in onPostExecute method
			serverResponse = "";
			
			int occupation_id, package_id, new_package_id, mobile_brand_id;
			int is_smart_phone = 0;
			
			if(((Spinner)findViewById(R.id.spnMobileType)).getSelectedItem().toString().equals("Smart Phone")){
				is_smart_phone = 1;
			}
			
			dbUtil = new DatabaseUtil(SurveyActivity.this);
			dbUtil.open();
			
			occupation_id = dbUtil.getId(ExperienceSQLiteOpenHelper.TABLE_OCCUPATIONS,
					((Spinner)findViewById(R.id.spnOccupation)).getSelectedItem().toString());
			
			package_id = dbUtil.getId(ExperienceSQLiteOpenHelper.TABLE_PACKAGES,
					((Spinner)findViewById(R.id.spnPackageTypes)).getSelectedItem().toString());
			
			new_package_id = dbUtil.getId(ExperienceSQLiteOpenHelper.TABLE_PACKAGES,
					((Spinner)findViewById(R.id.spnNewPackage)).getSelectedItem().toString());
			
			mobile_brand_id = dbUtil.getId(ExperienceSQLiteOpenHelper.TABLE_MOBILE_BRANDS,
					((Spinner)findViewById(R.id.spnMobileBrand)).getSelectedItem().toString());
			
			dbUtil.close();			

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(LoginActivity.BASE_URL+"/receive_survey_data");
				MultipartEntity reqEntry = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				
				reqEntry.addPart("promoter_id", new StringBody("1"));
				reqEntry.addPart("location_id", new StringBody("1"));
							
				reqEntry.addPart("name", new StringBody(((EditText)findViewById(R.id.edtName)).getText().toString().trim()));
				reqEntry.addPart("age", new StringBody(((EditText)findViewById(R.id.edtAge)).getText().toString().trim()));
				reqEntry.addPart("mobile", new StringBody(((EditText)findViewById(R.id.edtMobileNo)).getText().toString().trim()));
				reqEntry.addPart("recharge_amount", new StringBody(((EditText)findViewById(R.id.edtRecharge)).getText().toString().trim()));
				
				reqEntry.addPart("occupation_id", new StringBody(Integer.toString(occupation_id)));
				reqEntry.addPart("mobile_brand_id", new StringBody(Integer.toString(mobile_brand_id)));
				reqEntry.addPart("package_id", new StringBody(Integer.toString(package_id)));
				reqEntry.addPart("new_package_id", new StringBody(Integer.toString(new_package_id)));
				reqEntry.addPart("is_smart_phone", new StringBody(Integer.toString(is_smart_phone)));
				
				if( is_three_g() ){
					reqEntry.addPart("is_3g", new StringBody("1"));
				}else{
					reqEntry.addPart("is_3g", new StringBody("0"));
				}
				
				if( is_female() ){
					reqEntry.addPart("is_female", new StringBody("1"));
				}else{
					reqEntry.addPart("is_female", new StringBody("0"));
				}
				reqEntry.addPart("date_time", new StringBody(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())));				
					
				httppost.setEntity(reqEntry);

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity resEntity = response.getEntity();
				
				InputStream is = resEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				is.close();
//					String serverResponse = "";
				serverResponse = sb.toString().trim();
				
				Log.i("Response from server:::", "::::::"+serverResponse);				
				
				jDataObj = new JSONObject(serverResponse);	
			} catch (Exception ex) {
				taskResult = "ERROR: " + ex.getMessage();
				Log.i("Error", "_____________"+taskResult);
				return taskResult;
			}
			return taskResult;
		}

		protected void onPostExecute(String msg) {
			if(progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			try {
				jDataObj = new JSONObject(serverResponse);
				
				if( jDataObj.length()>0 && jDataObj.getBoolean("success")==true ){
					show_alert_dialog("Upload successful!", "Upload successful. Thank you.");
					reset_form();
				}else{
					show_alert_dialog("Upload response!", "Upload failed! Please try again");						
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean is_three_g(){
		RadioGroup rg = (RadioGroup)findViewById(R.id.rdo3g);
		int id= rg.getCheckedRadioButtonId();
		
	    View radioButton = rg.findViewById(id);
	    
	    int radioId = rg.indexOfChild(radioButton);
	    String is3g = ((RadioButton) rg.getChildAt(radioId)).getText().toString();
	    if( is3g.equals("Yes") ) return true;
	    return false;
	}
	
	public boolean is_female(){
		RadioGroup rg = (RadioGroup)findViewById(R.id.rdoGender);
		int id= rg.getCheckedRadioButtonId();
		
	    View radioButton = rg.findViewById(id);
	    
	    int radioId = rg.indexOfChild(radioButton);
	    String isFemale = ((RadioButton) rg.getChildAt(radioId)).getText().toString();
	    if( isFemale.equals("Female") ) return true;
	    return false;
	}
}
