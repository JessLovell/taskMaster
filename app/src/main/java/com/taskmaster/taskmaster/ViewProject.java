package com.taskmaster.taskmaster;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmaster.taskmaster.database.Project;

public class ViewProject extends AppCompatActivity {

    private String projectId;
    private String projectTitle;
    private Project projectToDisplay;

    private static final String TAG = "ViewProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);

        // Gets project id and title from the intent that directed the user to this activity
        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        projectId = getIntent().getStringExtra("PROJECT_ID");
        projectTitle = getIntent().getStringExtra("PROJECT_TITLE");

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
        startActivity(addTaskIntent);
    }
}
