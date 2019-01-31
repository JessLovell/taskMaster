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
import com.taskmaster.taskmaster.database.Project;

import java.util.HashMap;
import java.util.Map;

public class AddProject extends AppCompatActivity {

    private String TAG = "ProjectDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
    }

    public void onCreateProjectButtonClick(View v){

        // grab the info from the form
        EditText title = findViewById(R.id.editText);
        EditText description = findViewById(R.id.editText2);

        final Project newProject = new Project(title.getText().toString(), description.getText().toString());

        // add to firebase cloud
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("projects")
                .add(newProject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //add the project Id to the document
                        Map<String, Object> updateProjId = new HashMap<>();
                        updateProjId.put("pid", documentReference.getId());
                        documentReference.update(updateProjId);
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        // redirect to the main page
        finish();
    }
}
