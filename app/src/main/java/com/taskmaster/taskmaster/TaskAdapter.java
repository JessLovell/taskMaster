package com.taskmaster.taskmaster;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taskmaster.taskmaster.database.ProjectTask;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private List<ProjectTask> values;

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

    public void add(ProjectTask task) {
        values.add(task);
        notifyItemInserted(values.size() - 1);
    }

    public void update(ProjectTask task){

        for (ProjectTask t : values){
            if (t.getTitle() == task.getTitle()){
                int index = values.indexOf(t);
                values.set(index, task);
            }
        }
        notifyDataSetChanged();
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    //TODO: Change from Project Id to task id
    public void remove(String pid) {

    }
    public TaskAdapter(List<ProjectTask> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
                        String id = idView.getText().toString();
                        Log.i("Task Title", id);
                        goToTask(v, id);
                    }
                });

        // set the view's size, margins, paddings and layout parameters
        TaskAdapter.ViewHolder vh = new TaskAdapter.ViewHolder(v);
        return vh;
    }

    // Takes the user to the ProjectWithTasks activity
    // https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
    public void goToTask(View v, String id) {
        Intent goToTaskWithTasksIntent = new Intent(v.getContext(), ViewTask.class);

        // https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
        goToTaskWithTasksIntent.putExtra("TASK_ID", id);
        v.getContext().startActivity(goToTaskWithTasksIntent);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ProjectTask task = values.get(position);
        holder.txtHeader.setText(task.getTitle());
        holder.txtFooter.setText(task.getDescription());
        holder.txtDate.setText(task.getTid());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
