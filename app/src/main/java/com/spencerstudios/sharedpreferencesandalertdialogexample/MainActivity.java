package com.spencerstudios.sharedpreferencesandalertdialogexample;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences myPreferences;
    private SharedPreferences.Editor myPreferencesEditor;
    private EditText editTextName, editTextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextAge = (EditText) findViewById(R.id.edit_text_age);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myPreferencesEditor = myPreferences.edit();
    }


    private void displayAlertDialog() {

        final String NAME = editTextName.getText().toString().trim();
        final int AGE = Integer.parseInt(editTextAge.getText().toString().trim());

        AlertDialog myDialog = new AlertDialog.Builder(this).create();
        myDialog.setTitle("Your name/age");
        myDialog.setMessage("Name: " + NAME + "\n" +
                            "Age: " + AGE);

        myDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save to shared preferenceS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myPreferencesEditor.putString("name", NAME);
                myPreferencesEditor.putInt("age", AGE);
                myPreferencesEditor.apply();

                Toast.makeText(getApplicationContext(),"Saved to Shared Preferences",Toast.LENGTH_LONG).show();

                clearEditTexts();
            }
        });

        myDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        myDialog.show();
    }


    public void buttonClick(View v) {

        int id = v.getId();

        if (id == R.id.button_okay) {
            if (editTextName.getText().length() > 0 && editTextAge.getText().length() > 0) {
                displayAlertDialog();
            }
        } else if (id == R.id.button_fetch_info) {
            editTextName.setText(myPreferences.getString("name", "Larry Page"));
            editTextAge.setText(String.valueOf(myPreferences.getInt("age", 44)));
        }else{
            clearEditTexts();
        }
    }


    private void clearEditTexts(){
        editTextName.setText("");
        editTextAge.setText("");
    }
}
