package com.example.dell.growup.utils;

import android.os.AsyncTask;

/**
 * Created by dell on 2017/10/3.
 */

public class AsyncTaskLoader extends AsyncTask<IAsyncCallback, Integer, Boolean> {

    private IAsyncCallback[] params;

    @Override
    protected Boolean doInBackground(IAsyncCallback... params) {
        this.params = params;
        int count = params.length;
        for(int i = 0; i < count; i++){
            params[i].workToDo();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        for (IAsyncCallback param : this.params) {
            param.onComplete();
        }
    }
}
