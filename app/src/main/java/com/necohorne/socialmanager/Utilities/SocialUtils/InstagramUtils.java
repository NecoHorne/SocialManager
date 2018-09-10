package com.necohorne.socialmanager.Utilities.SocialUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.necohorne.socialmanager.Activities.WebLoginActivity;
import com.necohorne.socialmanager.Models.InstagramUser;
import com.necohorne.socialmanager.Utilities.Constants;
import com.necohorne.socialmanager.Utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;
import static com.necohorne.socialmanager.Utilities.Constants.INSTAGRAM_URL_LAUNCH;

public class InstagramUtils{

    private static final String TAG = InstagramUtils.class.getSimpleName();

    private static final String OAUTH_URL = "oauth/authorize/";
    private static final String CLIENT_URL = "?client_id=";
    private static final String REDIRECT_URL = "&redirect_uri=";
    private static final String RESPONSE_TOKEN_URL = "&response_type=token";
    private static final String USER_URL = "v1/users/self/?access_token=";
    private static final String DATA = "data";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String FULLNAME = "full_name";
    private static final String PROFILEPICTURE = "profile_picture";
    private static final String BIO = "bio";
    private static final String WEBSITE = "website";
    private static final String ISBUSINESS = "is_business";
    private static final String COUNTS = "counts";
    private static final String MEDIA = "media";
    private static final String FOLLOWS = "follows";
    private static final String FOLLOWEDBY = "followed_by";


    private static URL buildInstagramAuthUrl() {
        Uri builtUri = Uri.parse(Constants.INSTAGRAM_BASE_URL
                + OAUTH_URL + CLIENT_URL
                + Constants.INSTAGRAM_CLIENT_ID
                + REDIRECT_URL
                + Constants.INSTAGRAM_CALLBACK_URL
                + RESPONSE_TOKEN_URL);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static URL buildInstagramUserUrl(Context context) {
        Uri builtUri = Uri.parse(Constants.INSTAGRAM_BASE_URL
                + USER_URL
                + getInstagramToken(context));

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static InstagramUser buildInstagramUser(String json){
        if(json != null){
         InstagramUser instagramUser = new InstagramUser();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject userJson = jsonObject.optJSONObject(DATA);
                instagramUser.setId(userJson.optString(ID));
                instagramUser.setUsername(userJson.optString(USERNAME));
                instagramUser.setFull_name(userJson.optString(FULLNAME));
                instagramUser.setProfile_picture(userJson.optString(PROFILEPICTURE));
                instagramUser.setBio(userJson.optString(BIO));
                instagramUser.setWebsite(userJson.optString(WEBSITE));
                instagramUser.setIs_business(userJson.optBoolean(ISBUSINESS));

                JSONObject countsJson = userJson.optJSONObject(COUNTS);
                instagramUser.setMedia(countsJson.optInt(MEDIA));
                instagramUser.setFollows(countsJson.optInt(FOLLOWS));
                instagramUser.setFollowed_by(countsJson.optInt(FOLLOWEDBY));

                return instagramUser;

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static InstagramUser getInstagramUser(Context context){
        //this method makes a network call and should be called in a separate thread.
        try {
            if(getInstagramToken(context) != null){
                return buildInstagramUser(NetworkUtils.getResponseFromHttpUrl(buildInstagramUserUrl(context)));
            }else {
                return null;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void launchInstagramLogin(Context context){
        Intent instagramIntent = new Intent(context, WebLoginActivity.class);
        instagramIntent.putExtra(INSTAGRAM_URL_LAUNCH, String.valueOf(buildInstagramAuthUrl()));
        context.startActivity(instagramIntent);
    }

    public static void saveInstagramUserToken(String token, Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.INSTAGRAM_ACCESS_TOKEN, token);
        Log.d(TAG, "instagramUserToken: Instagram access token saved to shared prefs");
        editor.commit();
    }

    public static String getInstagramToken(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        if(pref.contains(Constants.INSTAGRAM_ACCESS_TOKEN)){
            return pref.getString(Constants.INSTAGRAM_ACCESS_TOKEN, null);
        }
        Log.d(TAG, "getInstagramToken: No instagram access token saved");
        return null;
    }

    public static void logout(Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.INSTAGRAM_ACCESS_TOKEN);
        Log.d(TAG, "instagramUserToken removed");
        editor.commit();
    }
}
