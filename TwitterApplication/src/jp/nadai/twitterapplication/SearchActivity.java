package jp.nadai.twitterapplication;

import java.util.List;

import jp.nadai.twitterapplication.model.Mentionline;
import jp.nadai.twitterapplication.model.Timeline;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class SearchActivity extends ListActivity {
	int pageNum = 1;
	private Timeline mAdapter;
	private Twitter mTwitter;
	private QueryResult seachResult;
	HomeActivity home = new HomeActivity();
	Query query = new Query();
	long lastTweetId=0;
    boolean IsSearch=false;
    boolean ismAtClean=false;
    ConnectivityManager cm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Button btn = (Button)findViewById(R.id.button1);
//query.sinceId(0);
//query.maxId(20);

		// ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ボタンがクリックされた時に呼び出されます
				EditText et = (EditText)findViewById(R.id.editText1);
				 SpannableStringBuilder sb = (SpannableStringBuilder)et.getText();

				 String st=sb.toString();
				 CloseKeyBoard(v);

				query.setQuery(st);
				showToast(st+"を検索しました");
				et.setText(null);
				IsSearch=true;
				reloadTimeLine();

			}
		});
		// setContentView(R.layout.activity_reply);
		// boolean isLastItemVisible = totalItemCount == firstVisibleItem +
		// visibleItemCount;
		/*
		 * getListView().addFooterView(LayoutInflater.from(footerViwe);
		 * getListView().setOnScrollListener(new OnScrollListener() { public
		 * void onScroll( AbsListView view, int firstVisibleItem, int
		 * visibleItemCount, int totalItemCount) { boolean isLastItemVisible =
		 * totalItemCount == firstVisibleItem + visibleItemCount; if
		 * (isLastItemVisible && !isLoading()) { loadNewItems(); } } }
		 */

		if (!TwitterUtils.hasAccessToken(this)) {
			Intent intent = new Intent(this, TwitterOAuthActivity.class);
			startActivity(intent);
			finish();
		} else {
			mAdapter = new Timeline(this);
			setListAdapter(mAdapter);
			mTwitter = TwitterUtils.getTwitterInstance(this);

		}


		final ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView parent,View view, int position, long id) {
				LayoutInflater factory = LayoutInflater.from(SearchActivity.this);
				final View inputview = factory.inflate(R.layout.detail_tweet, null);

				AlertDialog.Builder syosai = new AlertDialog.Builder(SearchActivity.this);
				User statusUser=mAdapter.getItem(position).getUser();

				SmartImageView icon = (SmartImageView)inputview.findViewById(R.id.Icon);
				icon.setImageUrl(statusUser.getProfileImageURL());
				TextView name=(TextView)inputview.findViewById(R.id.Name);
				name.setText("ユーザ名    : "+statusUser.getName()+"\n");

				TextView screen_name=(TextView)inputview.findViewById(R.id.Screen_Name);
				screen_name.setText("ID               : "+statusUser.getScreenName()+"\n");

				TextView follow=(TextView)inputview.findViewById(R.id.FollowCount);
				follow.setText("フォロー    : "+statusUser.getFriendsCount()+"\n");

				TextView follower=(TextView)inputview.findViewById(R.id.Follower);
				follower.setText("フォロワー: "+statusUser.getFollowersCount()+"\n");

				TextView favorite=(TextView)inputview.findViewById(R.id.Favorite);
				favorite.setText("お気に入り: "+statusUser.getFavouritesCount()+"\n");

				TextView text=(TextView)inputview.findViewById(R.id.Text);
				text.setText("自己紹介文: \n"+statusUser.getDescription()+"\n");

				syosai.setTitle("詳細");
				syosai.setView(inputview);
				syosai.show();
				return true;
			}
		}
				);



		listView.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int position = listView.getFirstVisiblePosition();




				if (listView.getChildAt(0) != null) {
					int y = listView.getLastVisiblePosition();
					//listView.setSelectionFromTop(position, y);
					int z=pageNum*15-1;
					if (y==z) {
						pageNum++;
							reloadTimeLine();
						showToast("loading...");
					}

				}


			}
		});

		Mentionline mention = new Mentionline();

		mention.init();

	}

	protected void onListItemClick(ListView l, View v, final int position,  long id){
		super.onListItemClick(l, v, position, id);
		//showToast("Short Tap" + String.valueOf(position));
		super.onListItemClick(l, v, position, id);

		String[] dialogItem = new String[]{"リプライ", "リツイート", "お気に入り"};
		AlertDialog.Builder dialogMenu = new AlertDialog.Builder(this);
		dialogMenu.setItems(dialogItem, new DialogInterface.OnClickListener() {


			public void onClick(DialogInterface dialog, int which)
			{

				switch (which)
				{
				case 0:

					//リプライ押下時の操作
					final EditText tweeting=(EditText)findViewById(R.id.editText1);
     	        	tweeting.setText("@"+mAdapter.getItem(position).getUser().getScreenName()+" ");
     	        	CharSequence str = tweeting.getText();
     	        	tweeting.setSelection(str.length());
     	  		    Button btn = (Button)findViewById(R.id.button1);
     	  		    btn.setText("リプライ");
     	  		    btn.setOnClickListener(new View.OnClickListener() {

     	  			public void onClick(View v) {
     	  			    // TODO 自動生成されたメソッド・スタブ
     	  			    try {
     	  				    mTwitter.updateStatus(new StatusUpdate(tweeting.getText().toString()).inReplyToStatusId(position));
     	  			    } catch (TwitterException e2) {
     	  			    	e2.printStackTrace();
     	  			    }
     	  			    showToast("リプライしました");
     	  			    tweeting.setText(null);
     	  			}
     	  			});					break;


				case 1:
					//リツイート押下時の操作
					showToast("リツイート実行");

					try {
						mTwitter.retweetStatus(mAdapter.getItem(position).getId());
					} catch (IllegalStateException | TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}


					break;

				case 2:
					//お気に入り押下時の操作
					showToast("お気に入り実行");

					try {
						mTwitter.createFavorite(mAdapter.getItem(position).getId());
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}

					break;
				}


			}
		}).create().show();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {//メニューキー押したときの処理
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {

	    case R.id.sent://投稿選択時

	    	final TextView tweeting=(TextView)findViewById(R.id.editText1);
	    	tweeting.setText(null);
			Button btn = (Button)findViewById(R.id.button1);
			btn.setText("投稿");
			btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
				    // TODO 自動生成されたメソッド・スタブ
				    try {
					    mTwitter.updateStatus(new StatusUpdate(tweeting.getText().toString()));
				    } catch (TwitterException e2) {
				    	e2.printStackTrace();
				    }
				    showToast("投稿しました");
				    tweeting.setText(null);
				}
				});
	      return true;


	    case R.id.reply://ホーム選択時
		  Intent intent1 = new Intent(this,HomeActivity.class);
		  startActivity(intent1);
		  showToast("ホーム画面に移動しました");
		  return true;

	    case R.id.DM://DM選択時
		  Intent intent2 = new Intent(this,DirectMessageActivity.class);
			startActivity(intent2);
			showToast("DM画面に移動しました");
		  return true;

	    case R.id.search://リプライ7選択時
		  Intent intent3 = new Intent(this,ReplyActivity.class);
		  startActivity(intent3);
		  showToast("リプライ画面に移動しました");
		  return true;
	    case R.id.reload://検索選択時
			reloadTimeLine();
			showToast("リロードしました");
			return true;
	    	}
	    return false;
	  }



	private void reloadTimeLine() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(
					Void... params) {

				try {
				//	query.setSinceId(pageNum*15);

					if(lastTweetId==0){
						lastTweetId++;

					}
					else{
						lastTweetId=mAdapter.getItem(mAdapter.getCount()-1).getId();
					query.setMaxId(lastTweetId-1);
					}


					seachResult = mTwitter.search(query);



				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return seachResult.getTweets();
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					//
					IsSearching();

					for (twitter4j.Status status : result) {
						mAdapter.add(status);

					}


				} else {
					net();
					showToast("タイムラインの取得に失敗しました…");
				}
			}
		};
		task.execute();
	}


	private void showToast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	private  void net(){


		cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo nInfo = cm.getActiveNetworkInfo();

		if (nInfo == null) {
			Toast.makeText(this, "ネットワークに接続できませんでした。", Toast.LENGTH_LONG).show();
		}
	}
void IsSearching(){//前回の検索履歴を消去する

	if(IsSearch){

		mAdapter.clear();
		pageNum=1;
		IsSearch=false;

	}else{
		IsSearch=false;
	}

}
void CloseKeyBoard(View v){//キーボードをしまう
	 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
     imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
}

}