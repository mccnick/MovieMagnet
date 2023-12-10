package com.MovieMagnet.Backend.Classes;



import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeByMovie {
    public String title;
    public String rating;
    public String runtime;
    public List<String> times;

    public ShowtimeByMovie(String title, String rating, String runtime) {
        this.title = title;
        this.rating = rating;
        this.runtime = runtime;
        this.times = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<String> getTimes() {
        return this.times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public void addTime(String time) {
        this.times.add(time);
    }
}
