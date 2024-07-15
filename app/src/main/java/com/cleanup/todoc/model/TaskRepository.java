package com.cleanup.todoc.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final Executor executor;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Task>> getAllTasks() {
        MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
        executor.execute(() -> tasks.postValue(taskDao.getAllTasks()));
        return tasks;
    }

    public void insertTask(Task task) {
        executor.execute(() -> taskDao.insertTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskDao.deleteTask(task));
    }
}


