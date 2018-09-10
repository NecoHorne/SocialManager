package com.necohorne.socialmanager.Models;

public class InstagramUser {

    public String id;
    public String username;
    public String full_name;
    public String profile_picture;
    public String bio;
    public String website;
    public boolean is_business;
    public int media;
    public int follows;
    public int followed_by;

    public InstagramUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isIs_business() {
        return is_business;
    }

    public void setIs_business(boolean is_business) {
        this.is_business = is_business;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowed_by() {
        return followed_by;
    }

    public void setFollowed_by(int followed_by) {
        this.followed_by = followed_by;
    }

    @Override
    public String toString() {
        return "InstagramUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", full_name='" + full_name + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", bio='" + bio + '\'' +
                ", website='" + website + '\'' +
                ", is_business=" + is_business +
                ", media=" + media +
                ", follows=" + follows +
                ", followed_by=" + followed_by +
                '}';
    }
}
