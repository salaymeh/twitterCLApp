package ca.jrvs.apps.twitter.util;

import java.util.ArrayList;
import java.util.List;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TwitterUtil {
    

    public static Tweet buildTweet(String status, Double lon, Double lat){

        Tweet tweet = new Tweet();
        List<Double> lonLat = new ArrayList<>();
        tweet.setText(status);
        lonLat.add(lon);
        lonLat.add(lat);
        Coordinates location = new Coordinates();
        location.setType("Point");
        location.setCoordinates(lonLat);
        tweet.setCoordinates(location);
        

        

        return tweet;


    }
}
