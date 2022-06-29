package ca.jrvs.apps.twitter.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonIgnoreProperties({
    "symbols",
    "urls"    
})
public class Entities {
    
    private List<Hashtag> hashtags;
    private List<UserMention> user_mentions;


    public Entities() {
    }

    public Entities(List<Hashtag> hashtags, List<UserMention> user_mentions) {
        this.hashtags = hashtags;
        this.user_mentions = user_mentions;
    }

    public List<Hashtag> getHashtags() {
        return this.hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<UserMention> getUser_mentions() {
        return this.user_mentions;
    }

    public void setUser_mentions(List<UserMention> user_mentions) {
        this.user_mentions = user_mentions;
    }

    public Entities hashtags(List<Hashtag> hashtags) {
        setHashtags(hashtags);
        return this;
    }

    public Entities user_mentions(List<UserMention> user_mentions) {
        setUser_mentions(user_mentions);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Entities)) {
            return false;
        }
        Entities entities = (Entities) o;
        return Objects.equals(hashtags, entities.hashtags) && Objects.equals(user_mentions, entities.user_mentions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashtags, user_mentions);
    }

    @Override
    public String toString() {
        return "{" +
            " hashtags='" + getHashtags() + "'" +
            ", user_mentions='" + getUser_mentions() + "'" +
            "}";
    }


}
