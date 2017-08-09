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

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.muhammadelsayed.g_books.databinding.ActivityBookDetailsBinding;

/*
*
*                    in this CLASS
*       I'm going Update the Views with the Data
* */



public class BookDetailsActivity extends AppCompatActivity {

    Bundle metadata;
    ActivityBookDetailsBinding bookInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_details);

        Toolbar toolbar = bookInfoBinding.toolbar;
        setSupportActionBar(toolbar);

        bookInfoBinding.toolbarLayout.setTitleEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        metadata = getIntent().getBundleExtra("metadata");
        updateViews();
    }

    //this method will update all the views of each book returned by the search task.
    private void updateViews() {

        Glide.with(this).load(metadata.getString(BookData.IMAGE)).into(bookInfoBinding.cover);

        String unknown = getString(R.string.unknown);

        bookInfoBinding.bookTitle.setText(metadata.containsKey(BookData.TITLE) ? metadata.getString(BookData.TITLE) : unknown);
        bookInfoBinding.bookContents.publishedDate.append(metadata.containsKey(BookData.PUBLISHED_DATE) ? metadata.getString(BookData.PUBLISHED_DATE) : unknown);
        bookInfoBinding.bookContents.publisher.append(metadata.containsKey(BookData.PUBLISHER) ? metadata.getString(BookData.PUBLISHER) : unknown);
        bookInfoBinding.bookContents.pages.append(metadata.containsKey(BookData.PAGES) ? metadata.getString(BookData.PAGES) : unknown);
        if (metadata.containsKey(BookData.AUTHORS)) {
            String author = "\n";
            String[] authors = metadata.getStringArray(BookData.AUTHORS);
            for (int i = 0; i < authors.length; i++) {
                String singleAuthor = authors[i];
                if (TextUtils.isEmpty(singleAuthor)) {
                    continue;
                }
                author += singleAuthor.concat(i == authors.length - 1 ? "" : "\n");
                bookInfoBinding.bookContents.author.append(author);
            }
        }
        if (metadata.containsKey(BookData.CATEGORIES)) {
            String category = "\n";
            String[] categories = metadata.getStringArray(BookData.CATEGORIES);
            for (int i = 0; i < categories.length; i++) {
                String singleCategory = categories[i];
                if (TextUtils.isEmpty(singleCategory)) {
                    continue;
                }
                category += singleCategory.concat(i == categories.length - 1 ? "" : "\n");
                bookInfoBinding.bookContents.category.append(category);
            }
        }

        if (metadata.containsKey(BookData.SUBTITLE)) {
            bookInfoBinding.subtitle.setText(metadata.getString(BookData.SUBTITLE));
        } else {
            bookInfoBinding.subtitle.setVisibility(View.GONE);
        }
        if (metadata.containsKey(BookData.DESCRIPTION)) {
            bookInfoBinding.bookContents.description.setText(metadata.getString(BookData.DESCRIPTION));
        }
    }

}
