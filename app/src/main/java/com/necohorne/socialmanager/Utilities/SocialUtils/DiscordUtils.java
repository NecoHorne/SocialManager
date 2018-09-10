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
import static com.necohorne.socialmanager.Utilities.Constants.DISCORD_URL_LAUNCH;

public class DiscordUtils {

    private static final String TAG = DiscordUtils.class.getSimpleName();

    private static final String OAUTH_URL = "oauth2/authorize";
    private static final String CLIENT_URL = "&client_id=";
    private static final String RESPONSE_URL = "?response_type=";
    private static final String TOKEN_URL = "token";
    private static final String STATE_URL = "&state=";
    private static final String STATE_STRING = "19n89h04pj12";
    private static final String SCOPE_URL = "&scope=";
    private static final String SCOPES= "identify%20connections%20guilds";

    private static URL buildDiscordAuthUrl() {
        Uri builtUri = Uri.parse(Constants.DISCORD_API_BASE_URL
                + OAUTH_URL
                + RESPONSE_URL
                + TOKEN_URL
                + CLIENT_URL
                + Constants.DISCORD_CLIENT_ID
                + STATE_URL
                + STATE_STRING
                + SCOPE_URL
                + SCOPES);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void launchDiscordLogin(Context context){
        Intent discordIntent = new Intent(context, WebLoginActivity.class);
        discordIntent.putExtra(DISCORD_URL_LAUNCH, String.valueOf(buildDiscordAuthUrl()));
        context.startActivity(discordIntent);
    }

    public static void saveDiscordUserToken(String token, Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.DISCORD_ACCESS_TOKEN, token);
        Log.d(TAG, "discordUserToken: Discord access token saved to shared prefs");
        editor.commit();
    }

    public static String getDiscordToken(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        if(pref.contains(Constants.DISCORD_ACCESS_TOKEN)){
            return pref.getString(Constants.DISCORD_ACCESS_TOKEN, null);
        }
        Log.d(TAG, "getDiscordToken: No discord access token saved");
        return null;
    }

    public static void logout(Context context){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.TOKEN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(Constants.DISCORD_ACCESS_TOKEN);
        Log.d(TAG, "discord User Token removed");
        editor.commit();
    }

}
