package com.taskmaster.taskmaster;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskmaster.taskmaster.database.Project;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Project> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public TextView txtDate;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            txtDate = (TextView) v.findViewById(R.id.textView9);
        }
    }

    public void add(Project project) {
        values.add(project);
        notifyItemInserted(values.size() - 1);
    }

    public void update(Project project){

        for (Project p : values){
            if (p.getTitle() == project.getTitle()){
                int index = values.indexOf(p);
                values.set(index, project);
            }
        }
        notifyDataSetChanged();
    }

    public void remove(String pid) {

        for (Project p : values){
            if (p.getPid() == pid){
                values.remove(values.indexOf(p));
                notifyItemRemoved(values.indexOf(p));
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProjectAdapter(List<Project> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);

        // Adds an onClick listener
        // https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        TextView idView = v.findViewById(R.id.textView9);
                        TextView titleView = v.findViewById(R.id.firstLine);
                        String id = idView.getText().toString();
                        String title = titleView.getText().toString();
                        Log.i("Project Title", id + " " + title);
                        goToProject(v, id, title);
                    }
                });

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Takes the user to the ProjectWithTasks activity
    // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
    public void goToProject(View v, String id, String title) {
        Intent goToProjectWithTasksIntent = new Intent(v.getContext(), ViewProject.class);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        goToProjectWithTasksIntent.putExtra("PROJECT_ID", id);
        goToProjectWithTasksIntent.putExtra("PROJECT_TITLE", title);
        v.getContext().startActivity(goToProjectWithTasksIntent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Project project = values.get(position);
        holder.txtHeader.setText(project.getTitle());
        holder.txtFooter.setText(project.getDescription());
        holder.txtDate.setText(project.getPid());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}