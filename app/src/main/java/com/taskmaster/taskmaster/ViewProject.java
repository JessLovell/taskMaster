package com.taskmaster.taskmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ViewProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);
    }

    public void onAddTaskCreate(View v){
        Intent addTaskIntent = new Intent(this, AddTask.class);
        startActivity(addTaskIntent);
    }
}
