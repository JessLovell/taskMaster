package com.taskmaster.taskmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateProjectButtonClick(View v){

        Intent createProjectIntent = new Intent(this, AddProject.class);
        startActivity(createProjectIntent);
    }
}
