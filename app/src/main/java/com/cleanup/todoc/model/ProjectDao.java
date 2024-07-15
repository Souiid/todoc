package com.cleanup.todoc.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects")
    List<Project> getAllProjects();

    @Insert
    void insertProject(Project project);
}