package com.taskmaster.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.taskmaster.taskmaster.database.ProjectTask;

import java.util.LinkedList;

public class AllTask extends AppCompatActivity {

    private static FirebaseUser user;
    private ProjectTask taskToDisplay;
    private static final String TAG = "AllTask";

    private RecyclerView recyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);

        user = FirebaseAuth.getInstance().getCurrentUser();
        updateTaskRecyclerView();
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
                .whereEqualTo("creatorId", user.getUid())
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
