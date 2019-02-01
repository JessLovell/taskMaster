package com.taskmaster.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmaster.taskmaster.database.ProjectTask;

public class AddTask extends AppCompatActivity {

    public static final String TAG = "AddTask";
    private String projectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        projectId = getIntent().getStringExtra("PROJECT_ID");
    }

    public void onCreateTaskButtonClick(View V){

        // grab the info from the form
        EditText title = findViewById(R.id.editText3);
        EditText description = findViewById(R.id.editText4);

        final ProjectTask newTask = new ProjectTask(title.getText().toString(), description.getText().toString(), projectId);

        // add to firebase cloud
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .add(newTask)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        // redirect to the project page
        finish();
    }
}
