package com.cleanup.todoc.model.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.database.dao.ProjectDao;
import com.cleanup.todoc.model.database.dao.TaskDao;
import com.cleanup.todoc.model.database.entity.Project;
import com.cleanup.todoc.model.database.entity.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (AppDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {

        return new Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);
                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    Executors.newSingleThreadExecutor().execute(() ->
                            INSTANCE.projectDao().insertProject(project));

                }
            }
        };
    }
}


