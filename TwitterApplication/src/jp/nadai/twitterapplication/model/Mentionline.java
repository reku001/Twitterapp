package jp.nadai.twitterapplication.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jp.nadai.twitterapplication.controller.OAuthController;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.graphics.drawable.Drawable;

public class Mentionline {
	Twitter twitter;
	ResponseList<Status> timeline;
	public void init(){
		twitter = OAuthController.getTwitterInstance();
		try {
			timeline = twitter.getMentionsTimeline();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	public String getReplyText(int i){//本文
	     try {
	        	return String.valueOf(timeline.get(i).getText());

	     } catch (IllegalStateException e) {
		      // TODO 自動生成された catch ブロック
	        	e.printStackTrace();
	     }
	     
	     return "miss";
	}
	
	public String getReplyId(int i){//ID

	     try {
	        	return String.valueOf(timeline.get(i).getUser().getScreenName());

	     } catch (IllegalStateException e) {
		      // TODO 自動生成された catch ブロック
	        	e.printStackTrace();
	     }
	     return "miss";
	}
	public String getReplyName(int i){//名前

	     try {
	        	return String.valueOf(timeline.get(i).getUser().getName());

	     } catch (IllegalStateException e) {
		      // TODO 自動生成された catch ブロック
	        	e.printStackTrace();
	     }
	     return "miss";
	}
	
	public Drawable getReplyImage(int i){//画像
		
InputStream is=null;
		
        try {
			URL uri =new URL(String.valueOf(timeline.get(i).getUser().getBiggerProfileImageURL()));
	
			try {
				is=uri.openStream();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
        Drawable drawable = Drawable.createFromStream(is,String.valueOf(timeline.get(i).getUser().getBiggerProfileImageURL()));
		     return drawable;
	}


}
