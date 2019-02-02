package com.taskmaster.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectTask;

public class ViewTask extends AppCompatActivity {

    private static final String TAG = "ViewTask";
    private String projectTaskId;
    private ProjectTask taskToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //get Task data from firebase
        projectTaskId = getIntent().getStringExtra("TASK_ID");
        getTaskFromFirebase();
    }

    public void getTaskFromFirebase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("tasks").document(projectTaskId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        taskToDisplay = document.toObject(ProjectTask.class);

                        TextView title = findViewById(R.id.textView3);
                        TextView description = findViewById(R.id.textView7);
                        TextView status = findViewById(R.id.textView10);

                        title.setText(taskToDisplay.getTitle());
                        description.setText(taskToDisplay.getDescription());
                        status.setText(taskToDisplay.getStatus());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
