package com.example.assignment.hw2;

import android.content.Intent;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class FireBaseJobService extends JobService {
   
    String mTitle = "News Applicaion", message = "News Updating...";
    Intent intent;

    @Override
    public boolean onStartJob(final JobParameters job) {
        intent = new Intent(getApplicationContext(), Intentservice.class);
        new NewsUpdatingTask(intent, job).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    public class NewsUpdatingTask extends AsyncTask<Void, Void, String> {

        Intent intent;
        NotificationUtility notificationUtility;
        NewsStoryRepository newsItemRepository;
        JobParameters job;

        NewsUpdatingTask(Intent intent, JobParameters job) {
            this.intent = intent;
            this.job = job;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... voids) {
            notificationUtility = new NotificationUtility(FireBaseJobService.this);
            notificationUtility.showNotification(mTitle, message, intent);
            newsItemRepository = new NewsStoryRepository(getApplicationContext());
            newsItemRepository.addDataFromServerToLocal();
            return "Updating...";
        }
        
        @Override
        protected void onPostExecute(String s) {
            jobFinished(job, false);
        }
    }
}
