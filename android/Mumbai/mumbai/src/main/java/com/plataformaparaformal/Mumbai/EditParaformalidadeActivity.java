package com.plataformaparaformal.Mumbai;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.plataformaparaformal.Mumbai.util.MultiSelectSpinner;


public class EditParaformalidadeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paraformalidade);

        getActionBar().setTitle(R.string.edit_title);
        getActionBar().setSubtitle(R.string.edit_subTitle);

        MultiSelectSpinner multiSelectSpinner;
        ArrayAdapter<String> adapter;
        String[] testArray;
        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.edit_environmentalConditionsType);
        testArray = getResources().getStringArray(R.array.see_environmentalConditionsTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.edit_equipmentInstalation);
        testArray = getResources().getStringArray(R.array.see_equimentInstalationsTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.edit_sensesType);
        testArray = getResources().getStringArray(R.array.see_sensesTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

        multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.edit_climateType);
        testArray = getResources().getStringArray(R.array.see_climateTypes);
        multiSelectSpinner.setItems(testArray);
        multiSelectSpinner.setSelection(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_paraformalidade, menu);
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
            case R.id.edit_settings:
                myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
