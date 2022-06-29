package ca.jrvs.apps.twitter.example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.dto.Company;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TwitterUtil;




public class sandbox {
 
    public static void main(String[] args) {

        
        TwitterDao dao;
        TwitterService service;
        String consumerKey = "cWLeTaYYkXv7GHb816yeuoIRO";
        String consumerSecret = "l6pdXvNHfpYMKpveQQb42yWQs1ZA64FxNOgA5tksruNvBSyzkE";
        String accessToken = "1511094511514042371-sE4PFrhtCkBRNZjO2NNve8nlRcEqsN";
        String tokenSecret =  "ZegvEd3cpx6o2DZq3YuSGJwxuO1WwIWWUE34u35HwFxA5";

        
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);

        dao = new TwitterDao(httpHelper);

        service = new TwitterService(dao);



        





      

        
        

    }


}
