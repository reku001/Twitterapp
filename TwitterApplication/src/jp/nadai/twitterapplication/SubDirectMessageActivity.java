package jp.nadai.twitterapplication;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubDirectMessageActivity extends Activity {

	protected static final String id = null;
	protected static final String text = null;
	private	EditText editText = null;
	private	TextView textView = null;
	private Twitter mTwitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_direct_message);

		Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(mButton1Listener);
        editText = (EditText)findViewById(R.id.editText1);
        textView = (TextView)findViewById(R.id.textView1);
        Intent intent = getIntent();
		String id = (String) intent. getSerializableExtra("ID");
		
		Log.d(text, "test");
	}

	private OnClickListener mButton1Listener = new OnClickListener() {
        public void onClick(View v) {
        	 Twitter twitter = new TwitterFactory().getInstance();
             try {
                 twitter.sendDirectMessage(id, editText.getText().toString());
                 showToast("成功");
                 System.exit(0);
             } catch (TwitterException te) {
                 te.printStackTrace();
                 showToast("失敗");
                 System.exit(-1);
             }

        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub_direct_message, menu);
		return true;
	}

	protected void showToast(String string) {
		// TODO 自動生成されたメソッド・スタブ

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

