package jp.nadai.twitterapplication.controller;

import jp.nadai.twitterapplication.R;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class OAuthController {
	    private final static String TOKEN = "token";
	    private final static String TOKEN_SECRET = "token_secret";
	    private final static String PREF_NAME = "twitter_access_token";
	    private static Twitter twitter = null;

	    public static void setTwitterInstance(Context context){
	        String consumerKey = context.getString(R.string.twitter_consumer_key);
	        String consumerSecret = context.getString(R.string.twitter_consumer_secret);

	        TwitterFactory factory = new TwitterFactory();
	        Twitter twitter = factory.getInstance();
	        twitter.setOAuthConsumer(consumerKey, consumerSecret);

	        if (hasAccessToken(context)) {
	            twitter.setOAuthAccessToken(loadAccessToken(context));
	        }
	        OAuthController.twitter = twitter;
	    }


	    /**
	     * Twitterインスタンスを取得します。アクセストークンが保存されていれば自動的にセットします。
	     *
	     * @param context
	     * @return
	     * @throws Exception
	     */
	    public static Twitter getTwitterInstance() {
	        return OAuthController.twitter;
	    }

	    /**
	     * アクセストークンをプリファレンスに保存します。
	     *
	     * @param context
	     * @param accessToken
	     */
	    public static void storeAccessToken(Context context, AccessToken accessToken) {
	        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
	                Context.MODE_PRIVATE);
	        Editor editor = preferences.edit();
	        editor.putString(TOKEN, accessToken.getToken());
	        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
	        editor.commit();
	    }

	    /**
	     * アクセストークンをプリファレンスから読み込みます。
	     *
	     * @param context
	     * @return
	     */
	    public static AccessToken loadAccessToken(Context context) {
	        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
	                Context.MODE_PRIVATE);
	        String token = preferences.getString(TOKEN, null);
	        String tokenSecret = preferences.getString(TOKEN_SECRET, null);
	        if (token != null && tokenSecret != null) {
	            return new AccessToken(token, tokenSecret);
	        } else {
	            return null;
	        }
	    }
	    
	   public static void deleteAccessToken(Context context){
		   SharedPreferences preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		   Editor editor = preference.edit();
		   editor.remove(TOKEN);
		   editor.remove(TOKEN_SECRET);
		   editor.commit();
	   }

	    /**
	     * アクセストークンが存在する場合はtrueを返します。
	     *
	     * @return
	     */
	    public static boolean hasAccessToken(Context context) {
	        return loadAccessToken(context) != null;
	    }
}
