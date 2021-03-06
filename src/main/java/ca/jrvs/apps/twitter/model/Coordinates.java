package ca.jrvs.apps.twitter.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "coordinates",
    "type"
})
public class Coordinates {
    
    @JsonProperty("coordinates")

    private List<Double> coordinates; 

    @JsonProperty("type")
    private String type;

    @JsonProperty("coordinates")
    public List<Double> getCoordinates(){
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(List<Double> coordinates){
        this.coordinates=coordinates;
    }

    @JsonProperty("type")
    public String getType(){
        return type;
    }
    @JsonProperty("type")
    public void setType(String type){
        this.type=type;
    }




    
}
