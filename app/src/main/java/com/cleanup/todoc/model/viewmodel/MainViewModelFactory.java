package com.cleanup.todoc.model.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.repositories.ProjectRepository;
import com.cleanup.todoc.model.repositories.TaskRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public MainViewModelFactory(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(projectRepository, taskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

