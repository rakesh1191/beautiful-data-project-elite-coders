package dataAcquisition;
import java.awt.List;
import java.sql.Date;
import java.util.ArrayList;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.FacebookResponse.Metadata;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.Tag;
import facebook4j.auth.AccessToken;

public class facebookCollector {

	public facebookCollector() {
		// TODO Auto-generated constructor stub
	}
	ArrayList<String> qwe=new ArrayList<>();
	@SuppressWarnings({ "deprecation", "null" })
	public ArrayList<String> getdata() throws FacebookException, InterruptedException
	{
		ArrayList<String> list = null;
		try{
			
		 // Generate facebook instance.
	    Facebook facebook = new FacebookFactory().getInstance();
	    // Use default values for oauth app id.
	    facebook.setOAuthAppId("","");
	    // Get an access token from: 
	    // https://developers.facebook.com/tools/explorer
	    // Copy and paste it below.
	    String accessTokenString = "";
	    AccessToken at = new AccessToken(accessTokenString);
	    // Set access token.
	    facebook.setOAuthAccessToken(at);

	    // We're done.
	    // Access group feeds.
	    // You can get the group ID from:
	    // https://developers.facebook.com/tools/explorer

	    // Set limit to 25 feeds. 
	    //yahoo movies ID=73756409831
	    //imdb id=15925638948
	    Date dte=new Date(0);
	    	
	    ResponseList<Post> feeds = facebook.getFeed("15925638948",
	            new Reading().limit(100).since("Mon May 04 09:51:52 CDT 2014"));
	    	
	        // For all 25 feeds...
	        for (int i = 0; i < feeds.size(); i++) {
	           try {			
	        	// Get post.
	            Post post = feeds.get(i);
	            
	            // Get (string) message.
	            String message = post.getMessage();
	                            // Print out the message.
	            System.out.println(message);
	            list.add(message);
	            // Get more stuff...
	            PagableList<Comment> comments = post.getComments();
	            String date = post.getCreatedTime().toString();
	            //java.util.List<Tag> ls= post.getMessageTags();
	            //Metadata meta= post.getMetadata();
	            
	          //get likes
	            //PagableList<Like> likes= post.getLikes();
	            //	List postLikes;
	            //int c=post.getLikes().getCount();	            
	            //      	            
	            //for (Tag string : ls) {
	            	System.out.println(post.getLikes()); 	
				//}
	            
	            System.out.println(date);
	            //String name = post.getFrom().getName();
	            //String id = post.getId();
	            //System.out.println(name);
	           
	           	} catch (Exception e) {
					System.out.println("error----"+e);
				
				}
	           }
		}catch(Exception e){
			Thread.sleep(600000);
			list.addAll(getdata());
			
		}
		 System.out.println("no of rows:-"+list.size());
		return list;

	}
	
	public static void main(String[] args) throws FacebookException, InterruptedException {
		try {
			facebookCollector f=new facebookCollector();
			f.getdata();
			
		} catch (Exception e) {
			
		}	
		
	}
	

}
