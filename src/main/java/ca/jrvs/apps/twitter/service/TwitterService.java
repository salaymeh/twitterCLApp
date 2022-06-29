package ca.jrvs.apps.twitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;




@org.springframework.stereotype.Service
public class TwitterService implements Service {
    
    
    private CrdDao dao;

    private Double MAX_LONGITUDE = 180.0;
    private Double MAX_LATITUDE = 90.0;



    @Autowired
    public TwitterService(CrdDao dao){
        this.dao=dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        return validatePostTweet(tweet);
    }


    @Override
    public Tweet showTweet(String id, String[] fields) {
        
        boolean valid = validateID(id);
        if(valid == false){
            throw new IllegalArgumentException("id not valid, please make sure its digits only!",null);
        }

        return (Tweet) dao.findById(id);
    
        
    }
  
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
                
        return validateDeleteIds(ids);
    }

    private Tweet validatePostTweet(Tweet tweet) {

        if(tweet==null){
            throw new IllegalArgumentException("Tweet Object Cannot be empty!",null);
        }
        
        if(tweet.getText().length()>141){
            throw new IllegalArgumentException("Tweet has a max of 140 Characters!",null);
        }
        if(tweet.getCoordinates().getCoordinates().get(0)>MAX_LONGITUDE){
            throw new IllegalArgumentException("longitude cannot be bigger then: "+MAX_LONGITUDE,null);
        }
        if(tweet.getCoordinates().getCoordinates().get(0)<-MAX_LONGITUDE){
            throw new IllegalArgumentException("longitude cannot be smaller then: "+(-MAX_LONGITUDE),null);
        }
        if(tweet.getCoordinates().getCoordinates().get(1)>MAX_LATITUDE){
            throw new IllegalArgumentException("latitude cannot be bigger then: "+MAX_LATITUDE,null);
        }
        if(tweet.getCoordinates().getCoordinates().get(1)<-MAX_LATITUDE){
            throw new IllegalArgumentException("latitude cannot be smaller then: "+(-MAX_LATITUDE),null);
        }

        return (Tweet) dao.create(tweet);
        


    }


    private boolean validateID(String id) {
        Matcher m = Pattern.compile("[0-9]+").matcher(id);
        System.out.println(id);
        return m.matches();
    }


    private List<Tweet> validateDeleteIds(String[] ids) {
        
        List<Tweet> deletedTweets = new ArrayList<>();

        for(String id: ids){
            if(validateID(id)==false){
                throw new IllegalArgumentException("ID is out of range: "+id +"Please double check ID again",null);
            }else{
                deletedTweets.add((Tweet) this.dao.findById(id));
                this.dao.deleteById(id);
            }
        }

        return deletedTweets;
    }





}
