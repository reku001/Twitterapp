package jp.nadai.twitterapplication;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.nadai.twitterapplication.model.DMline;
import jp.nadai.twitterapplication.model.Mentionline;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class DirectMessageActivity extends ListActivity implements UserStreamListener{
	TwitterStream twitterStream;


	ArrayList<twitter4j.DirectMessage> arrayList=new ArrayList<twitter4j.DirectMessage>();;

	private DMline mAdapter;
	private Twitter mTwitter;


	int pageNum = 1,limit,time;
	boolean ismAtClean=false;
	NetworkInfo nInfo;
	ConnectivityManager cm;
	String ac;
	String hint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//最初キーボードを非表示
		setContentView(R.layout.activity_reply);


		String oAuthConsumerKey = "FsYF2mWXFjlos8VgiSVz8nfHv";
		String oAuthConsumerSecret = "KkXSXlRiyQstw8K8JCiU2Q4Bfw7GRVHkWodaS2G2MqenchlVeW";
		String oAuthAccessToken = "1017709742-5E7r1mqChjXU6Pduk56dJeM2iXgjLjbZmaK3M1L";
		String oAuthAccessTokenSecret = "djr8C9vLjCPQ7wPRVEjWGq9SHxpTPnRpyU0nADCTC8IS1";

		ConfigurationBuilder builder = new ConfigurationBuilder();
		{
			builder.setOAuthConsumerKey(oAuthConsumerKey);
			builder.setOAuthConsumerSecret(oAuthConsumerSecret);
			builder.setOAuthAccessToken(oAuthAccessToken);
			builder.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
		}

		Configuration conf = builder.build();
		TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(conf);
		TwitterStream twitterStream = twitterStreamFactory.getInstance();
		twitterStream.addListener(this);
		twitterStream.user();
		if (!TwitterUtils.hasAccessToken(this)) {
			Intent intent = new Intent(this, TwitterOAuthActivity.class);
			startActivity(intent);
			finish();
		} else {
			mAdapter = new DMline(this);
			setListAdapter(mAdapter);

			mTwitter = TwitterUtils.getTwitterInstance(this);
			reloadTimeLine();
		}

		final ListView listView = (ListView) findViewById(android.R.id.list);

		listView.setOnScrollListener(new OnScrollListener() {

			//				@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			//				@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount)
			{
				//					


				if (listView.getChildAt(0) != null)
				{
					int y = listView.getLastVisiblePosition();

					int z=pageNum*20-1;
					if (y==z) {
						pageNum++;
						ismAtClean=false;

						reloadTimeLine();
						showToast("loading...");
					}
				}


			}
		});


		Mentionline mention = new Mentionline();

		mention.init();


		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView parent,View view, int position, long id) {
				LayoutInflater factory = LayoutInflater.from(DirectMessageActivity.this);
				final View inputview = factory.inflate(R.layout.detail_tweet, null);

				AlertDialog.Builder syosai = new AlertDialog.Builder(DirectMessageActivity.this);
				User statusUser=mAdapter.getItem(position).getSender();

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
	}



	protected void onListItemClick(ListView l, View v, final int position, final long id){
		super.onListItemClick(l, v, position, id);
		String[] dialogItem = new String[]{"リプライ", "リツイート", "お気に入り","DMの返信","URL先へジャンプ"};
		AlertDialog.Builder dialogMenu = new AlertDialog.Builder(this);
		dialogMenu.setItems(dialogItem, new DialogInterface.OnClickListener() {


			public void onClick(DialogInterface dialog, int which)
			{

				switch (which)
				{
				case 0:
					//リプライ押下時の操作
					final EditText tweeting=(EditText)findViewById(R.id.editText1);
					tweeting.setText("@"+mAdapter.getItem(position).getText()+" ");
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
					});
					break;

				case 1:
					//リツイート押下時の操作
					try {
						mTwitter.retweetStatus(mAdapter.getItem(position).getId());
					} catch (TwitterException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
					showToast("リツイートしました");
					break;

				case 2:
					//お気に入り押下時の操作
					try {
						mTwitter.createFavorite(mAdapter.getItem(position).getId());
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					showToast("お気に入りに追加しました");
					break;
				case 3:


					break;

				case 4:
					//URL先にジャンプする時の操作

					Pattern urlPattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+"
							, Pattern.CASE_INSENSITIVE);
					Matcher matcher = urlPattern.matcher(mAdapter.getItem(position).getText());
					while (matcher.find()) {
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(matcher.group()));
						startActivity(intent);
					}
					showToast("URL先へジャンプしました");
					break;
				}
			}
		}).create().show();


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.direct_message, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {


		switch (item.getItemId()) {

		case R.id.home:
			Intent intent0=new Intent(this,HomeActivity.class);
			startActivity(intent0);
			showToast("ホーム画面に移動しました");
			return true;

		case R.id.reply://リプライ選択時
			Intent intent1 = new Intent(this,ReplyActivity.class);
			startActivity(intent1);
			showToast("リプライ画面に移動しました");
			return true;

		case R.id.DM://DM選択時
			Intent intent2 = new Intent(this,DirectMessageActivity.class);
			startActivity(intent2);
			showToast("DM画面に移動しました");
			return true;

		case R.id.search://検索選択時
			Intent intent3 = new Intent(this,SearchActivity.class);
			startActivity(intent3);
			showToast("検索画面に移動しました");
			return true;
		case R.id.reload://検索選択時
			ismAtClean=true;
			pageNum=1;
			reloadTimeLine();
			showToast("更新しています");
			return true;


		}

		return super.onOptionsItemSelected(item);
	}



	private void reloadTimeLine() {
		AsyncTask<Void, Void, ArrayList<twitter4j.DirectMessage>> task = new AsyncTask<Void, Void, ArrayList<twitter4j.DirectMessage>>() {
			@Override
			protected ArrayList<DirectMessage> doInBackground(Void... params) {
				try {

					Paging paging = new Paging(pageNum,20);
					ResponseList<twitter4j.DirectMessage> responseList = mTwitter.getDirectMessages(paging);

					arrayList.clear();
					for(int i=0;i<responseList.size();i++){

						arrayList.add(responseList.get(i));			            		

					}


					return arrayList;
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(ArrayList<twitter4j.DirectMessage> result) {

				if (result != null) {

					if(ismAtClean){
						mAdapter.clear();

					}

					for (twitter4j.DirectMessage status:result) {
						mAdapter.add(status);
					}
				}else  if(limit<=0){
					net();
					showToast("APIが切れています。");
					showToast(String.valueOf("回復まで"+time+"秒"));
				}
				else {
					net();
					showToast("タイムラインが取得できませんでした。");
				}
			}
		};
		task.execute();
	}



	private void showToast(String text) {
		Toast ts=Toast.makeText(this, text, Toast.LENGTH_SHORT);
		ts.setGravity(Gravity.CENTER,0 , 0);
		ts.show();
	}



	private  void net(){


		cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo nInfo = cm.getActiveNetworkInfo();

		if (nInfo == null) {
			Toast.makeText(this, "ネットワークに接続できませんでした。", Toast.LENGTH_LONG).show();

		}




	}
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		//TODO 自動生成されたメソッド・スタブ
	}


	public void onScrubGeo(long arg0, long arg1) {
		// TODO 自動生成されたメソッド・スタブ
	}


	public void onStallWarning(StallWarning arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onStatus(Status arg0) {
		// TODO 自動生成されたメソッド・スタブ



	}




	public void onTrackLimitationNotice(int arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onException(Exception arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onBlock(User arg0, User arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onDeletionNotice(long arg0, long arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onDirectMessage(DirectMessage arg0) {
		if (arrayList != null||arg0!=null) {



			try{
				mAdapter.clear();
			}catch(Exception a){
				mAdapter.clear();
				arrayList.add(0,arg0);
				mAdapter.addAll(arrayList);
			}		


			arrayList.add(0,arg0);
			mAdapter.addAll(arrayList);

		}else  if(limit<=0){
			net();
			showToast("APIが切れています。");
			showToast(String.valueOf("回復まで"+time+"秒"));
		}
		else {
			net();
			showToast("タイムラインが取得できませんでした。");
		}

	}


	public void onFavorite(User arg0, User arg1, Status arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onFollow(User arg0, User arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onFriendList(long[] arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUnblock(User arg0, User arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUnfollow(User arg0, User arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserListCreation(User arg0, UserList arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void onUserListDeletion(User arg0, UserList arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserListUpdate(User arg0, UserList arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void onUserProfileUpdate(User arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

}



