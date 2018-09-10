package com.necohorne.socialmanager.Utilities;

import com.necohorne.socialmanager.BuildConfig;

public class Constants {

    public static final String oauth_request_token = "https://necohorne.com?source=twitter";
    public static final String twitter_callback_url = "https://necohorne.com?source=twitter";
    public static final String REDIRECT = "https://www.necohorne.com/";

    //Shared Preferences
    public static final String TOKEN_PREFS = "TokenPrefs";

    //TWITCH
    public static final String TWITCH_API_BASE_URL = "https://id.twitch.tv/";
    public static final String TWITCH_URL_LAUNCH = "TwitchUrl";
    public static final String TWITCH_ACCESS_TOKEN = "twitch_access_token";

    //INSTAGRAM
    public static final String INSTAGRAM_CALLBACK_URL = "https://www.necohorne.com";
    public static final String INSTAGRAM_BASE_URL = "https://api.instagram.com/";
    public static final String INSTAGRAM_REDIRECT_URL = "https://instagram.com/";
    public static final String INSTAGRAM_URL_LAUNCH = "InstagramUrl";
    public static final String INSTAGRAM_ACCESS_TOKEN = "instagram_access_token";

    //REDDIT
    public static final String REDDIT_API_BASE_URL = "https://www.reddit.com/api/v1/";
    public static final String REDDIT_URL_LAUNCH = "RedditUrl";
    public static final String REDDIT_ACCESS_TOKEN = "reddit_access_token";

    //DISCORD
    public static final String DISCORD_API_BASE_URL = "https://discordapp.com/api/";
    public static final String DISCORD_URL_LAUNCH = "DiscordUrl";
    public static final String DISCORD_ACCESS_TOKEN = "discord_access_token";

    //App Secrets and API Keys
    //These keys are stored in the gradle.properties file for security reasons.
    public static final String FACEBOOK_CLIENT_TOKEN = BuildConfig.FACEBOOK_CLIENT_TOKEN;
    public static final String FACEBOOK_APP_ID = BuildConfig.FACEBOOK_APP_ID;
    public static final String FACEBOOK_SECRET = BuildConfig.FACEBOOK_SECRET;
    public static final String FACEBOOK_LOGIN_PROTOCAL = BuildConfig.FACEBOOK_LOGIN_PROTOCAL;
    public static final String TWITTER_API_KEY = BuildConfig.TWITTER_API_KEY;
    public static final String TWITTER_API_SECRET = BuildConfig.TWITTER_API_SECRET;
    public static final String TWITTER_ACCESS_TOKEN = BuildConfig.TWITTER_ACCESS_TOKEN;
    public static final String TWITTER_TOKEN_SECRET = BuildConfig.TWITTER_TOKEN_SECRET;
    public static final String LINKEDIN_SECRET = BuildConfig.LINKEDIN_SECRET;
    public static final String LINKEDIN_CLIENT_ID = BuildConfig.LINKEDIN_CLIENT_ID;
    public static final String INSTAGRAM_CLIENT_ID = BuildConfig.INSTAGRAM_CLIENT_ID;
    public static final String INSTAGRAM_CLIENT_SECRET = BuildConfig.TWITCH_CLIENT_SECRET;
    public static final String GOOGLE_API_KEY = BuildConfig.GOOGLE_API_KEY;
    public static final String PINTEREST_APP_ID = BuildConfig.PINTEREST_APP_ID;
    public static final String PINTEREST_APP_SECRET = BuildConfig.PINTEREST_APP_SECRET;
    public static final String TWITCH_CLIENT_ID = BuildConfig.TWITCH_CLIENT_ID;
    public static final String TWITCH_CLIENT_SECRET = BuildConfig.TWITCH_CLIENT_SECRET;
    public static final String REDDIT_API_KEY = BuildConfig.REDDIT_API_KEY;
    public static final String VIMEO_CLIENT_ID = BuildConfig.VIMEO_CLIENT_ID;
    public static final String VIMEO_CLIENT_SECRET = BuildConfig.VIMEO_CLIENT_SECRET;
    public static final String VIMEO_ACCESS_TOKEN = BuildConfig.VIMEO_ACCESS_TOKEN;
    public static final String PATREON_CLIENT_ID = BuildConfig.PATREON_CLIENT_ID;
    public static final String PATREON_CLIENT_SECRET = BuildConfig.PATREON_CLIENT_SECRET;
    public static final String PATREON_CREATOR_ACCESS_TOKEN = BuildConfig.PATREON_CREATOR_ACCESS_TOKEN;
    public static final String PATREON_CREATOR_REFRESH_TOKEN = BuildConfig.PATREON_CREATOR_REFRESH_TOKEN;
    public static final String DISCORD_CLIENT_ID = BuildConfig.DISCORD_CLIENT_ID;
    public static final String DISCORD_CLIENT_SECRET = BuildConfig.DISCORD_CLIENT_SECRET;

    //post hash map keys:
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String INSTAGRAM = "instagram";
    public static final String LINKEDIN = "linkedin";
    public static final String YOUTUBE = "youtube";
    public static final String GOOGLE = "google";
    public static final String PINTEREST = "pinterest";
    public static final String TWITCH = "twitch";
    public static final String DISCORD = "discord";
    public static final String REDDIT = "reddit";
    public static final String VIMEO = "vimeo";
    public static final String PATREON = "patreon";

    //FACEBOOK
    public static final String FACEBOOKGRAPHBASEURL = "graph.facebook.com";

    //Fabric Answers Custom Events
    public static final String FACEBOOKLOGIN = "Facebook Login";
    public static final String TWITTERLOGIN = "Twitter Login";
    public static final String INSTAGRAMLOGIN = "Instagram Login";
    public static final String LINKEDINLOGIN = "Linkedin Login";
    public static final String YOUTUBELOGIN = "Youtube Login";
    public static final String GOOGLELOGIN = "Google+ Login";
    public static final String PINTERESTLOGIN = "Pinterest Login";
    public static final String TWITCHLOGIN = "Twitch Login";
    public static final String REDDITLOGIN = "reddit Login";
    public static final String VIMEOLOGIN = "Vimeo Login";
    public static final String PATREONLOGIN = "Patreon Login";
    public static final String DISCORDLOGIN = "Discord Login";
    public static final String FACEBOOKLOGOUT = "Facebook Logout";
    public static final String TWITTERLOGOUT = "Twitter Logout";
    public static final String INSTAGRAMLOGOUT = "Instagram Logout";
    public static final String LINKEDINLOGOUT = "Linkedin Logout";
    public static final String YOUTUBELOGOUT = "Youtube Logout";
    public static final String GOOGLELOGOUT = "Google+ Logout";
    public static final String PINTERESTLOGOUT = "Pinterest Logout";
    public static final String TWITCHLOGOUT = "Twitch Logout";
    public static final String REDDITLOGOUT = "reddit Logout";
    public static final String VIMEOLOGOUT = "Vimeo Logout";
    public static final String PATREONLOGOUT = "Patreon Logout";
    public static final String DISCORDLOGOUT = "Discord Logout";

}
