package ca.jrvs.apps.twitter.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;

import ca.jrvs.apps.twitter.util.TwitterUtil;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

    private static final String COORD_SEP=":";
    private static final String COMMA=",";
    private Service service;

  

    @Autowired
    public TwitterController(Service service){
        this.service=service;
    }

    @Override
    public Tweet postTweet(String[] args) {
        // TODO Auto-generated method stub
        if(args.length!=3){
            throw new IllegalArgumentException(
                "USAGE: TwitterCLIApp post \"tweet_text\"\"Latitude:Longitude");
        }
        String tweet_txt=args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if(coordArray.length!=2 || StringUtils.isEmpty(tweet_txt)){
            throw new IllegalArgumentException("Invalid location format \n USAGE: TwitterCLIApp  post \"tweet_text\"\"latitude:longitude\"");
        }
        Double lon = null;
        Double lat = null;

        try{
            lon= Double.parseDouble(coordArray[0]);
            lat= Double.parseDouble(coordArray[1]);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid location format \n USAGE: TwitterCLIApp  post \"tweet_text\"\"latitude:longitude\"",e);
        }

        Tweet postTweet = TwitterUtil.buildTweet(tweet_txt, lon, lat);

        return service.postTweet(postTweet);
       
    }

    @Override
    public Tweet showTweet(String[] args) {
        // TODO Auto-generated method stub
        if(args.length!=2){
            throw new IllegalArgumentException(
                "USAGE: TwitterCLIApp SHOW \"tweet_id");
        }

        String id_String= args[1];
        
        return service.showTweet(id_String, null);
        // return null;
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        // TODO Auto-generated method stub
        if(args.length<=0){
            throw new IllegalArgumentException(
                "USAGE: TwitterCLIApp DELETE \"id");
        }
        String[] args2= new String[args.length-1];

        for(int i=0; i<args.length-1; i++){
            args2[i]=args[i+1];
        }
        

        return service.deleteTweets(args2);
     

    }
    
}
