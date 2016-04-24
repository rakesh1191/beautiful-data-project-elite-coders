package dataAcquisition;
import java.util.ArrayList;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class newTwi{
	static String consumerKeyStr = "qnSHMdMT2DnCOybMHkaqwHi7I";
	static String consumerSecretStr = "NQUHN2Hhb6Y1D0n4WftxF3AVhtTZi0TVVUIAjI9auojjMwl0ru";
	static String accessTokenStr = "342627782-X81pmUAPN5o6wFIOYXPrZgKaRP14sxqC8ozqnGJD";
	static String accessTokenSecretStr = "gaUvnFhN2Vombyd0ox0t2qyqz5M1BANak4J1XA5iHd1Nj";
	ArrayList<String> twitsArr=new ArrayList<String>();
	
	public Runnable twi() throws InterruptedException{
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr,
				accessTokenSecretStr);

		twitter.setOAuthAccessToken(accessToken);
		//-------------------------
		Query query = new Query("#ipl");
		  int numberOfTweets = 51;
		  long lastID = Long.MAX_VALUE;
		  ArrayList<Status> tweets = new ArrayList<Status>();
		  while (tweets.size () < numberOfTweets) {
		    if (numberOfTweets - tweets.size() > 100)
		      query.setCount(100);
		    else 
		      query.setCount(numberOfTweets - tweets.size());
		    try {
		      QueryResult result = twitter.search(query);
		      tweets.addAll(result.getTweets());
		      System.out.println("Gathered " + tweets.size() + " tweets");
		      for (Status t: tweets) 
		        if(t.getId() < lastID) lastID = t.getId();

		    }

		    catch (TwitterException te) {
		    	System.out.println("Couldn't connect: " + te);
		    	Thread.sleep(120*1000);
		    }; 
		    query.setMaxId(lastID-1);
		  }

		  for (int i = 0; i < tweets.size(); i++) {
		    Status t = (Status) tweets.get(i);

		    GeoLocation loc = t.getGeoLocation();

		    String user = t.getUser().getScreenName();
		    String msg = t.getText();
		    String time = "";
		    if (loc!=null) {
		      Double lat = t.getGeoLocation().getLatitude();
		      Double lon = t.getGeoLocation().getLongitude();
		      System.out.println(i + " USER: " + user + " wrote: " + msg + " located at " + lat + ", " + lon);
		      
		    } 
		    else 
		    	System.out.println(i + " USER: " + user + " wrote: " + msg);
		    twitsArr.add(msg);
		  }
		return null;
	}
		
			//use  thread timing
		public static void main(String[] args) throws InterruptedException {
			try {
				
			newTwi tw=new newTwi();
			Thread t=new Thread(tw.twi());
			t.start();
			//t.sleep(10000);
			//t.start();
			} catch (Exception e) {
				// TODO: handle exception
				//Thread.sleep(10000);
			}
		
		
			
			//-------------------------
			
		}

	}


