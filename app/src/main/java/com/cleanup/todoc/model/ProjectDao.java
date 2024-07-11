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

    @Query("SELECT * FROM projects WHERE id = :id")
    Project getProjectById(long id);

    @Insert
    void insertProject(Project project);

    @Update
    void updateProject(Project project);

    @Delete
    void deleteProject(Project project);
}