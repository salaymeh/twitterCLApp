package ca.jrvs.apps.twitter.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
// import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
// import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;




@Component
public class TwitterCLIApp {

    public static final String USAGE="USAGE: TwitterCLIApp post|show|delete [options]";

    private Controller controller;



    @Autowired
    public TwitterCLIApp(Controller controller ){
        this.controller = controller;
    }
    


    // public static void main(String[] args) throws IOException {

        
    //     Properties prop = new Properties();
    //     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    //     InputStream inputStream = classLoader.getResourceAsStream("/config.properties");
    //     prop.load(inputStream);
        

    //     String consumerKey = System.getenv("consumerKey");
    //     String consumerSecret = System.getenv("consumerSecret");
    //     String accessToken = System.getenv("accessToken");
    //     String tokenSecret = System.getenv("tokenSecret");

    //     HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    //     CrdDao dao = new TwitterDao(httpHelper);
    //     Service service = new TwitterService(dao);
    //     Controller controller = new TwitterController(service);
    //     TwitterCLIApp app = new TwitterCLIApp(controller);

    //     System.out.println(consumerKey);
    //     System.out.println(consumerSecret);
    //     System.out.println(accessToken);
    //     System.out.println(tokenSecret);

        
    //     app.run(args);
       
    // }


    public void run(String[] args){
        if(args.length==0){
            throw new IllegalArgumentException(USAGE);
        }
        switch(args[0].toLowerCase()){
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                List<Tweet> deletedTweets = controller.deleteTweet(args);
                for(Tweet tweet: deletedTweets){
                    printTweet(tweet);
                }
                break;
            default:
                throw new IllegalArgumentException(USAGE);
            }
        }

    private void printTweet(Tweet tweet){
        try{
            System.out.println(JsonParser.toJson(tweet, true, false));
        }catch(JsonProcessingException e){
            throw new RuntimeException("Unable to conver tweet object to string",e);
        }
    }
    
}
