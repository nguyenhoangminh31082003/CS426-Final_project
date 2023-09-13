package com.example.cs426_final_project.models.posts;

import okhttp3.MultipartBody;

public class CreatePostRequest {

    public String title;
    public String body;
    public int rating;
    public String image_name;
    public String image_base64;

    public CreatePostRequest(
            final String title,
            final String body,
            final int rating,
            final String image_name,
            final String image_base64
    ) {
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.image_base64 = image_base64;
        this.image_name = image_name;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getRating() {
        return rating;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getImage_base64() {
        return image_base64;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }
}
