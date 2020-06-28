package tv.danmaku.ijk.media.example.netflix;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {

    private static App instance;
    private ExecutorService taskExecutor;


    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.taskExecutor = Executors.newSingleThreadExecutor();
    }

    public ExecutorService getTaskExecutor() {
        return taskExecutor;
    }
}
