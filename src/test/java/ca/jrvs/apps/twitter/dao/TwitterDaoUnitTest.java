package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TwitterUtil;
import net.minidev.json.JSONUtil;

public class TwitterDaoUnitTest {

  
    private HttpHelper mockHelper;


    
    private TwitterDao dao;

    @Before 
    public void setup(){
        this.mockHelper = mock(HttpHelper.class);
        this.dao = new TwitterDao(mockHelper);
    }


    @Test
    public void showTweet() throws Exception{

        String hashtag = "#abc mock ";
        String text ="Hello just testing with mocaktio show tweet "+hashtag+System.currentTimeMillis();

        Double lat = 1d;
        Double lon = -1d;
   
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        try{
            dao.create(TwitterUtil.buildTweet(text, lon, lat));
            fail();
        }catch(RuntimeException e){
            assertTrue(true);
        }


        String tweetJsonStr = "{\n"
                + "   \"created_at\":\"Mon June 27 15:00:00 +0000 2022\",\n"
                + "   \"id\":18294902381502,\n"
                + "   \"id_str\":\"18294902381502\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],"
                + "      \"user_mentions\":[]"
                + "   },\n"
                + "   \"coordinates\":null,"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao= spy(dao);
        Tweet expectedTweet= JsonParser.toObjectFromJson(tweetJsonStr,Tweet.class);

        //Mock parseResponseBody

        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        Tweet tweet=spyDao.create(TwitterUtil.buildTweet(text,lon,lat));


        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteTweet() throws Exception{
        
        String hashtag = "#abc mock ";
        String text ="Hello just testing with mocaktio Delete Tweet "+hashtag+System.currentTimeMillis();

        Double lat = 1d;
        Double lon = -1d;
   
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        try{
            dao.create(TwitterUtil.buildTweet(text, lon, lat));
            fail();
        }catch(RuntimeException e){
            assertTrue(true);
        }


        String tweetJsonStr = "{\n"
                + "   \"created_at\":\"Mon June 27 15:00:00 +0000 2022\",\n"
                + "   \"id\":18294902381502,\n"
                + "   \"id_str\":\"18294902381502\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],"
                + "      \"user_mentions\":[]"
                + "   },\n"
                + "   \"coordinates\":null,"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao= spy(dao);
        Tweet expectedTweet= JsonParser.toObjectFromJson(tweetJsonStr,Tweet.class);

        //Mock parseResponseBody

        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    
        Tweet deletedTweet = spyDao.deleteById("18294902381502");
        assertNotNull(deletedTweet);
        assertNotNull(deletedTweet.getText());



    }



}



    



