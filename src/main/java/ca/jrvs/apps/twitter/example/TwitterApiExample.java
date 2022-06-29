package ca.jrvs.apps.twitter.example;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class TwitterApiExample {
  // private static String CONSUMER_KEY = System.getenv("consumerKey");
  // private static String CONSUMER_SECRET = System.getenv("consumerSecret");
  // private static String ACCESS_TOKEN = System.getenv("accessToken");
  // private static String TOKEN_SECRET = System.getenv("tokenSecret");
  
 
  public static void main (String[] args) throws Exception {

    String consumerKey = "cWLeTaYYkXv7GHb816yeuoIRO";
    String consumerSecret = "l6pdXvNHfpYMKpveQQb42yWQs1ZA64FxNOgA5tksruNvBSyzkE";
    String accessToken = "1511094511514042371-sE4PFrhtCkBRNZjO2NNve8nlRcEqsN";
    String tokenSecret =  "ZegvEd3cpx6o2DZq3YuSGJwxuO1WwIWWUE34u35HwFxA5";

    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    
    String status = "hello world testing something to see ";
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    HttpPost request = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status="+ percentEscaper.escape(status)+"&long=12.55&lat=37.7821120598956&type=Point");

    consumer.sign(request);
    System.out.println("http request header");
    //Arrays.stream(request.getAllHeaders()).forEach(System.out::println);

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpResponse response = httpClient.execute(request);
    //System.out.println(EntityUtils.toString(response.getEntity()));
    
    
  }
}

