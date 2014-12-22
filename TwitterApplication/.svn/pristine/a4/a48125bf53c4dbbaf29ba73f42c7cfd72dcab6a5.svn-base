package jp.nadai.twitterapplication;

import jp.nadai.twitterapplication.controller.OAuthController;
import jp.nadai.twitterapplication.model.Sample;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SampleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);

		Sample sample = new Sample();
		Toast.makeText(this, sample.getTimelineFirst(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		   menu.add(Menu.NONE, 1, Menu.NONE, "認証解除");	
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
		switch(id){
		case 1:
			OAuthController.deleteAccessToken(this);
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
