package dataAcquisition;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;
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
	static ArrayList<String> twitsArr=new ArrayList<String>();
	
	public Runnable twi() throws InterruptedException, IOException{
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr,
				accessTokenSecretStr);

		twitter.setOAuthAccessToken(accessToken);
		//-------------------------
		Query query = new Query("#pritamaher");
		//
		
		//
		  int numberOfTweets = 100;
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
		    	 //Thread.sleep(120000); 	
		    }; 
		    query.setMaxId(lastID-1);
		   // Thread.sleep(120000); 	
		  }

		  for (int i = 0; i < tweets.size(); i++) {
			  //Thread.sleep(120000); 	
			  Status t = (Status) tweets.get(i);

		    GeoLocation loc = t.getGeoLocation();

		    String user = t.getUser().getScreenName();
		    String msg = t.getText();
		    String time = "";
		    //
		    saveData sv=new saveData();
		    //
		    if (loc!=null) {
		      Double lat = t.getGeoLocation().getLatitude();
		      Double lon = t.getGeoLocation().getLongitude();
		      System.out.println(i + " USER: " + user + " wrote: " + msg + " located at " + lat + ", " + lon);
		     // modelTw td=new modelTw(user, msg)		      
		      twitsArr.add(msg);
		      //CSVWriter writer = new CSVWriter(new FileWriter("D:/exam/BigData/yourfile.csv"), '\t');
		     // feed in your array (or convert your data to an array)
		     //String[] entries =(String[]) twitsArr.toArray();
		     //writer.writeNext(entries);
			 //writer.close();
		    //twitsArr.add(msg);
			    
				modelTw tw=new modelTw(user,msg);
				sv.mdb(tw);
		    } 
		    else 
		    	System.out.println(i + " USER: " + user + " wrote: " + msg);
		    modelTw tw=new modelTw(user,msg);
			sv.mdb(tw);
			
		  }
		
		return null;
	}
		
			//use  thread timing
		public static void main(String[] args) throws InterruptedException, IOException {
		
			try {
				
			newTwi tw=new newTwi();
			Thread t=new Thread(tw.twi());
			t.start();
			
			//t.start();
			} catch (Exception e) {
				// TODO: handle exception
				//Thread.sleep(10000);
			}
			
			
			//-------------------------
			
			
		}
		
	}


