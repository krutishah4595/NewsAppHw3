package com.example.assignment.hw2;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;


public class Intentservice extends IntentService {
    FirebaseJobDispatcher firebaseJobDispatcher;
    NotificationUtility  notificationUtility;
    public Intentservice() {
        super("not doing anything");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));
        firebaseJobDispatcher.cancel("jobServiceTag");
        notificationUtility = new NotificationUtility(Intentservice.this);
        notificationUtility.removeNoti();
    }
}
