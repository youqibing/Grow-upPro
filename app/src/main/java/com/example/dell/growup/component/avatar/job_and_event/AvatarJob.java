package com.example.dell.growup.component.avatar.job_and_event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.growup.network.result.AvatarResult;
import com.example.dell.growup.data.UserPreference;
import com.example.dell.growup.network.result.ApiResult;
import com.example.dell.growup.network.RetrofitApi;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dell on 2017/10/5.
 */

public class AvatarJob extends Job {

    private String uri;
    private static final int UX = 4;

    public AvatarJob(String uri) {
        super(new Params(UX).requireNetwork().persist());

        this.uri = uri;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<AvatarResult> headerResult = RetrofitApi.headerchange(uri);

        if(headerResult.getCode() != 0 ){
            EventBus.getDefault().post(new AvatarEvent(false));
            return;
        }

        UserPreference.storeHeadUrl(headerResult.getData().getUrl());
        EventBus.getDefault().post(new AvatarEvent(true));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
