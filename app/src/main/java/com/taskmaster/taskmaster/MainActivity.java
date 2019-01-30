package com.taskmaster.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taskmaster.taskmaster.database.Project;
import com.taskmaster.taskmaster.database.ProjectDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProjectDatabase projectDatabase;
    private List<Project> serverDatabase;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final int RC_SIGN_IN = 482;

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

    public void onLoginButtonClick(View v){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                TextView showName = findViewById(R.id.textView);
                showName.setText(user.getDisplayName());
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
