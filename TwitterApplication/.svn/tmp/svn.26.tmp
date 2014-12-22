package jp.nadai.twitterapplication;

import jp.nadai.twitterapplication.controller.OAuthController;
import twitter4j.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	ConnectivityManager cm;
	 private Twitter twitter;
//	private TwitterResponse twitterStatuses;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		/* ConnectivityManagerの取得 */
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        if (nInfo == null) {
            Toast.makeText(this, "No Network Connection!", Toast.LENGTH_LONG).show();

            return;
        }

        if (nInfo.isConnected()) {
            /* NetWork接続可 */
            Toast.makeText(this, nInfo.getTypeName() + "接続中",
                    Toast.LENGTH_LONG).show();
            if (nInfo.getTypeName().equals("WIFI")) {

            } else if (nInfo.getTypeName().equals("mobile")) {

            }

        } else {
            /* NetWork接続不可 */
            Toast.makeText(this, "ネットワークに接続できませんでした。", Toast.LENGTH_LONG)
                    .show();
        }


		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//アクセストークンが存在した場合ホーム画面に遷移
		if(OAuthController.hasAccessToken(this)){
			OAuthController.setTwitterInstance(this);
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		//存在しない場合、認証画面に遷移
		}else{
			Intent intent = new Intent(this,TwitterOAuthActivity.class);
			startActivity(intent);
		}
		finish();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}

