package jp.nadai.twitterapplication;

import jp.nadai.twitterapplication.controller.OAuthController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class TwitterOAuthActivity extends Activity {

    private String mCallbackURL;
    private Twitter mTwitter;
    private RequestToken mRequestToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_oauth);

        mCallbackURL = getString(R.string.twitter_callback_url);
        OAuthController.setTwitterInstance(this);
        mTwitter = OAuthController.getTwitterInstance();

        startAuthorize();

    }

    /**
     * OAuth認証（厳密には認可）を開始します。
     *
     * @param listener
     */
    private void startAuthorize() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            //URL取得
            protected String doInBackground(Void... params) {
                try {
                    mRequestToken = mTwitter.getOAuthRequestToken(mCallbackURL);
                    return mRequestToken.getAuthorizationURL();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
            //アクセス
            protected void onPostExecute(String url) {
                if (url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {

                }
            }
        };
        task.execute();
    }

    //Intent
    public void onNewIntent(Intent intent) {
        if (intent == null
                || intent.getData() == null
                || !intent.getData().toString().startsWith(mCallbackURL)) {
            return;
        }
        String verifier = intent.getData().getQueryParameter("oauth_verifier");

        AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
            //アクセストークン取得
            protected AccessToken doInBackground(String... params) {
                try {
                    return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

            //認証可否
            protected void onPostExecute(AccessToken accessToken) {
                if (accessToken != null) {
                    // 認証成功！
                    showToast("認証成功");
                    successOAuth(accessToken);
                } else {
                    // 認証失敗
                    showToast("認証失敗");
                }
            }
        };
        task.execute(verifier);
    }
    //認証成功時
    private void successOAuth(AccessToken accessToken) {
    	//AccessTokenの保存
        OAuthController.storeAccessToken(this, accessToken);
        //ホーム画面に遷移
        Intent intent = new Intent(this, SampleActivity.class);
        startActivity(intent);
        finish();
    }
    //Toast表示
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}