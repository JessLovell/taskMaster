package com.taskmaster.taskmaster.database;

import java.util.LinkedList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity
public class Project {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String description;

    @Relation(parentColumn = "id", entityColumn = "userId", entity = Task.class)
    public List<Task> tasks;

    public Project () {}

    public Project (String title, String description) {

        this.title = title;
        this.description = description;
        this.tasks = new LinkedList<>();
    }

    public String toString(){

        return this.title + ": " + this.description;
    }
}
