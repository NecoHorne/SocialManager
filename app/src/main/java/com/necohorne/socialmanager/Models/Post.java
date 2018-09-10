package com.necohorne.socialmanager.Models;

import java.util.HashMap;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.necohorne.socialmanager.Utilities.Constants;

@Entity(tableName = "posts")
public class Post implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    public int postKey;
    public HashMap sharedOn;
    //hash map of social keys found in Constants and social postID from post on social network as value;
    public int totalLikes;
    public int totalDislikes;
    public int totalViews;
    public int totalShares;
    public int totalComments;
    public String datePosted;

    //Social Media booleans from hash map
    public boolean facebook;
    public boolean twitter;
    public boolean instagram;
    public boolean linkedIn;
    public boolean youTube;
    public boolean googlePlus;
    public boolean pinterest;
    public boolean twitch;
    public boolean reddit;
    public boolean vimeo;
    public boolean patreon;

    //Social Media booleans from hash map
    public String facebookId;
    public String twitterId;
    public String instagramId;
    public String linkedInId;
    public String youTubeId;
    public String googlePlusId;
    public String pinterestId;
    public String twitchId;
    public String redditId;
    public String vimeoId;
    public String patreonId;

    //Likes
    public int facebookLikes;
    public int twitterLikes;
    public int instagramLikes;
    public int linkedInLikes;
    public int youTubeLikes;
    public int googlePlusLikes;
    public int redditLikes;
    public int vimeoLikes;
    public int patreonLikes;

    //Comments
    public int facebookComments;
    public int twitterComments;
    public int instagramComments;
    public int linkedInComments;
    public int youTubeComments;
    public int googlePlusComments;
    public int pinterestComments;
    public int twitchComments;
    public int redditComments;
    public int vimeoComments;
    public int patreonComments;

    //Dislikes
    public int youTubeDislikes;
    public int redditDislikes;

    //Shares
    public int facebookShares;
    public int twitterShares;
    public int instagramShares;
    public int linkedInShares;
    public int youTubeShares;
    public int googlePlusShares;
    public int pinterestShares;
    public int twitchShares;
    public int redditShares;
    public int vimeoShares;
    public int patreonShares;

    //Views
    public int facebookViews;
    public int twitterViews;
    public int instagramViews;
    public int linkedInViews;
    public int youTubeViews;
    public int googlePlusViews;
    public int pinterestViews;
    public int twitchViews;
    public int redditViews;
    public int vimeoViews;
    public int patreonViews;


    //Parcelable methods
    protected Post(Parcel in) {
        postKey = in.readInt();
        totalLikes = in.readInt();
        totalDislikes = in.readInt();
        totalViews = in.readInt();
        totalShares = in.readInt();
        totalComments = in.readInt();
        datePosted = in.readString();
        facebookLikes = in.readInt();
        twitterLikes = in.readInt();
        instagramLikes = in.readInt();
        linkedInLikes = in.readInt();
        youTubeLikes = in.readInt();
        googlePlusLikes = in.readInt();
        redditLikes = in.readInt();
        vimeoLikes = in.readInt();
        patreonLikes = in.readInt();
        facebookComments = in.readInt();
        twitterComments = in.readInt();
        instagramComments = in.readInt();
        linkedInComments = in.readInt();
        youTubeComments = in.readInt();
        googlePlusComments = in.readInt();
        pinterestComments = in.readInt();
        twitchComments = in.readInt();
        redditComments = in.readInt();
        vimeoComments = in.readInt();
        patreonComments = in.readInt();
        youTubeDislikes = in.readInt();
        redditDislikes = in.readInt();
        facebookShares = in.readInt();
        twitterShares = in.readInt();
        instagramShares = in.readInt();
        linkedInShares = in.readInt();
        youTubeShares = in.readInt();
        googlePlusShares = in.readInt();
        pinterestShares = in.readInt();
        twitchShares = in.readInt();
        redditShares = in.readInt();
        vimeoShares = in.readInt();
        patreonShares = in.readInt();
        facebookViews = in.readInt();
        twitterViews = in.readInt();
        instagramViews = in.readInt();
        linkedInViews = in.readInt();
        youTubeViews = in.readInt();
        googlePlusViews = in.readInt();
        pinterestViews = in.readInt();
        twitchViews = in.readInt();
        redditViews = in.readInt();
        vimeoViews = in.readInt();
        patreonViews = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postKey);
        dest.writeInt(totalLikes);
        dest.writeInt(totalDislikes);
        dest.writeInt(totalViews);
        dest.writeInt(totalShares);
        dest.writeInt(totalComments);
        dest.writeString(datePosted);
        dest.writeInt(facebookLikes);
        dest.writeInt(twitterLikes);
        dest.writeInt(instagramLikes);
        dest.writeInt(linkedInLikes);
        dest.writeInt(youTubeLikes);
        dest.writeInt(googlePlusLikes);
        dest.writeInt(redditLikes);
        dest.writeInt(vimeoLikes);
        dest.writeInt(patreonLikes);
        dest.writeInt(facebookComments);
        dest.writeInt(twitterComments);
        dest.writeInt(instagramComments);
        dest.writeInt(linkedInComments);
        dest.writeInt(youTubeComments);
        dest.writeInt(googlePlusComments);
        dest.writeInt(pinterestComments);
        dest.writeInt(twitchComments);
        dest.writeInt(redditComments);
        dest.writeInt(vimeoComments);
        dest.writeInt(patreonComments);
        dest.writeInt(youTubeDislikes);
        dest.writeInt(redditDislikes);
        dest.writeInt(facebookShares);
        dest.writeInt(twitterShares);
        dest.writeInt(instagramShares);
        dest.writeInt(linkedInShares);
        dest.writeInt(youTubeShares);
        dest.writeInt(googlePlusShares);
        dest.writeInt(pinterestShares);
        dest.writeInt(twitchShares);
        dest.writeInt(redditShares);
        dest.writeInt(vimeoShares);
        dest.writeInt(patreonShares);
        dest.writeInt(facebookViews);
        dest.writeInt(twitterViews);
        dest.writeInt(instagramViews);
        dest.writeInt(linkedInViews);
        dest.writeInt(youTubeViews);
        dest.writeInt(googlePlusViews);
        dest.writeInt(pinterestViews);
        dest.writeInt(twitchViews);
        dest.writeInt(redditViews);
        dest.writeInt(vimeoViews);
        dest.writeInt(patreonViews);
    }

    //Constructors
    @Ignore
    public Post (HashMap sharedOn, String datePosted){
        this.sharedOn = sharedOn;
        this.datePosted = datePosted;
        checkSocialMedia(sharedOn);
        setTotalComments();
        setTotalDislikes();
        setTotalLikes();
        setTotalShares();
        setTotalViews();
    }

    public Post(int postKey, HashMap sharedOn, int totalLikes, int totalDislikes, int totalViews, int totalShares, int totalComments, String datePosted) {
        this.postKey = postKey;
        this.sharedOn = sharedOn;
        this.totalLikes = totalLikes;
        this.totalDislikes = totalDislikes;
        this.totalViews = totalViews;
        this.totalShares = totalShares;
        this.totalComments = totalComments;
        this.datePosted = datePosted;
    }

    //Setters
    public void setPostKey(int postKey) {
        this.postKey = postKey;
    }

    public void setSharedOn(HashMap sharedOn) {
        //TODO
        this.sharedOn = sharedOn;
    }

    private void setTotalLikes() {
        this.totalLikes = getLikes();
    }

    private void setTotalDislikes() {
        this.totalDislikes = getDislikes();
    }

    private void setTotalViews() {
        this.totalViews = getViews();
    }

    private void setTotalShares() {
        this.totalShares = getShares();
    }

    private void setTotalComments() {
        this.totalComments = getComments();
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    //Getters Main metrics
    public int getPostKey() {
        return postKey;
    }

    public HashMap getSharedOn() {
        return sharedOn;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalDislikes() {
        return totalDislikes;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public int getTotalShares() {
        return totalShares;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public String getDatePosted() {
        return datePosted;
    }

    //Getters Specific metrics
    //Likes
    //TODO update methods to check post ID details with API
    public int getFacebookLikes() {
        return facebookLikes;
    }

    public int getTwitterLikes() {
        return twitterLikes;
    }

    public int getInstagramLikes() {
        return instagramLikes;
    }

    public int getLinkedInLikes() {
        return linkedInLikes;
    }

    public int getYouTubeLikes() {
        return youTubeLikes;
    }

    public int getGooglePlusLikes() {
        return googlePlusLikes;
    }

    public int getRedditLikes() {
        return redditLikes;
    }

    public int getVimeoLikes() {
        return vimeoLikes;
    }

    public int getPatreonLikes() {
        return patreonLikes;
    }

    //Comments
    //TODO update methods to check post ID details with API
    public int getFacebookComments() {
        return facebookComments;
    }

    public int getTwitterComments() {
        return twitterComments;
    }

    public int getInstagramComments() {
        return instagramComments;
    }

    public int getLinkedInComments() {
        return linkedInComments;
    }

    public int getYouTubeComments() {
        return youTubeComments;
    }

    public int getGooglePlusComments() {
        return googlePlusComments;
    }

    public int getPinterestComments() {
        return pinterestComments;
    }

    public int getTwitchComments() {
        return twitchComments;
    }

    public int getRedditComments() {
        return redditComments;
    }

    public int getVimeoComments() {
        return vimeoComments;
    }

    public int getPatreonComments() {
        return patreonComments;
    }

    //Dislikes
    //TODO update methods to check post ID details with API
    public int getYouTubeDislikes() {
        return youTubeDislikes;
    }

    public int getRedditDislikes() {
        return redditDislikes;
    }

    //Shares
    //TODO update methods to check post ID details with API
    public int getFacebookShares() {
        return facebookShares;
    }

    public int getTwitterShares() {
        return twitterShares;
    }

    public int getInstagramShares() {
        return instagramShares;
    }

    public int getLinkedInShares() {
        return linkedInShares;
    }

    public int getYouTubeShares() {
        return youTubeShares;
    }

    public int getGooglePlusShares() {
        return googlePlusShares;
    }

    public int getPinterestShares() {
        return pinterestShares;
    }

    public int getTwitchShares() {
        return twitchShares;
    }

    public int getRedditShares() {
        return redditShares;
    }

    public int getVimeoShares() {
        return vimeoShares;
    }

    public int getPatreonShares() {
        return patreonShares;
    }

    //Views
    //TODO update methods to check post ID details with API
    public int getFacebookViews() {
        return facebookViews;
    }

    public int getTwitterViews() {
        return twitterViews;
    }

    public int getInstagramViews() {
        return instagramViews;
    }

    public int getLinkedInViews() {
        return linkedInViews;
    }

    public int getYouTubeViews() {
        return youTubeViews;
    }

    public int getGooglePlusViews() {
        return googlePlusViews;
    }

    public int getPinterestViews() {
        return pinterestViews;
    }

    public int getTwitchViews() {
        return twitchViews;
    }

    public int getRedditViews() {
        return redditViews;
    }

    public int getVimeoViews() {
        return vimeoViews;
    }

    public int getPatreonViews() {
        return patreonViews;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postKey=" + postKey +
                ", sharedOn=" + sharedOn +
                ", totalLikes=" + totalLikes +
                ", totalDislikes=" + totalDislikes +
                ", totalViews=" + totalViews +
                ", totalShares=" + totalShares +
                ", totalComments=" + totalComments +
                ", datePosted='" + datePosted + '\'' +
                '}';
    }

    //Methods
    private int getLikes(){
        int total = 0;

        if(facebook){
            facebookLikes = getFacebookLikes();
            total += facebookLikes;
        }
        if(twitter){
            twitterLikes = getTwitterLikes();
            total += twitterLikes;
        }
        if(instagram){
            instagramLikes = getInstagramLikes();
            total += instagramLikes;
        }
        if(linkedIn){
            linkedInLikes = getLinkedInLikes();
            total += linkedInLikes;
        }
        if(youTube){
            youTubeLikes = getYouTubeLikes();
            total += youTubeLikes;
        }
        if(googlePlus){
            googlePlusLikes = getGooglePlusLikes();
            total += googlePlusLikes;
        }
        if(reddit){
            redditLikes = getRedditLikes();
            total += redditLikes;
        }
        if(vimeo){
            vimeoLikes = getVimeoLikes();
            total += vimeoLikes;
        }
        if(patreon){
            patreonLikes = getPatreonLikes();
            total += patreonLikes;
        }

        return total;
    }

    private int getComments(){
        int total = 0;

        if(facebook){
            facebookComments = getFacebookComments();
            total += facebookComments;
        }
        if(twitter){
            twitterComments = getTwitterComments();
            total += twitterComments;
        }
        if(instagram){
            instagramComments = getInstagramComments();
            total += instagramComments;
        }
        if(linkedIn){
            linkedInComments = getLinkedInComments();
            total += linkedInComments;
        }
        if(youTube){
            youTubeComments = getYouTubeComments();
            total += youTubeComments;
        }
        if(googlePlus){
            googlePlusComments = getGooglePlusComments();
            total += googlePlusComments;
        }
        if(pinterest){
            pinterestComments = getPinterestComments();
            total += pinterestComments;
        }
        if(twitch){
            twitchComments = getTwitchComments();
            total += twitchComments;
        }
        if(reddit){
            redditComments = getRedditComments();
            total += redditComments;
        }
        if(vimeo){
            vimeoComments = getVimeoComments();
            total += vimeoComments;
        }
        if(patreon){
            patreonComments = getPatreonComments();
            total += patreonComments;
        }
        return total;
    }

    private int getDislikes(){
        int total = 0;

        if(youTube){
            youTubeDislikes = getYouTubeDislikes();
            total += youTubeDislikes;
        }
        if(reddit){
            redditDislikes = getRedditDislikes();
            total += redditDislikes;
        }
        return total;
    }

    private int getShares(){
        int total = 0;

        if(facebook){
            facebookShares = getFacebookShares();
            total += facebookShares;
        }
        if(twitter){
            twitterShares = getTwitterShares();
            total += twitterShares;
        }
        if(instagram){
            instagramShares = getInstagramShares();
            total += instagramShares;
        }
        if(linkedIn){
            linkedInShares = getLinkedInShares();
            total += linkedInShares;
        }
        if(youTube){
            youTubeShares = getYouTubeShares();
            total += youTubeShares;
        }
        if(googlePlus){
            googlePlusShares = getGooglePlusShares();
            total += googlePlusShares;
        }
        if(pinterest){
            pinterestShares = getPinterestShares();
            total += pinterestShares;
        }
        if(twitch){
            twitchShares = getTwitchShares();
            total += twitchShares;
        }
        if(reddit){
            redditShares = getRedditShares();
            total += redditShares;
        }
        if(vimeo){
            vimeoShares = getVimeoShares();
            total += vimeoShares;
        }
        if(patreon){
            patreonShares = getPatreonShares();
            total += patreonShares;
        }
        return total;
    }

    private int getViews(){
        int total = 0;

        if(facebook){
            facebookViews = getFacebookViews();
            total += facebookViews;
        }
        if(twitter){
            twitterViews = getTwitterViews();
            total += twitterViews;
        }
        if(instagram){
            instagramViews = getInstagramViews();
            total += instagramViews;
        }
        if(linkedIn){
            linkedInViews = getLinkedInViews();
            total += linkedInViews;
        }
        if(youTube){
            youTubeViews = getYouTubeViews();
            total += youTubeViews;
        }
        if(googlePlus){
            googlePlusViews = getGooglePlusViews();
            total += googlePlusViews;
        }
        if(pinterest){
            pinterestViews = getPinterestViews();
            total += pinterestViews;
        }
        if(twitch){
            twitchViews = getTwitchViews();
            total += twitchViews;
        }
        if(reddit){
            redditViews = getRedditViews();
            total += redditViews;
        }
        if(vimeo){
            vimeoViews = getVimeoViews();
            total += vimeoViews;
        }
        if(patreon){
            patreonViews = getPatreonViews();
            total += patreonViews;
        }
        return total;
    }

    private void checkSocialMedia(HashMap hashMap){
        //method check hash map in constructor to ascertain on which social media post was shared.

        facebook = hashMap.containsKey(Constants.FACEBOOK);
        if(facebook){
            facebookId = (String) hashMap.get(Constants.FACEBOOK);
        }
        twitter = hashMap.containsKey(Constants.TWITTER);
        if(twitter){
            twitterId = (String) hashMap.get(Constants.TWITTER);
        }
        instagram = hashMap.containsKey(Constants.INSTAGRAM);
        if(instagram){
            instagramId = (String) hashMap.get(Constants.INSTAGRAM);
        }
        linkedIn = hashMap.containsKey(Constants.LINKEDIN);
        if(linkedIn){
            linkedInId = (String) hashMap.get(Constants.LINKEDIN);
        }
        youTube = hashMap.containsKey(Constants.YOUTUBE);
        if(youTube){
            youTubeId = (String) hashMap.get(Constants.YOUTUBE);
        }
        googlePlus = hashMap.containsKey(Constants.GOOGLE);
        if(googlePlus){
            googlePlusId = (String) hashMap.get(Constants.GOOGLE);
        }
        pinterest = hashMap.containsKey(Constants.PINTEREST);
        if(pinterest){
            pinterestId = (String) hashMap.get(Constants.PINTEREST);
        }
        twitch = hashMap.containsKey(Constants.TWITCH);
        if(twitch){
            twitchId = (String) hashMap.get(Constants.TWITCH);
        }
        reddit = hashMap.containsKey(Constants.REDDIT);
        if(reddit){
            redditId = (String) hashMap.get(Constants.REDDIT);
        }
        vimeo = hashMap.containsKey(Constants.VIMEO);
        if(vimeo){
            vimeoId = (String) hashMap.get(Constants.VIMEO);
        }
        patreon = hashMap.containsKey(Constants.PATREON);
        if(patreon){
            patreonId = (String) hashMap.get(Constants.PATREON);
        }
    }

}
