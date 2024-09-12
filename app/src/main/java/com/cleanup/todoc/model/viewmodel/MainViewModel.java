package com.cleanup.todoc.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.database.entity.Project;
import com.cleanup.todoc.model.database.entity.Task;
import com.cleanup.todoc.model.repositories.ProjectRepository;
import com.cleanup.todoc.model.repositories.TaskRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final TaskRepository taskRepository;
    private final LiveData<List<Project>> allProjects;
    private final LiveData<List<Task>> allTasks;

    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        allProjects = projectRepository.getAllProjects();
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }
}

