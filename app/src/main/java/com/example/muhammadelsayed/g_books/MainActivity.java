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

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.muhammadelsayed.g_books.databinding.ActivityMainBinding;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements searchQuery.SearchListener {

    private List<Volume> volumeList;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        searchFragment searchFragment = (searchFragment) getSupportFragmentManager().findFragmentByTag("searchFragment");
        if (searchFragment != null) {
            volumeList = searchFragment.getVolumeList();
            binding.searchView.setQuery(searchFragment.getLatestQuery(), false);
        } else {
            volumeList = new ArrayList<>();
            searchFragment = new searchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(searchFragment, "searchFragment")
                    .commit();
        }

        RecyclerView recyclerView = binding.booksGrid;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5);
        BookAdapter adapter = new BookAdapter(volumeList, gridLayoutManager.getSpanCount());

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFragment searchFragment = (searchFragment) getSupportFragmentManager().findFragmentByTag("searchFragment");
                if (searchFragment != null) {
                    searchFragment.searchBooks(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onSearching() {
        volumeList.clear();
        binding.booksGrid.getAdapter().notifyDataSetChanged();
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResult(List<Volume> volumes) {
        binding.loadingView.setVisibility(View.GONE);
        volumeList.addAll(volumes);
        binding.booksGrid.getAdapter().notifyDataSetChanged();
    }

}
