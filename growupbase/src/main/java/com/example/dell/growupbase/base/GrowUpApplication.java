package com.example.dell.growupbase.base;

import android.app.Application;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;

/**
 * 创建完自定义的Application之后一定要在mainfinest中设置一下，不然不会起作用
 */

public class GrowUpApplication extends Application{

    private JobManager jobManager;
    public static GrowUpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        configureJobManager();
    }

    public static GrowUpApplication getInstance() {
        return mInstance;
    }

    public void configureJobManager(){
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {

                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {

                    }

                    @Override
                    public void e(String text, Object... args) {

                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(configuration);
    }

    public JobManager getJobManager(){
        return jobManager;
    }

}
