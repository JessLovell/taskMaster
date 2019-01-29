package com.taskmaster.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {
    public abstract ProjectDao projectDao();
}
