package ca.jrvs.apps.twitter.dao.helper;



import java.io.IOException;
import java.net.URI;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;



@Component
public class TwitterHttpHelper implements HttpHelper{



    private OAuthConsumer consumer;
    private HttpClient httpClient;
    
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String tokenSecret;


    @Autowired
    public TwitterHttpHelper(){
        Config config = new Config();
        List<String> keys = config.ConfigRun(); 
        // this.consumerKey=System.getenv("consumerKey");
        // this.consumerSecret=System.getenv("consumerSecret");
        // this.accessToken=System.getenv("accessToken");
        // this.tokenSecret=System.getenv("tokenSecret");
        this.consumerKey=keys.get(0);
        this.consumerSecret=keys.get(1);
        this.accessToken=keys.get(2);
        this.tokenSecret=keys.get(3);
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = new DefaultHttpClient();
    }
    
    public TwitterHttpHelper(String consumerKey,String consumerSecret,String accessToken,String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = new DefaultHttpClient();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        // TODO Auto-generated method stub
        try{
            return executeHttpRequest(HttpMethod.POST, uri, null);
        }catch(OAuthException | IOException e){
            throw new RuntimeException("Failed to execute",e);
        }
   
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        // TODO Auto-generated method stub
        try{
            return executeHttpRequest(HttpMethod.GET, uri, null);
        }catch(OAuthException | IOException e){
            throw new RuntimeException("Failed to execute",e);
        }
    }

    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity) throws OAuthException, IOException{
        
        if(method == HttpMethod.GET){
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);


        }else if (method == HttpMethod.POST){
            
            HttpPost request = new HttpPost(uri);
            if(stringEntity !=null){
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);


        }else{
            throw new IllegalArgumentException("unknown Http Method: "+ method.name());
        }
       
    }
    
}
