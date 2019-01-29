package com.taskmaster.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
    }

    public void onCreateProjectButtonClick(View v){

        // grab the info from the form
        EditText title = findViewById(R.id.editText);
        EditText description = findViewById(R.id.editText2);

        // add to the database

        // redirect to the main page
        finish();
    }
}
