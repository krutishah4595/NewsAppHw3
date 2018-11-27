package com.example.assignment.hw2;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class NewsStoryRepository {

    private NewsItemDao newsStoryDao;
    private NewsStoryDataBase newsStoryDataBase;
    Context ctx;

    public NewsStoryRepository(Context ctx) {
        newsStoryDataBase = NewsStoryDataBase.getDatabase(ctx);
        newsStoryDao = newsStoryDataBase.newsStoryDao();
        this.ctx = ctx;
    }

    public MutableLiveData<List<NewsItem>> getDataFromLocal() {
        try {
            return new loadDataFromLocal().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public NewsItemDao getnewsStoryDao() {
        return newsStoryDao;
    }

    private class loadDataFromLocal extends AsyncTask<Void, Void, MutableLiveData<List<NewsItem>>> {

        @Override
        protected MutableLiveData<List<NewsItem>> doInBackground(Void... voids) {
            MutableLiveData<List<NewsItem>> listMutableLiveData = new MutableLiveData<>();
            listMutableLiveData.postValue(newsStoryDao.loadAllNewsItemWithoutLiveData());
            return listMutableLiveData;
        }
    }


    public void addDataFromServerToLocal() {
        new getNewsApiResponse(newsStoryDao).execute();
    }

    public static class getNewsApiResponse extends AsyncTask<Void, Void, Void> {

        String response;
        NewsItemDao newsStoryDao;

        public getNewsApiResponse(NewsItemDao newsStoryDao) {
            this.newsStoryDao = newsStoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                response = NetworkUtils.getResponseFromHttpUrl
                        (new URL(NetworkUtils.appendURL(NetworkUtils.BASEURL, NetworkUtils.APIKEY)));

                if (JsonUtils.parseData(response).size() != 0 && JsonUtils.parseData(response) != null) {
                    newsStoryDao.clearAll();
                    newsStoryDao.insert(JsonUtils.parseData(response));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
