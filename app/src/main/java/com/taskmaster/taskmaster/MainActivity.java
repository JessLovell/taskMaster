package com.taskmaster.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProjectDatabase projectDatabase;
    private List<Project> serverDatabase;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectDatabase = Room.databaseBuilder(getApplicationContext(),
                ProjectDatabase.class, "exercise_journal").allowMainThreadQueries().build();

        renderRecyclerView();
    }

    @Override
    protected void onRestart() {
        renderRecyclerView();
        super.onRestart();
    }

    public void onCreateProjectButtonClick(View v){

        Intent createProjectIntent = new Intent(this, AddProject.class);
        startActivity(createProjectIntent);


    }

    public void renderRecyclerView(){

        projectDatabase = Room.databaseBuilder(getApplicationContext(),
                ProjectDatabase.class, "exercise_journal").allowMainThreadQueries().build();

        //Add the external database to the local database
//        serverDatabase.addAll(projectDatabase.projectDao().getAll());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new MyAdapter(projectDatabase.projectDao().getAll());
        recyclerView.setAdapter(mAdapter);
    }
}
