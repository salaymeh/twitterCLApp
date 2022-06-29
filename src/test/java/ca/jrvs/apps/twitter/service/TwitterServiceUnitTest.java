package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.diff.Delta;
import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TwitterUtil;

public class TwitterServiceUnitTest {
    

    private TwitterDao dao;
    private TwitterService service;

    
    @Before
    public void setup(){
        this.dao = mock(TwitterDao.class);
        this.service = new TwitterService(this.dao);
    
    }

    @Test
    public void postTweetTest(){

        Tweet tweetMock = TwitterUtil.buildTweet("status", 11d,  12d);
        when(dao.create(isNotNull())).thenReturn(tweetMock);
        Tweet tweetService = this.service.postTweet(tweetMock);
        assertNotNull(tweetService);
        assertEquals("status", tweetService.getText());


    }

    @Test
    public void showTweetService(){

        Tweet tweetMock = TwitterUtil.buildTweet("status", 11d,  12d);
        tweetMock.setId_str("90201");

        when(dao.findById(notNull())).thenReturn(tweetMock);

        Tweet show = this.service.showTweet(tweetMock.getId_str(), null);
        
        assertNotNull(show);
        assertEquals("status", show.getText());


    }

    @Test
    public void deleteTweetService() {
        Tweet tweetMock = new Tweet();
        tweetMock.setText("text");
        String[] ids = {"154124519233"};
        when(dao.deleteById(any())).thenReturn(tweetMock);
        when(dao.findById(any())).thenReturn(tweetMock);

        List<Tweet> deletes = this.service.deleteTweets(ids);

        assertNotNull(deletes);
        assertEquals("text", deletes.get(0).getText());

        

    }
}
