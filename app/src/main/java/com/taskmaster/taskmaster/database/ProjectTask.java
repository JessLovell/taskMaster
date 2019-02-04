package com.taskmaster.taskmaster.database;

public class ProjectTask {

    private String tid;
    private String title;
    private String description;
    private String status;
    private String projectId;
    private String creatorId;
    private String assignedToId;

     public ProjectTask(){ }

     public ProjectTask(String title, String description, String projectId, String creatorId){
         this.title= title;
         this.description = description;
         this.status = "Available";
         this.projectId = projectId;
         this.creatorId = creatorId;
     }

     public String getCreatorId(){
         return this.creatorId;
     }
     public String getTitle(){
         return this.title;
     }

     public String getDescription(){
         return this.description;
     }

     public String getProjectId(){
         return this.projectId;
     }

     public String getStatus(){
         return this.status;
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

     public String getTid() { return this.tid; }

     public ProjectTask setTid(String id) {
         this.tid = id;
         return this;
     }

     public String setAssingedTo(String id){
        return this.assignedToId = id;
     }

     public String getAssignedToId() {
         return this.assignedToId;
     }
}
