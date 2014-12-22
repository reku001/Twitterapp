package jp.nadai.twitterapplication.model;

import jp.nadai.twitterapplication.controller.OAuthController;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Sample {

	public String getTimelineFirst(){
		Twitter twitter = OAuthController.getTwitterInstance();
		try {
			return String.valueOf(twitter.getHomeTimeline().get(0).getText());
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return "miss";
	}
}
