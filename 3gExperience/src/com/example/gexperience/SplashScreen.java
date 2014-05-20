package com.example.gexperience;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.splash_container, new PlaceholderFragment())
                    .commit();
        }
        
        
        
        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        finish();
        //Now going to login activity
//        Intent loginIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginIntent);
        
//        Intent surveyIntent = new Intent(this, SurveyActivity.class);
////        startActivity(surveyIntent);
//		startActivityForResult(surveyIntent, 2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_splash_screen, container, false);
            return rootView;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//        if (data.hasExtra("returnKey1")) {
//          Toast.makeText(this, data.getExtras().getString("returnKey1"),
//            Toast.LENGTH_SHORT).show();
//        }
//      }
    	
//    	etPassword.setText("");
    } 

}
