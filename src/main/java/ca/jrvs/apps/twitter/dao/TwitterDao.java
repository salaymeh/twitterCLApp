package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;



@Component
public class TwitterDao implements CrdDao<Tweet,String> {


    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH="/1.1/statuses/destroy/";

    private static final String QUERY_SYM="?";
    private static final String AMPERSAND="&";
    private static final String EQUAL="=";


    private static final int HTTP_OK=200;
    private HttpHelper httpHelper;


    @Autowired
    public TwitterDao(HttpHelper httpHelper){
        this.httpHelper=httpHelper;
    }


    // public TwitterDao() {
    // }


    @Override
    public Tweet create(Tweet tweet) {
        // TODO Auto-generated method stub

        URI uri;
        try{
            uri = getPostUri(tweet);
        }catch (URISyntaxException e){
            throw new IllegalArgumentException("Invalid tweet input: ",e);
        }
        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException {

        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String status = percentEscaper.escape(tweet.getText());

        String geoLocation = AMPERSAND+"long"+EQUAL+tweet.getCoordinates().getCoordinates().get(0)+AMPERSAND+"lat"+EQUAL+tweet.getCoordinates().getCoordinates().get(1)+AMPERSAND+"type"+EQUAL+tweet.getCoordinates().getType();

        String uri_str = API_BASE_URI + POST_PATH + QUERY_SYM + "status"+EQUAL+status+geoLocation;


        URI uri = new URI(uri_str);
        return uri;
    }




    @Override
    public Tweet findById(String id) {
        // TODO Auto-generated method stub
        URI uri;
        try{
            uri = getIdUri(id);
            
        }catch(URISyntaxException e){
            throw new IllegalArgumentException("invalid id input: ",e);
        }
        HttpResponse response = httpHelper.httpGet(uri);


        return parseResponseBody(response, HTTP_OK);
    }

    private URI getIdUri(String id) throws URISyntaxException {
        String uri_str = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id"+EQUAL+id;
        URI uri = new URI(uri_str);
        return uri;
    }


    @Override
    public Tweet deleteById(String id) {
        // TODO Auto-generated method stub
        URI uri;
        try{
            uri = getDeleteUri(id);
        }catch (URISyntaxException e){
            throw new IllegalArgumentException("invalid id input",e);
        }
        HttpResponse response = httpHelper.httpPost(uri);


        return parseResponseBody(response, HTTP_OK);
    }


    private URI getDeleteUri(String id) throws URISyntaxException {
        String uri_str = API_BASE_URI+DELETE_PATH+id+".json";
        URI uri = new URI(uri_str);
     

        return uri;
    }


    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
        Tweet tweet = null;
        int status = response.getStatusLine().getStatusCode();
        if(status != expectedStatusCode){
            try{
                EntityUtils.toString(response.getEntity());
            }catch(IOException e){
                System.out.println("response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status: "+status);
        }
        if(response.getEntity()==null){
            throw new RuntimeException("Empty response body");
        }
        String jsonStr;
        try{
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch(IOException e){
            throw new RuntimeException("Unable to convert JSON str to OBject",e);
        }
        
        try{
            tweet = JsonParser.toObjectFromJson(jsonStr,Tweet.class);

        }catch(IOException e){
            throw new RuntimeException("Unable to conver JSON str to Object", e);
        }

        return tweet;
        
    }
    
}
