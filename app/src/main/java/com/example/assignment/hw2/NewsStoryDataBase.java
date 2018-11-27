package com.example.assignment.hw2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsStoryDataBase extends RoomDatabase {

    public abstract NewsItemDao newsStoryDao();

    private static NewsStoryDataBase INSTANCE;

    public static NewsStoryDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NewsStoryDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , NewsStoryDataBase.class, "newsstoriesdatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
