package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.database.AppDatabase;
import com.cleanup.todoc.model.database.dao.ProjectDao;
import com.cleanup.todoc.model.database.dao.TaskDao;
import com.cleanup.todoc.model.database.entity.Project;
import com.cleanup.todoc.model.database.entity.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private AppDatabase database;
    private ProjectDao projectDao;
    private TaskDao taskDao;

    @Before
    public void setUp() {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }
        });

        // Initialiser la base de données en mémoire
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        projectDao = database.projectDao();
        taskDao = database.taskDao();
    }

    @After
    public void tearDown() {
        // Réinitialiser le comportement par défaut d'exécution des tâches
        ArchTaskExecutor.getInstance().setDelegate(null);
        database.close();
    }

    @Test
    public void insertAndGetProject() {
        // Insérer un projet dans la base de données
        Project project = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDao.insertProject(project);

        List<Project> projects = projectDao.getAllProjects();
        assertEquals(1, projects.size());
        assertEquals("Projet Tartampion", projects.get(0).getName());
    }

    @Test
    public void insertAndGetTask() throws Exception {
        // Insérer un projet et une tâche associée
        Project project = new Project(1L, "Projet Lucidia", 0xFFB4CDBA);
        projectDao.insertProject(project);

        Task task = new Task(1L, "Tâche de test", System.currentTimeMillis());
        taskDao.insertTask(task);

        // Récupérer toutes les tâches et vérifier l'insertion
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getAllTasks());
        assertEquals(1, tasks.size());
        assertEquals("Tâche de test", tasks.get(0).getName());
    }

    @Test
    public void insertAndDeleteTask() throws Exception {
        // Insérer un projet avant d'insérer la tâche
        Project project = new Project(1L, "Projet Lucidia", 0xFFB4CDBA);
        projectDao.insertProject(project);  // Assurez-vous que le projet existe

        // Insérer une tâche liée à ce projet
        Task task = new Task(1L, "Tâche à supprimer", System.currentTimeMillis());
        taskDao.insertTask(task);

        // Récupérer les tâches et vérifier l'insertion
        List<Task> tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getAllTasks());
        assertEquals(1, tasks.size());

        // Supprimer la tâche et vérifier la suppression
        taskDao.deleteTask(tasks.get(0));
        tasks = LiveDataTestUtil.getOrAwaitValue(taskDao.getAllTasks());
        assertTrue(tasks.isEmpty());
    }

}
