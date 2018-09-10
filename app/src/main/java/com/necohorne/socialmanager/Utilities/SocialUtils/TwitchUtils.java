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

public class TwitchUtils {

    private static final String TAG = TwitchUtils.class.getSimpleName();

    private static final String TWITCH_AUTH_URL = "oauth2/authorize?";
    private static final String TWITCH_CLIENT_URL = "client_id=";
    private static final String TWITCH_REDIRECT_URL = "&redirect_uri=";
    private static final String TWITCH_RESPONSE_URL = "&response_type=token";
    private static final String TWITCH_SCOPE_URL = "&scope=";
    public static final String TWITCH_STATE_URL = "&state";
    private static final String SCOPE1 = "analytics:read:extensions";
    private static final String SCOPE2 = "analytics:read:games";
    private static final String SCOPE3 = "bits:read";
    private static final String SCOPE4 = "clips:edit";
    private static final String SCOPE5 = "user:edit";
    private static final String SCOPE6 = "user:edit:broadcast";
    private static final String SCOPE7 = "user:read:broadcast";
    private static final String SCOPE8 = "user:read:email";

    private static URL buildTwitchAuthUrl() {
        Uri builtUri = Uri.parse(Constants.TWITCH_API_BASE_URL
                + TWITCH_AUTH_URL
                + TWITCH_CLIENT_URL
                + Constants.TWITCH_CLIENT_ID
                + TWITCH_REDIRECT_URL
                + Constants.REDIRECT
                + TWITCH_RESPONSE_URL
                + TWITCH_SCOPE_URL
                + SCOPE1 + "+" + SCOPE2 + "+" + SCOPE3 + "+" + SCOPE4 + "+" + SCOPE5 + "+" + SCOPE6 + "+" + SCOPE7 + "+" + SCOPE8);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void launchTwitchLogin(Context context){
        Intent twitchIntent = new Intent(context, WebLoginActivity.class);
        twitchIntent.putExtra(Constants.TWITCH_URL_LAUNCH, String.valueOf(buildTwitchAuthUrl()));
        context.startActivity(twitchIntent);
    }

    public static void saveTwitchUserToken(String token, Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.TWITCH_ACCESS_TOKEN, token);
        Log.d(TAG, "saveTwitchUserToken: Twitch access token saved to shared prefs");
        editor.commit();
    }

    public static String getTwitchToken(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        if(pref.contains(Constants.TWITCH_ACCESS_TOKEN)){
            return pref.getString(Constants.TWITCH_ACCESS_TOKEN, null);
        }
        Log.d(TAG, "getTwitchToken: No Twitch access token saved");
        return null;
    }

    public static void logout(Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.TWITCH_ACCESS_TOKEN);
        Log.d(TAG, "twitchUserToken removed");
        editor.commit();
    }
}
