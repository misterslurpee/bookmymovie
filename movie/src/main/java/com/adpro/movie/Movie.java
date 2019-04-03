package com.adpro.movie;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.json.JSONObject;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    protected Movie() {}

    public Movie(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Movie parseMovie(JSONObject movieJson) {
        String name = movieJson.getString("original_title");
        String description = movieJson.getString("overview");
        return new Movie(name, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
