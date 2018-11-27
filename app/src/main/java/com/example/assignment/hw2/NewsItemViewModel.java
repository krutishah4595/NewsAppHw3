package com.example.assignment.hw2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


public class NewsItemViewModel extends AndroidViewModel {
    
    NewsStoryRepository newsStoryRepository;
    NewsItemDao newsItemDao;

    public NewsItemViewModel(Application application) {
        super(application);
        this.newsStoryRepository = new NewsStoryRepository(application);
        newsItemDao =newsStoryRepository.getnewsStoryDao();
    }

    public void getDataFromServer(){
        newsStoryRepository.addDataFromServerToLocal();
    }

    public MutableLiveData<List<NewsItem>> getNewsSories(){
       return newsStoryRepository.getDataFromLocal();
    }


}
