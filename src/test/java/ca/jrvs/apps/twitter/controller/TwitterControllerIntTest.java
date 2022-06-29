package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TwitterUtil;

public class TwitterControllerIntTest {
    
    private TwitterDao dao;
    private TwitterService service;
    private TwitterController controller;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

        this.dao = new TwitterDao(httpHelper);

        this.service = new TwitterService(this.dao);
        this.controller = new TwitterController(this.service);
    }


    @Test
    public void postTweet(){

        Tweet tweet =  TwitterUtil.buildTweet("this tweet is a test from controller: "+System.currentTimeMillis(), 1d, 1d);
        
        String[] postTweet = {"post",tweet.getText(),tweet.getCoordinates().getCoordinates().get(0)+":"+tweet.getCoordinates().getCoordinates().get(1)};

        
        assertEquals(tweet.getText(), this.controller.postTweet(postTweet).getText());

    }
    @Test
    public void showTweet(){
        String[] showTweet= {"show","1540395790673256450"};

        assertEquals(this.service.showTweet("1540395790673256450", null).getId_str(), this.controller.showTweet(showTweet).getId_str());
    }

    @Test
    public void deleteTweet(){
        Tweet tweet1= this.service.postTweet(TwitterUtil.buildTweet("Testing controller1 "+System.currentTimeMillis(), 1d, 1d));
        Tweet tweet2= this.service.postTweet(TwitterUtil.buildTweet("Testing controller2 "+System.currentTimeMillis(), 1d, 1d));
        Tweet tweet3= this.service.postTweet(TwitterUtil.buildTweet("Testing controller3 "+System.currentTimeMillis(), 1d, 1d));
        String[] deleteTweet={"delete",tweet1.getId_str(),tweet2.getId_str(),tweet3.getId_str()};
        

        List<Tweet> listTweet = this.controller.deleteTweet(deleteTweet);
        
        // System.out.println("tweet 2: "+tweet2.getId_str());
        // System.out.println("tweet 2 controller:"+this.controller.deleteTweet(deleteTweet).get(2).getId_str());
 
        assertEquals(tweet1.getId_str(), listTweet.get(0).getId_str());
        assertEquals(tweet2.getId_str(), listTweet.get(1).getId_str());
        assertEquals(tweet3.getId_str(), listTweet.get(2).getId_str());
    }


}
