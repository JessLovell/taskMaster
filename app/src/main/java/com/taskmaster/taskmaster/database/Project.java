package com.taskmaster.taskmaster.database;

import java.util.ArrayList;

public class Project {

    private String pid;
    private String title;
    private String description;
    private ArrayList<String> tasks;


    public Project () {}

    public Project (String title, String description) {

        this.title = title;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public String toString(){

        return this.title + ": " + this.description;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public ArrayList<String> getTasks(){
        return this.tasks;
    }

    public boolean addTask(String taskId){

        return tasks.add(taskId);
    }

    public Project setPid(String id){
        this.pid = id;
        return this;
    }

    public String getPid(){
        return this.pid;
    }
}
