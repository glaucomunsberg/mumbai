package com.plataformaparaformal.Mumbai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.plataformaparaformal.Mumbai.util.MultiSelectSpinner;

import java.util.ArrayList;
import java.util.Collections;


public class EditParaformalidadeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paraformalidade);

        if(Build.VERSION.SDK_INT > 10){
            getActionBar().setTitle(R.string.edit_title);
            getActionBar().setSubtitle(R.string.edit_subTitle);
        }else{
            getSupportActionBar().setTitle(R.string.edit_title);
            getSupportActionBar().setSubtitle(R.string.edit_subTitle);
        }

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


        Button botton = (Button) findViewById(R.id.edit_btnUpload);
        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.alert_edit_chooseUploadTitle);
                builder.setItems(R.array.edit_choosePictureOrigins, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /**
                         * UIASUIHIUAH
                         */
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

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
            case R.id.edit_send:
                AlertDialog.Builder edit_send = new AlertDialog.Builder(this);
                edit_send.setMessage(R.string.alert_edit_message).setTitle(R.string.alert_edit_title);
                AlertDialog dialog = edit_send.create();
                dialog.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
