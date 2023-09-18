package com.example.cs426_final_project;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs426_final_project.utilities.ImageUtilityClass;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<String> imageLinks;

    public AlbumAdapter(final List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public AlbumAdapter() {
        this.imageLinks = new ArrayList<String>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            final int viewType
    ) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_of_album_item, parent, false);

        return new AlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            final int position) {

        final String imageLink = this.imageLinks.get(position);

        if (imageLink.startsWith("http")) {
            ImageUtilityClass
                    .Companion
                    .loadSquareImageViewFromUrl(
                            holder.image,
                            imageLink,
                            100,
                            16
                    );
        } else {
            Uri uri = Uri.parse(imageLink);
            holder.image.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return imageLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.the_only_child_view_in_album_item);
        }

    }



}
