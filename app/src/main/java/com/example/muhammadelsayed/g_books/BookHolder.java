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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.api.services.books.model.Volume;

/**
 * Created by Muhammad Elsayed on 8/8/2017.
 */


    /*
    * IN THIS CALSS I'M GOING TO:
    * Parsing the Response of the JSON
    * */

public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Volume volume;
    private int spanCount;

    public BookHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_book, viewGroup, false));
        itemView.setOnClickListener(this);
    }

    public void setVolume(Volume volume) {
        this.volume = volume;

        System.out.println(volume.getVolumeInfo().getInfoLink());

        int approximateWidth = 300;
        int approximateHeight = 400;

        DisplayMetrics displayMetrics = itemView.getContext().getResources().getDisplayMetrics();

        int screenWidth = displayMetrics.widthPixels;

        int width = screenWidth / spanCount;
        int height = (approximateHeight * width) / approximateWidth;

        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = width;
        params.height = height;
        itemView.setLayoutParams(params);
        itemView.invalidate();

        Volume.VolumeInfo.ImageLinks imageLinks = volume.getVolumeInfo().getImageLinks();

        if (imageLinks != null) {
            String medium = imageLinks.getMedium();
            String large = imageLinks.getLarge();
            String small = imageLinks.getSmall();
            String thumbnail = imageLinks.getThumbnail();
            String smallThumbnail = imageLinks.getSmallThumbnail();

            String imageLink = "";
            if (large != null) {
                imageLink = large;
            } else if (medium != null) {
                imageLink = medium;
            } else if (small != null) {
                imageLink = small;
            } else if (thumbnail != null) {
                imageLink = thumbnail;
            } else if (smallThumbnail != null) {
                imageLink = smallThumbnail;
            }

            imageLink = imageLink.replace("edge=curl", "");
            System.out.println(imageLink);

            Glide.with(itemView.getContext())
                    .load(imageLink)
                    .into((ImageView) itemView);
        } else {
            System.err.println("No images ??");
        }

    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void onClick(View v) {

        Bundle metadata = new Bundle();


        Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
        Volume.SaleInfo saleInfo = volume.getSaleInfo();

        if (volumeInfo != null) {
            Volume.VolumeInfo.ImageLinks imageLinks = volumeInfo.getImageLinks();
            if (volumeInfo.getTitle() != null) {
                metadata.putString(Book.TITLE, volumeInfo.getTitle());
            }

            if (volumeInfo.getSubtitle() != null) {
                metadata.putString(Book.SUBTITLE, volumeInfo.getSubtitle());
            }
            if (volumeInfo.getDescription() != null) {
                metadata.putString(Book.DESCRIPTION, volumeInfo.getDescription());
            }
            if (volumeInfo.getPublisher() != null) {
                metadata.putString(Book.PUBLISHER, volumeInfo.getPublisher());
            }
            if (volumeInfo.getAuthors() != null) {
                metadata.putStringArray(Book.AUTHORS, volumeInfo.getAuthors().toArray(new String[volumeInfo.getAuthors().size()]));
            }
            if (volumeInfo.getCategories() != null) {
                metadata.putStringArray(Book.CATEGORIES, volumeInfo.getCategories().toArray(new String[volumeInfo.getCategories().size()]));
            }
            if (volumeInfo.getPageCount() != null) {
                metadata.putString(Book.PAGES, String.valueOf(volumeInfo.getPageCount()));
            }
            if (volumeInfo.getPublishedDate() != null) {
                metadata.putString(Book.PUBLISHED_DATE, volumeInfo.getPublishedDate());
            }
            if (saleInfo != null) {
                Volume.SaleInfo.RetailPrice retailPrice = saleInfo.getRetailPrice();
                Volume.SaleInfo.ListPrice listPrice = saleInfo.getListPrice();
                if (retailPrice != null) {
                    metadata.putDouble(Book.RETAIL_PRICE, retailPrice.getAmount());
                    metadata.putString(Book.RETAIL_PRICE_CURRENCY_CODE, retailPrice.getCurrencyCode());
                }
                if (listPrice != null) {
                    metadata.putDouble(Book.LIST_PRICE, listPrice.getAmount());
                    metadata.putString(Book.LIST_PRICE_CURRENCY_CODE, listPrice.getCurrencyCode());
                }
            }

            if (imageLinks != null) {
                String image = null;
                if (imageLinks.getExtraLarge() != null) {
                    image = imageLinks.getExtraLarge();
                } else if (imageLinks.getLarge() != null) {
                    image = imageLinks.getLarge();
                } else if (imageLinks.getMedium() != null) {
                    image = imageLinks.getMedium();
                } else if (imageLinks.getSmall() != null) {
                    image = imageLinks.getSmall();
                } else if (imageLinks.getThumbnail() != null) {
                    image = imageLinks.getThumbnail();
                } else if (imageLinks.getSmallThumbnail() != null) {
                    image = imageLinks.getSmallThumbnail();
                }
                if (image != null) {
                    metadata.putString(Book.IMAGE, image);
                }
            }
        }

        Context context = itemView.getContext();
        context.startActivity(new Intent(context, BookDetailsActivity.class).putExtra("metadata", metadata));
    }
}
