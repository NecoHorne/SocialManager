package com.necohorne.socialmanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.necohorne.socialmanager.R;
import com.necohorne.socialmanager.Utilities.Constants;
import com.necohorne.socialmanager.Utilities.SocialUtils.DiscordUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.InstagramUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.RedditUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.TwitchUtils;

public class WebLoginActivity extends AppCompatActivity {

    private WebView loginWebview;
    private static final String TAG = WebLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        loginWebview = findViewById(R.id.web_login_web_view);
        Intent intent = getIntent();
        instagramLogin(intent);
        twitchLogin(intent);
        redditLogin(intent);
        discordLogin(intent);
    }

    public void instagramLogin(Intent intent){
        if(intent.hasExtra(Constants.INSTAGRAM_URL_LAUNCH)){
            String url = intent.getStringExtra(Constants.INSTAGRAM_URL_LAUNCH);
            loginWebview.getSettings().setJavaScriptEnabled(true);
            loginWebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d(TAG, "URL : " + url);
                    if (url.startsWith(Constants.INSTAGRAM_CALLBACK_URL)) {
                        if(url.contains("access_token")) {
                            String accessToken = url.split("#access_token=")[1];
                            Log.d(TAG, "Instagram TOKEN: " + accessToken);
                            InstagramUtils.saveInstagramUserToken(accessToken, WebLoginActivity.this);
                            finish();
                        } else if(url.contains("error_reason")) {
                            String error = url.contains("user_denied") ? "User denied access" : "Authentication failed";

                            finish();
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });

            loginWebview.loadUrl(url);
        }
    }

    public void twitchLogin(Intent intent){
        if(intent.hasExtra(Constants.TWITCH_URL_LAUNCH)){
            String url = intent.getStringExtra(Constants.TWITCH_URL_LAUNCH);
            loginWebview.getSettings().setJavaScriptEnabled(true);
            loginWebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d(TAG, "URL : " + url);
                    if (url.startsWith(Constants.REDIRECT)) {
                        if(url.contains("access_token")) {
                            String result = url.split("#access_token=")[1];
                            String accessToken = result.split("&")[0];
                            Log.d(TAG, "Twitch TOKEN: " + accessToken);
                            TwitchUtils.saveTwitchUserToken(accessToken, WebLoginActivity.this);
                            finish();
                        } else if(url.contains("error_reason")) {
                            String error = url.contains("user_denied") ? "User denied access" : "Authentication failed";
                            finish();
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            loginWebview.loadUrl(url);
        }
    }

    public void redditLogin(Intent intent){
        if(intent.hasExtra(Constants.REDDIT_URL_LAUNCH)){
            String url = intent.getStringExtra(Constants.REDDIT_URL_LAUNCH);
            loginWebview.getSettings().setJavaScriptEnabled(true);
            loginWebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d(TAG, "URL : " + url);
                    if (url.startsWith(Constants.REDIRECT)) {
                        if(url.contains("access_token")) {
                            String result = url.split("#access_token=")[1];
                            String accessToken = result.split("&")[0];
                            Log.d(TAG, "Reddit TOKEN: " + accessToken);
                            RedditUtils.saveRedditUserToken(accessToken, WebLoginActivity.this);
                            finish();
                        } else if(url.contains("error_reason")) {
                            String error = url.contains("access_denied") ? "User denied access" : "Authentication failed";
                            finish();
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            loginWebview.loadUrl(url);
        }
    }

    public void discordLogin(Intent intent){

        //TODO issues with discord login, not going beyond login screen.

        if(intent.hasExtra(Constants.DISCORD_URL_LAUNCH)){
            String url = intent.getStringExtra(Constants.DISCORD_URL_LAUNCH);
            loginWebview.getSettings().setJavaScriptEnabled(true);
            loginWebview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d(TAG, "URL : " + url);
                    if (url.startsWith(Constants.REDIRECT)) {
                        if(url.contains("access_token")) {
                            String result = url.split("#access_token=")[1];
                            String accessToken = result.split("&")[0];
                            Log.d(TAG, "Discord TOKEN: " + accessToken);
                            DiscordUtils.saveDiscordUserToken(accessToken, WebLoginActivity.this);
                            finish();
                        } else if(url.contains("error_reason")) {
                            String error = url.contains("access_denied") ? "User denied access" : "Authentication failed";
                            finish();
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            loginWebview.loadUrl(url);
        }
    }
}
