package com.taskmaster.taskmaster.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String description;
    public String status;


    public Project project;

     public Task (){ }

     public Task (String title, String description, Project project){
         this.title= title;
         this.description = description;
         this.status = "Available";
         this.project = project;
     }

     public String setStatus (int status){

         if (status == 0){
             this.status = "Available";
         } else if (status == 1){
             this.status = "Assigned";
         } else if (status == 2){
             this.status = "Accepted";
         } else if (status == 3){
             this.status = "Finished";
         } else {
             return "Status not set";
         }
         return this.status;
     }
}
