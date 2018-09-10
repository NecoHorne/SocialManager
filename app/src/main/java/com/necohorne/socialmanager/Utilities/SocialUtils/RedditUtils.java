package com.necohorne.socialmanager.Utilities.SocialUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.necohorne.socialmanager.Activities.WebLoginActivity;
import com.necohorne.socialmanager.Utilities.Constants;

import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class RedditUtils {

    private static final String TAG = RedditUtils.class.getSimpleName();

    private static final String AUTH_URL = "authorize";
    private static final String CLIENT_URL = "?client_id=";
    private static final String RESPONSE_URL = "&response_type=";
    private static final String RESPONSE_TOKEN = "token";
    private static final String STATE_URL = "&state=";
    private static final String STATE_STRING = "19n89h04pj12";
    private static final String REDIRECT_URL = "&redirect_uri=";
    private static final String SCOPE_URL = "&scope=";
    private static final String SCOPE1 = "identity";
    private static final String SCOPE2 = "flair";
    private static final String SCOPE3 = "history";
    private static final String SCOPE4 = "read";
    private static final String SCOPE5 = "modtraffic";

    private static URL buildRedditAuthUrl() {
        Uri builtUri = Uri.parse(Constants.REDDIT_API_BASE_URL
                + AUTH_URL
                + CLIENT_URL
                + Constants.REDDIT_API_KEY
                + RESPONSE_URL
                + RESPONSE_TOKEN
                + STATE_URL
                + STATE_STRING
                + REDIRECT_URL
                + Constants.REDIRECT
                + SCOPE_URL + SCOPE1 + "+" + SCOPE2 + "+" + SCOPE3 + "+" + SCOPE4 + "+" + SCOPE5);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void launchRedditLogin(Context context){
        Intent redditIntent = new Intent(context, WebLoginActivity.class);
        redditIntent.putExtra(Constants.REDDIT_URL_LAUNCH, String.valueOf(buildRedditAuthUrl()));
        context.startActivity(redditIntent);
    }

    public static void saveRedditUserToken(String token, Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.REDDIT_ACCESS_TOKEN, token);
        Log.d(TAG, "saveRedditUserToken: Reddit access token saved to shared prefs");
        editor.commit();
    }

    public static String getRedditToken(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        if(pref.contains(Constants.REDDIT_ACCESS_TOKEN)){
            return pref.getString(Constants.REDDIT_ACCESS_TOKEN, null);
        }
        Log.d(TAG, "getRedditToken: No Reddit access token saved");
        return null;
    }

    public static void logout(Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.REDDIT_ACCESS_TOKEN);
        Log.d(TAG, "redditUserToken removed");
        editor.commit();
    }
}
