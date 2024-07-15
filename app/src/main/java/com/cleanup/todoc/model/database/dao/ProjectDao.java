package com.cleanup.todoc.model.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.database.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects")
    List<Project> getAllProjects();

    @Insert
    void insertProject(Project project);
}