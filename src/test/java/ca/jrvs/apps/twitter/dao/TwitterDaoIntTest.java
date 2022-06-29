package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TwitterUtil;

public class TwitterDaoIntTest {
    
    private TwitterDao dao;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception{
        String hastTag = "#abc";
        String text ="sometext 1234sds125 "+hastTag+" "+System.currentTimeMillis();

        Double lon = 1d;
        Double lat = -1d;

        Tweet tweet = TwitterUtil.buildTweet(text, lon, lat);

        Tweet reposnse = dao.create(tweet);

        assertEquals(text, reposnse.getText());
        assertNotNull(tweet.getCoordinates());
        assertTrue(hastTag.contains(reposnse.getEntitles().getHashtags().get(0).getText()));
        
    }

    @Test 
    public void read() throws Exception{
        String id = "1539323596388061184";
        Tweet tweet = dao.findById(id);

        assertNotNull(tweet);
        assertEquals(id, tweet.getId_str());


    }

    @Test
    public void delete() throws Exception{
        String text ="this is test tweet "+System.currentTimeMillis();

        Double lon = 1d;
        Double lat = -1d;

        Tweet tweetTemp = TwitterUtil.buildTweet(text, lon, lat);
        Tweet temp = dao.create(tweetTemp);

        String id = temp.getId_str();
        Tweet deleted = dao.deleteById(id);

        
        assertEquals(id, deleted.getId_str());
    }

}
