package com.example.dell.growup.component.avatar.job_and_event;

/**
 * Created by dell on 2017/10/5.
 */

public class AvatarEvent {

    public boolean isSucessed;

    public AvatarEvent(boolean isSucessed){
        this.isSucessed = isSucessed;
    }

    public boolean isSucess(){
        return isSucessed;
    }
}
