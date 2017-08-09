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

    private void updateViews() {

        Glide.with(this).load(metadata.getString(Book.IMAGE)).into(bookInfoBinding.cover);

        String unknown = getString(R.string.unknown);

        bookInfoBinding.bookTitle.setText(metadata.containsKey(Book.TITLE) ? metadata.getString(Book.TITLE) : unknown);
        bookInfoBinding.bookContents.publishedDate.append(metadata.containsKey(Book.PUBLISHED_DATE) ? metadata.getString(Book.PUBLISHED_DATE) : unknown);
        bookInfoBinding.bookContents.publisher.append(metadata.containsKey(Book.PUBLISHER) ? metadata.getString(Book.PUBLISHER) : unknown);
        bookInfoBinding.bookContents.pages.append(metadata.containsKey(Book.PAGES) ? metadata.getString(Book.PAGES) : unknown);
        if (metadata.containsKey(Book.AUTHORS)) {
            String author = "\n";
            String[] authors = metadata.getStringArray(Book.AUTHORS);
            for (int i = 0; i < authors.length; i++) {
                String singleAuthor = authors[i];
                if (TextUtils.isEmpty(singleAuthor)) {
                    continue;
                }
                author += singleAuthor.concat(i == authors.length - 1 ? "" : "\n");
                bookInfoBinding.bookContents.author.append(author);
            }
        }
        if (metadata.containsKey(Book.CATEGORIES)) {
            String category = "\n";
            String[] categories = metadata.getStringArray(Book.CATEGORIES);
            for (int i = 0; i < categories.length; i++) {
                String singleCategory = categories[i];
                if (TextUtils.isEmpty(singleCategory)) {
                    continue;
                }
                category += singleCategory.concat(i == categories.length - 1 ? "" : "\n");
                bookInfoBinding.bookContents.category.append(category);
            }
        }

        if (metadata.containsKey(Book.SUBTITLE)) {
            bookInfoBinding.subtitle.setText(metadata.getString(Book.SUBTITLE));
        } else {
            bookInfoBinding.subtitle.setVisibility(View.GONE);
        }
        if (metadata.containsKey(Book.DESCRIPTION)) {
            bookInfoBinding.bookContents.description.setText(metadata.getString(Book.DESCRIPTION));
        }
    }

}
