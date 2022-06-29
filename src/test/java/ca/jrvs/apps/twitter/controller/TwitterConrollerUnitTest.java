package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TwitterUtil;

public class TwitterConrollerUnitTest {
    

    private TwitterDao dao;
    private TwitterService service;
    private TwitterController controller;


    @Before
    public void setup(){
        this.dao = mock(TwitterDao.class);
        this.service = new TwitterService(this.dao);
        this.controller = new TwitterController(this.service);
    }


    @Test
    public void postTweet(){
        Tweet tweet = TwitterUtil.buildTweet("status", 11d,  12d);
        when(dao.create(isNotNull())).thenReturn(tweet);
        String[] args = {"post",tweet.getText(),tweet.getCoordinates().getCoordinates().get(0)+":"+tweet.getCoordinates().getCoordinates().get(1)};
        when(this.service.postTweet(tweet)).thenReturn(tweet);
        Tweet mockTweet = this.controller.postTweet(args);
        assertNotNull(mockTweet);
        assertEquals("status", mockTweet.getText());

    }

    @Test
    public void showTweet(){

        Tweet tweetMock = TwitterUtil.buildTweet("status", 11d,  12d);
        tweetMock.setId_str("90201");

        when(dao.findById(notNull())).thenReturn(tweetMock);
        
        String[] args= {"show","90201"};
        when(service.showTweet("90201", null)).thenReturn(tweetMock);
        Tweet mockTweet = this.controller.showTweet(args);
        assertNotNull(mockTweet);
        assertEquals("status", mockTweet.getText());

    }

    @Test
    public void deleteTweet(){

        Tweet tweetMock = new Tweet();
        tweetMock.setText("text");
        String[] ids = {"1234"};

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweetMock);

       
        TwitterDao spyDao= spy(dao);
        TwitterService spyService = spy(service);
        TwitterController spyController = spy(controller);
        String[] args = {"delete","1234"};

        doReturn(tweetMock).when(spyDao).deleteById(notNull());
        doReturn(tweetMock).when(spyDao).findById(notNull());
        doReturn(tweets).when(spyService).deleteTweets(ids);
        doReturn(tweets).when(spyController).deleteTweet(args);
        

        List<Tweet>  mockTweet = spyController.deleteTweet(args);
        assertNotNull(mockTweet);
        assertEquals(tweetMock, mockTweet.get(0));


    }

    

}
