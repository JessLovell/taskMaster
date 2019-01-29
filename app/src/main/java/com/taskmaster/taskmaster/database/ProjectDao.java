package com.taskmaster.taskmaster.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project")
    List<Project> getAll();

    @Query("SELECT * FROM project WHERE id = :id")
    Project getById(long id);

    @Insert
    void add(Project project);
}
