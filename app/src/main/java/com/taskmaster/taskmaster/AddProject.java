package com.taskmaster.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectDatabase;

public class AddProject extends AppCompatActivity {

    private ProjectDatabase projectDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        projectDatabase = Room.databaseBuilder(getApplicationContext(),
                ProjectDatabase.class, "exercise_journal").allowMainThreadQueries().build();
    }

    public void onCreateProjectButtonClick(View v){

        // grab the info from the form
        EditText title = findViewById(R.id.editText);
        EditText description = findViewById(R.id.editText2);

        // add to the database
        Project newProject = new Project(title.getText().toString(), description.getText().toString());
        projectDatabase.projectDao().add(newProject);

        // redirect to the main page
        finish();
    }
}
