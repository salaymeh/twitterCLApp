package ca.jrvs.apps.twitter.dao.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {
 
    
    public List<String> ConfigRun(){
        List<String> keys = new ArrayList<>();
        Properties prop = new Properties();

        try (FileInputStream ip = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(ip);
            keys.add(prop.getProperty("consumerKey"));
            keys.add(prop.getProperty("consumerSecret"));
            keys.add(prop.getProperty("accessToken"));
            keys.add(prop.getProperty("tokenSecret"));




        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return keys;

    } 

    public static void main(String[] args) {
      Config config = new Config();
      config.ConfigRun();
    }
}
