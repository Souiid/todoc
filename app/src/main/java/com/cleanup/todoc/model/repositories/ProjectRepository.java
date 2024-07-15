package com.cleanup.todoc.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.database.entity.Project;
import com.cleanup.todoc.model.database.dao.ProjectDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectRepository {
    private final ProjectDao projectDao;
    private final Executor executor;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Project>> getAllProjects() {
        MutableLiveData<List<Project>> projects = new MutableLiveData<>();
        executor.execute(() -> projects.postValue(projectDao.getAllProjects()));
        return projects;
    }

    public void insertProject(Project project) {
        executor.execute(() -> projectDao.insertProject(project));
    }
}


