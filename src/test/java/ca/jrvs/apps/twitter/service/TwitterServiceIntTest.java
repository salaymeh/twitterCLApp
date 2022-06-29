package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TwitterUtil;

public class TwitterServiceIntTest {


    private TwitterDao dao;
    private TwitterService service;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

        this.dao = new TwitterDao(httpHelper);

        this.service = new TwitterService(this.dao);
    }


    @Test(expected = IllegalArgumentException.class)
    public void postTweetTest(){
        
        Tweet tweetValid = TwitterUtil.buildTweet("this is service test,Testing Post"+System.currentTimeMillis(), -1d, 1d);

        Tweet tweetInValidStatus = TwitterUtil.buildTweet("kwsaqqjnnkfiwxxpozussnjhjdntdkovcmlodhxknviuwanjvdsqvwkzbkewtfybubdbwpcjylgj"+
        "wcmgerjjmrxhhlfddoplpqzfoksdbqgfinlszyqhdfmkozfmdyaxnmdogwhwzskzgtm"+System.currentTimeMillis(), 1d, -1d);

        Tweet tweetInValidLong = TwitterUtil.buildTweet("this is service test"+System.currentTimeMillis(), 191d, 1d);
        Tweet tweetInValidLong2 = TwitterUtil.buildTweet("this is service test"+System.currentTimeMillis(), -191d, 1d);
        Tweet tweetInValidLat = TwitterUtil.buildTweet("this is service test"+System.currentTimeMillis(), -1d, 92d);
        Tweet tweetInValidLat2 = TwitterUtil.buildTweet("this is service test"+System.currentTimeMillis(), -1d, -92d);


        assertEquals(tweetValid.getText(),service.postTweet(tweetValid).getText());

        assertNull("expected Null: Reason Characters > 140 : ",service.postTweet(tweetInValidStatus));
        assertNull("expected Null: Reason Longtitude > 180 ",service.postTweet(tweetInValidLong));
        assertNull("expected Null: Reason Longtitude < 180 ",service.postTweet(tweetInValidLong2));
        assertNull("expected Null: Reason Latitude > 90 ",service.postTweet(tweetInValidLat));
        assertNull("expected Null: Reason latitude < 90 ",service.postTweet(tweetInValidLat2));

    }


    @Test(expected =  IllegalArgumentException.class)
    public void showTweet(){

        Tweet tweetValid = TwitterUtil.buildTweet("this is service test,Testing Show:"+System.currentTimeMillis(), -1d, 1d);

        Tweet serviceTweet = service.postTweet(tweetValid);

        Tweet show = service.showTweet(serviceTweet.getId_str(), null);
        

        assertEquals(show.getText(), tweetValid.getText());
        assertNull(service.showTweet("122a34", null));

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTweets(){
        Tweet tweetValid1 = service.postTweet(TwitterUtil.buildTweet("this is service test,Testing Delete 1:"+System.currentTimeMillis(), -1d, 1d));
        Tweet tweetValid2 = service.postTweet(TwitterUtil.buildTweet("this is service test,Testing Delete 2:"+System.currentTimeMillis(), -1d, 1d));

    
        String[] ids = {tweetValid1.getId_str(),tweetValid2.getId_str()};
        
        String[] idsUnValid = {"1234ABC","ABC123"};

        List<Tweet> deletedTweets = service.deleteTweets(ids);

        assertEquals(tweetValid1.getId_str(), deletedTweets.get(0).getId_str());
        assertEquals(tweetValid2.getId_str(), deletedTweets.get(1).getId_str());

        assertNull("expected Null: ids are not valid",service.deleteTweets(idsUnValid));




    }
    
}
