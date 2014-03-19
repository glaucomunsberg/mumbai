package com.plataformaparaformal.Mumbai;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;


public class SeeParaformalidadeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_paraformalidade);

        Intent myIntent;
        ImageView image = (ImageView) findViewById(R.id.see_img);
        getActionBar().setTitle(R.string.see_title);
        getActionBar().setSubtitle(R.string.see_subTitle);
        /**
         * HERE I NEED POST IMAGE OPENING WHIT ImageFullActivity
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.see_paraformalidade, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.see_activityType);
        spinner.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.see_localizationType);
        spinner.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.see_equipmentMobilityType);
        spinner.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.see_shiftType);
        spinner.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.see_senseType);
        spinner.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.see_registredAmountType);
        spinner.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent myIntent;
        switch (id){

            case R.id.see_settings:
                myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            case R.id.see_edit:
                myIntent = new Intent(this, EditParaformalidadeActivity.class);
                startActivity(myIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}