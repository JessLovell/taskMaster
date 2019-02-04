package com.taskmaster.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectTask;

import java.util.HashMap;

public class AddTask extends AppCompatActivity {

    public static final String TAG = "AddTask";
    private String projectId;
    private Project projectToUpdate;
    private String newTaskId;
    private static FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        projectId = getIntent().getStringExtra("PROJECT_ID");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void onCreateTaskButtonClick(View V){

        // grab the info from the form
        EditText title = findViewById(R.id.editText3);
        EditText description = findViewById(R.id.editText4);

        final ProjectTask newTask = new ProjectTask(title.getText().toString(), description.getText().toString(), projectId, user.getUid());

        // add to firebase cloud
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .add(newTask)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        newTaskId = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        //update the related project
        final DocumentReference docRef = db.collection("projects").document(projectId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        //get the project instance and add the task
                        projectToUpdate = document.toObject(Project.class);
                        projectToUpdate.addTask(newTaskId);

                        //create field in firebase to update the tasks
                        HashMap<String, Object> projectUpdate = new HashMap<>();
                        projectUpdate.put("tasks", projectToUpdate.getTasks());

                        docRef.update(projectUpdate);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // redirect to the project page
        finish();
    }
}
