package com.necohorne.socialmanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.necohorne.socialmanager.R;
import com.necohorne.socialmanager.Utilities.Constants;
import com.necohorne.socialmanager.Utilities.SocialUtils.DiscordUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.InstagramUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.RedditUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.TwitchUtils;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialSelectionActivity extends AppCompatActivity {

    private static final String TAG = SocialSelectionActivity.class.getSimpleName();
    public static final String PACKAGE = "com.necohorne.socialmanager";

    public CheckBox facebookCheckBox;
    public CheckBox twitterCheckBox;
    public CheckBox instagramCheckBox;
    public CheckBox linkedinCheckBox;
    public CheckBox youtubeCheckBox;
    public CheckBox googlePlusCheckBox;
    public CheckBox pinterestCheckBox;
    public CheckBox twitchCheckBox;
    public CheckBox redditCheckBox;
    public CheckBox vimeoCheckBox;
    public CheckBox patreonCheckBox;
    public CheckBox discordCheckBox;

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
    public boolean discord;

    private Intent mLogOutIntent;
    private boolean isActivityRunning;
    private AccessToken mFacebookAccessToken;
    private TwitterSession mTwitterSession;
    private LoginManager mFbLoginManager;
    private CallbackManager mCallbackManager;
    private TwitterAuthToken mTwitterAuthToken;
    private TwitterAuthClient mTwitterAuthClient;
    private PDKClient mPDKClient;
    private PDKUser mPinterestUser;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 9012;
    private GoogleSignInAccount mGoogleAccount;

    //TODO Remember to un-comment Answer Custom Logs.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_selection);
        setupUi();
        mLogOutIntent = new Intent( SocialSelectionActivity.this, LoginActivity.class );
        mPDKClient = PDKClient.configureInstance(this, Constants.PINTEREST_APP_ID);
        mPDKClient.onConnect(this);
        fbLoginManager();
        checkLoginStatus();
    }

    @Override
    protected void onStart() {
        isActivityRunning = true;
        checkAuthenticationState();
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(SocialSelectionActivity.this, requestCode, resultCode, data);
        mPDKClient.getInstance().onOauthResponse(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkAuthenticationState() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            mLogOutIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( mLogOutIntent );
            finish();
        }
    }

    private void fbLoginManager(){
        mFbLoginManager = com.facebook.login.LoginManager.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mFbLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // here write code When Login successfully
                Toast.makeText(SocialSelectionActivity.this, "Logged in to Facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                // here write code when get error
            }
        });
    }

    private void getSocialTokens() {
        //facebook token
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mFacebookAccessToken = AccessToken.getCurrentAccessToken();

        //twitter token
        Twitter.initialize(this);
        mTwitterAuthClient = new TwitterAuthClient();
        mTwitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void checkLoginStatus() {
        getSocialTokens();

        //check facebook api for login status
        facebook = mFacebookAccessToken != null && !mFacebookAccessToken.isExpired();
        facebookCheckBox.setChecked(facebook);

        //check twitter api for login status
        if(mTwitterSession != null){
            mTwitterAuthToken = mTwitterSession.getAuthToken();
            String token = mTwitterAuthToken.token;
            String secret = mTwitterAuthToken.secret;
            if(token!=null && secret!=null
                    && token.length()>0 && secret.length()>0){
                twitter = true;
            }
        }else {
            twitter = false;
        }
        twitterCheckBox.setChecked(twitter);

        //todo check login status and set bool as per login status
        //check shared prefs for Instagram token
        if(InstagramUtils.getInstagramToken(SocialSelectionActivity.this) != null){
            instagram = true;
        }
        instagramCheckBox.setChecked(instagram);

        //check Linkedin api for login status
        LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
        LISession session = sessionManager.getSession();
        linkedIn = session.isValid();
        linkedinCheckBox.setChecked(linkedIn);

        //check Youtube api for login status
        youtubeCheckBox.setChecked(youTube);

        //check GooglePlus api for login status
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleAccount != null){
            googlePlus = true;
        }else {
            googlePlus = false;
        }
        googlePlusCheckBox.setChecked(googlePlus);

        //check Pinterest api for login status
        mPDKClient.getPath("me/", null, new PDKCallback(){
            @Override
            public void onSuccess(PDKResponse response){
                pinterest = true;
                pinterestCheckBox.setChecked(pinterest);
            }

            @Override
            public void onFailure(PDKException exception){
                pinterest = false;
                pinterestCheckBox.setChecked(pinterest);
            }
        });

        //check shared prefs for Twitch token
        if(TwitchUtils.getTwitchToken(SocialSelectionActivity.this) != null){
            twitch = true;
        }
        twitchCheckBox.setChecked(twitch);

        //check shared prefs for Dischord token
        if(DiscordUtils.getDiscordToken(SocialSelectionActivity.this) != null){
            discord = true;
        }
        discordCheckBox.setChecked(discord);

        //check shared prefs for reddit token
        if(RedditUtils.getRedditToken(SocialSelectionActivity.this) != null){
            reddit = true;
        }
        redditCheckBox.setChecked(reddit);

        //check Vimeo api for login status
        vimeoCheckBox.setChecked(vimeo);

        //check Patreon api for login status
        patreonCheckBox.setChecked(patreon);

    }

    private void setupUi() {
        facebookCheckBox = findViewById(R.id.facebook_check_box);
        facebookCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                facebookLog(isChecked);
            }
        });

        twitterCheckBox = findViewById(R.id.twitter_check_box);
        twitterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                twitterLog(isChecked);
            }
        });

        instagramCheckBox = findViewById(R.id.instagram_check_box);
        instagramCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                instagramLog(isChecked);
            }
        });

        linkedinCheckBox = findViewById(R.id.linkedin_check_box);
        linkedinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                linkedinLog(isChecked);
            }
        });

        youtubeCheckBox = findViewById(R.id.youtube_check_box);
        youtubeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                youtubeLog(isChecked);
            }
        });

        googlePlusCheckBox = findViewById(R.id.google_check_box);
        googlePlusCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                googlePlusLog(isChecked);
            }
        });

        pinterestCheckBox = findViewById(R.id.pinterest_check_box);
        pinterestCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pinterestLog(isChecked);
            }
        });

        twitchCheckBox = findViewById(R.id.twitch_check_box);
        twitchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                twitchLog(isChecked);
            }
        });

        redditCheckBox = findViewById(R.id.reddit_check_box);
        redditCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                redditLog(isChecked);
            }
        });

        vimeoCheckBox = findViewById(R.id.vimeo_check_box);
        vimeoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vimeoLog(isChecked);
            }
        });

        patreonCheckBox = findViewById(R.id.patreon_check_box);
        patreonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                patreonLog(isChecked);
            }
        });

        discordCheckBox = findViewById(R.id.discord_check_box);
        discordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                discordLog(isChecked);
            }
        });
    }

    private void facebookLog(boolean isChecked) {
        if(isChecked){
            if(!facebook){
                mFbLoginManager.logInWithReadPermissions(SocialSelectionActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
//                Answers.getInstance().logCustom(new CustomEvent(Constants.FACEBOOKLOGIN));
            }

        } else {
            LoginManager.getInstance().logOut();
            Toast.makeText(SocialSelectionActivity.this, "Logged out of Facebook", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.FACEBOOKLOGOUT));
        }
    }

    private void twitterLog(boolean isChecked) {
        if(isChecked){
            if(!twitter){
                mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        Toast.makeText(SocialSelectionActivity.this, "Logged in to Twitter", Toast.LENGTH_SHORT).show();
//                        Answers.getInstance().logCustom(new CustomEvent(Constants.TWITTERLOGIN));
                    }
                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            Toast.makeText(this, "Logged out of Twitter", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.TWITTERLOGOUT));
        }
    }

    private void instagramLog(boolean isChecked) {
        if(isChecked){
            if(!instagram){
                InstagramUtils.launchInstagramLogin(SocialSelectionActivity.this);
                Toast.makeText(this, "Logged in to Instagram", Toast.LENGTH_SHORT).show();
//                Answers.getInstance().logCustom(new CustomEvent(Constants.INSTAGRAMLOGIN));
            }
        } else {
            InstagramUtils.logout(SocialSelectionActivity.this);
            Toast.makeText(this, "Logged Out of Instagram", Toast.LENGTH_SHORT).show();
            instagram = false;
//            Answers.getInstance().logCustom(new CustomEvent(Constants.INSTAGRAMLOGOUT));
        }
    }

    private void linkedinLog(boolean isChecked) {
        if(isChecked){
            if(!linkedIn){
                LISessionManager.getInstance(getApplicationContext()).init(SocialSelectionActivity.this, buildScope(), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        Toast.makeText(SocialSelectionActivity.this, "Logged in to Linked In", Toast.LENGTH_SHORT).show();
                        linkedIn = true;
//                        Answers.getInstance().logCustom(new CustomEvent(Constants.LINKEDINLOGIN));
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        Toast.makeText(SocialSelectionActivity.this, "Something went wrong: " + error.toString(), Toast.LENGTH_SHORT).show();
                        linkedIn = false;
                        linkedinCheckBox.setChecked(linkedIn);
                    }
                }, true);
            }
        } else {
            LISessionManager.getInstance(getApplicationContext()).clearSession();
            Toast.makeText(this, "Logged out of Linked In", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.LINKEDINLOGOUT));
        }
    }

    private void youtubeLog(boolean isChecked) {
        if(isChecked){
            //TODO Social Media Login intent
            Toast.makeText(this, "Logged in to You Tube", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.YOUTUBELOGIN));
        } else {
            //TODO Social Media Logout intent
            Toast.makeText(this, "Logged Out of You Tube", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.YOUTUBELOGOUT));
        }
    }

    private void googlePlusLog(boolean isChecked) {
        if(isChecked){
            if(!googlePlus){
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        } else {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SocialSelectionActivity.this, "Logged Out of Google+", Toast.LENGTH_SHORT).show();
                            googlePlus = false;
//                        Answers.getInstance().logCustom(new CustomEvent(Constants.GOOGLELOGOUT));
                        }
                    });
        }
    }

    private void pinterestLog(boolean isChecked) {

        if(isChecked){
            if(!pinterest){
                List scopes = new ArrayList<String>();
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_RELATIONSHIPS);
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_RELATIONSHIPS);
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PRIVATE);
                scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PRIVATE);

                mPDKClient.login(SocialSelectionActivity.this, scopes, new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        Log.d(getClass().getName(), response.getData().toString());
                        Toast.makeText(SocialSelectionActivity.this, "Logged in to Pinterest", Toast.LENGTH_SHORT).show();
//                        Answers.getInstance().logCustom(new CustomEvent(Constants.PINTERESTLOGIN));
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        Log.e(getClass().getName(), exception.getDetailMessage());
                    }
                });
            }
        } else {
            mPDKClient.logout();
            pinterest = false;
            Toast.makeText(this, "Logged Out of Pinterest", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.PINTERESTLOGOUT));
        }
    }

    private void twitchLog(boolean isChecked) {
        if(isChecked){
            if(!twitch){
                TwitchUtils.launchTwitchLogin(SocialSelectionActivity.this);
                Toast.makeText(this, "Logged in to Twitch", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.TWITCHLOGIN));
            }
        } else {
            TwitchUtils.logout(SocialSelectionActivity.this);
            Toast.makeText(this, "Logged Out of Twitch", Toast.LENGTH_SHORT).show();
            twitch = false;
//            Answers.getInstance().logCustom(new CustomEvent(Constants.TWITCHLOGOUT));
        }
    }

    private void discordLog(boolean isChecked) {
        if(isChecked){
            if(!discord){
                DiscordUtils.launchDiscordLogin(SocialSelectionActivity.this);
                Toast.makeText(this, "Logged in to Discord", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.DISCORDLOGIN));
            }
        } else {
            //TODO Social Media Logout intent
            Toast.makeText(this, "Logged Out of Discord", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.DISCORDLOGOUT));
        }
    }

    private void redditLog(boolean isChecked) {
        if(isChecked){
            if(!reddit){
                RedditUtils.launchRedditLogin(SocialSelectionActivity.this);
                Toast.makeText(this, "Logged in to Reddit", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.REDDITLOGIN));
            }

        } else {
            RedditUtils.logout(SocialSelectionActivity.this);
            Toast.makeText(this, "Logged Out of Reddit", Toast.LENGTH_SHORT).show();
            reddit = false;
//            Answers.getInstance().logCustom(new CustomEvent(Constants.REDDITLOGOUT));
        }
    }

    private void vimeoLog(boolean isChecked) {
        if(isChecked){
            //TODO Social Media Login intent
            Toast.makeText(this, "Logged in to Vimeo", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.VIMEOLOGIN));
        } else {
            //TODO Social Media Logout intent
            Toast.makeText(this, "Logged Out of Vimeo", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.VIMEOLOGOUT));
        }
    }

    private void patreonLog(boolean isChecked) {
        if(isChecked){
            //TODO Social Media Login intent
            Toast.makeText(this, "Logged in to Patreon", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.PATREONLOGIN));
        } else {
            //TODO Social Media Logout intent
            Toast.makeText(this, "Logged Out of Patreon", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.PATREONLOGOUT));
        }
    }

    private static Scope buildScope() {
        //linked in scope builder
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            mGoogleAccount = completedTask.getResult(ApiException.class);
            Toast.makeText(SocialSelectionActivity.this, "Logged in to Google+", Toast.LENGTH_SHORT).show();
//            Answers.getInstance().logCustom(new CustomEvent(Constants.GOOGLELOGIN));
            googlePlus = true;
        } catch (ApiException e) {
            googlePlus = false;
            googlePlusCheckBox.setChecked(false);
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

}
