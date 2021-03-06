package com.taskmaster.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taskmaster.taskmaster.database.Project;


public class AddProject extends AppCompatActivity {

    private String TAG = "ProjectDatabase";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void onCreateProjectButtonClick(View v){

        // grab the info from the form
        EditText title = findViewById(R.id.editText);
        EditText description = findViewById(R.id.editText2);

        final Project newProject = new Project(title.getText().toString(), description.getText().toString(), user.getUid());

        // add to firebase cloud
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("projects")
                .add(newProject)
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

        // redirect to the main page
        finish();
    }
}
