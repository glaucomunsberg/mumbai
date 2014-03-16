package com.plataformaparaformal.Mumbai;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.plataformaparaformal.Mumbai.util.MultiSelectSpinner;


public class SearchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MultiSelectSpinner multiSelectSpinner;
        ArrayAdapter<String> adapter;
        String[] testArray;

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_activityType);
        testArray = getResources().getStringArray(R.array.see_activitiesTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_shiftType);
        testArray = getResources().getStringArray(R.array.see_shiftsTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_registredAlmoustType);
        testArray = getResources().getStringArray(R.array.see_registredAmountTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_localizationType);
        testArray = getResources().getStringArray(R.array.see_localizationsTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_postionBodyType);
        testArray = getResources().getStringArray(R.array.see_positionBodyTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_numberBodyType);
        testArray = getResources().getStringArray(R.array.see_numberBodyTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_sensesType);
        testArray = getResources().getStringArray(R.array.see_sensesTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_equmentMobilityType);
        testArray = getResources().getStringArray(R.array.see_equimentMobilitiesTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_equipmentScaleType);
        testArray = getResources().getStringArray(R.array.see_equipmentScaleTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_equmentInstalationType);
        testArray = getResources().getStringArray(R.array.see_equimentInstalationsTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent myIntent;
        switch(id){
            case R.id.search_settings:
                myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            case R.id.search_discard:
                this.clearFilters();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearFilters(){
        MultiSelectSpinner multiSelectSpinner;
        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.search_equmentInstalationType);
        multiSelectSpinner.setSelection(-1);
    }
}
