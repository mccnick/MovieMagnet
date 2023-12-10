package com.MovieMagnet.Backend.Classes;

import com.MovieMagnet.Backend.Repositories.TheaterRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Theater {

    @Autowired
    private static TheaterRepository theaterRepository;

    @Schema(description = "Unique identifier of the theater")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Name of the theater")
    @Column(nullable = false)
    private String name;

    @Schema(description = "Physical address of the theater")
    @Column(nullable = false)
    private String address;

    @Schema(description = "Phone number of the theater")
    @Column(nullable = false)
    private String phoneNumber;

    @Schema(description = "ZIP code of the theater's location")
    @Column(nullable = false)
    private String zip;

    @Schema(description = "City where the theater is located")
    @Column(nullable = false)
    private String city;

    @Schema(description = "List of movies available at the theater")
    @ManyToMany
    @Column(nullable = false, name = "movies")
    @JsonIgnore
    private List<Movie> movieList;

    @Schema(description = "List of showtimes available at the theater")
    @OneToMany
    @JsonIgnore
    private List<Showtime> showtimeList;

    private String url;

    public Theater(){}

    public Theater (String name, String address, String phoneNumber, String zip, String city)
    {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.zip = zip;
        this.city = city;
        this.movieList = new ArrayList<>();
        this.showtimeList = new ArrayList<>();
    }

    public static Theater findTheater(List<Theater> theaterList, String name) {

        Theater theater = null;
        for (Theater t : theaterList)
            if (t.getName().equals(name)){
                theater = t;
            }
        return theater;
    }

    public static Theater findByName(String name) {
        return theaterRepository.findByName(name);
    }

    public int getId()
{
    return id;
}

    public void setId()
{
    this.id = id;
}
    public String getName()
{
    return this.name;
}

    public void setName(String name)
{
    this.name = name;
}

    public String getAddress()
{
    return this.address;
}

    public void setAddress(String address)
{
    this.address = address;
}

    public String getPhoneNumber()
{
    return this.phoneNumber;
}

    public void setPhoneNumber(String phoneNumber)
{
    this.phoneNumber = phoneNumber;
}

    public void setZip(String zip) { this.zip = zip; }

    public String getZip() {return this.zip; }

    public void setCity(String city) { this.city = city; }

    public String getCity() { return this.city; }

    public List<Showtime> getShowtimeList() {
        return showtimeList;
    }

    public void setShowtimeList(List<Showtime> showtimeList) {
        this.showtimeList = showtimeList;
    }

    public void addShowtime(Showtime showtime){
        this.showtimeList.add(showtime);
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void addMovie(Movie movie){
        this.movieList.add(movie);
    }

    public void clearMovieList() {this.movieList = new ArrayList<>(); }

    public String getURL() { return url; }

    public void setURL(String newURL) { this.url = newURL; }

    @Override
    public String toString()
    {
        return name + " " + address + " " + phoneNumber + " " + zip + " " + city;
    }
}
