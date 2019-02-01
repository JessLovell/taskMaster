package com.taskmaster.taskmaster;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectTask;

import java.util.LinkedList;

public class ViewProject extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String projectId;
    private Project projectToDisplay;

    private static final String TAG = "ViewProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);

        // Gets project id and title from the intent that directed the user to this activity
        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        projectId = getIntent().getStringExtra("PROJECT_ID");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("projects").document(projectId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        projectToDisplay = document.toObject(Project.class);

                        TextView title = findViewById(R.id.textView3);
                        title.setText(projectToDisplay.getTitle());

                        TextView description  = findViewById(R.id.textView7);
                        description.setText(projectToDisplay.getDescription());

                        updateTaskRecyclerView();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void onAddTaskCreate(View v){

        Intent addTaskIntent = new Intent(this, AddTask.class);

        addTaskIntent.putExtra("PROJECT_ID", projectId);
        startActivity(addTaskIntent);


    }

    public void updateTaskRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.taskRecycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new TaskAdapter(new LinkedList<ProjectTask>());
        recyclerView.setAdapter(mAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New task: " + dc.getDocument().getData());
                                    mAdapter.add(dc.getDocument().toObject(ProjectTask.class).setTid(dc.getDocument().getId()));
                                    break;
                                case MODIFIED:
                                    Log.d(TAG, "Modified task: " + dc.getDocument().getData());
                                    mAdapter.update(dc.getDocument().toObject(ProjectTask.class));
                                    break;
                                case REMOVED:
                                    Log.d(TAG, "Removed task: " + dc.getDocument().getData());
                                    break;
                            }
                        }

                    }
                });
    }
}
