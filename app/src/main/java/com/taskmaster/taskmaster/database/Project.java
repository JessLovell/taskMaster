package com.taskmaster.taskmaster.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Project {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String description;

    public Project () {}

    public Project (String title, String description) {

        this.title = title;
        this.description = description;
    }

    public String toString(){

        return this.title + ": " + this.description;
    }
}
