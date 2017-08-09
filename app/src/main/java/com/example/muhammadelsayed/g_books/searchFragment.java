package com.example.muhammadelsayed.g_books;

/*
* Copyright [2017] [Muhammad Elsayed]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
* */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Elsayed on 8/8/2017.
 */

/*
*                      in This CLASS
*       I'll implement the searchFragment Lifecycle.
*
* */

public class searchFragment extends Fragment implements searchTask.SearchListener {

    private boolean searching;

    private searchTask searchTask;
    private searchTask.SearchListener searchListener;

    private String latestQuery;
    private List<Volume> volumeList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        searchListener = (searchTask.SearchListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Saving the Fragment State
        setRetainInstance(true);
    }


    public void searchBooks(String query) {
        if (query.equalsIgnoreCase(latestQuery)) {
            return;
        }
        if (searchTask != null) {
            searchTask.cancel(true);
        }
        latestQuery = query;
        searchTask = new searchTask();
        searchTask.setSearchListener(this);
        searchTask.execute(query);
    }

    @Override
    public void onSearching() {
        searching = true;
        volumeList.clear();
        searchListener.onSearching();
    }

    @Override
    public void onResult(List<Volume> volumes) {
        searching = false;
        volumeList = volumes;
        searchListener.onResult(volumeList);
    }


    public String getLatestQuery() {
        return latestQuery;
    }

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    public boolean isSearching() {
        return searching;
    }
}
