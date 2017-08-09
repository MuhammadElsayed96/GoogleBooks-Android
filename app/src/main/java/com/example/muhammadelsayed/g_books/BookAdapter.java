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

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.api.services.books.model.Volume;

import java.util.List;

/**
 * Created by Muhammad Elsayed on 8/8/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {

    private final int spanCount;
    private List<Volume> volumes;

    public BookAdapter(List<Volume> volumes, int spanCount) {
        this.volumes = volumes;
        this.spanCount = spanCount;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookHolder(parent);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        holder.setSpanCount(spanCount);
        holder.setVolume(volumes.get(position));
    }

    @Override
    public long getItemId(int position) {
        return volumes.get(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return volumes.size();
    }
}
