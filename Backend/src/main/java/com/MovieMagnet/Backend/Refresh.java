package com.MovieMagnet.Backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.MovieMagnet.Backend.Classes.Movie;
import com.MovieMagnet.Backend.Classes.Theater;
import com.MovieMagnet.Backend.Classes.Showtime;
import com.MovieMagnet.Backend.Repositories.MovieRepository;
import com.MovieMagnet.Backend.Repositories.ShowtimeRepository;
import com.MovieMagnet.Backend.Repositories.TheaterRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Refresh {

    public static List<Movie> movieRequest() throws IOException {

        String googleUrl = "https://www.google.com/search?q=currently+showing+movies+ames&gl=us";
        Document doc = Jsoup.connect(googleUrl).get();
        Elements results = doc.select("div.dAassd");

        List<Movie> movieList = new ArrayList<>();

        int c = 0;
        for (Element result : results) {
            String genre = result.select("div.cp7THd.FozYP").text();
            String title = result.select("div[jsname]").text();
            String rating = result.select("div.zqhAOd").text();
            if (rating.equals("PG-13 (USA)")) { rating = "PG13"; }
            String runtimeTemp = result.select("div.dAassd").text();
            String[] split = runtimeTemp.split(" ");
            String runtime = "";
            for (String string : split) {
                if ((string.matches("[0-9]+h"))) { runtime = runtime + string + " "; }
                else if (string.matches("[0-9]+m$")) { runtime = runtime + string; }
            }

            Movie movie = new Movie(title, rating, genre, runtime);
            movieList.add(movie);

            c++;
        }
        return movieList;
    }

    public static List<Theater> theaterRequest() throws IOException {

        List<Theater> theaterList = new ArrayList<>();
        int c = 0;

        for (Element result : urlRequest()) {
            String name = result.select("[href^=/showtimes/cinema/US/]").text();
            String phoneNumber = result.select("[itemprop=telephone]").text();
            String address = result.select("[itemprop=streetAddress]").text();
            String zip = result.select("[itemprop=postalCode]").text();
            String city = result.select("[itemprop=addressLocality]").text();

            Theater theater = new Theater(name, address, phoneNumber, zip, city);
            theaterList.add(theater);

            c++;
        }

        return theaterList;
    }

    public static List<String> RefreshTheaters() throws IOException {

        List<String> output = new ArrayList<>();

        int c = 0;
        for (Element result : urlRequest()) {
            String name = result.select("[href^=/showtimes/cinema/US/]").text();
            String movies = result.select("[href^=/showtimes/title/]").text();
            movies = movies + "  ";
            String[] movieList = movies.split(" \\(+[0-9]+[0-9]+[0-9]+[0-9]+\\)+  ");
            String[] showtimes = result.select("[class=showtimes]").text().split("Get Tickets ");
            if (name.equals("Valle Drive-In")) { showtimes = result.select("[class=showtimes]").text().split(" pm"); }

            for (int i = 0; i < movieList.length; i++) {

                String movie = movieList[i];

                if (!name.equals("Valle Drive-In")) {
                    String[] times = null;
                    if (showtimes.length != 1) { times = showtimes[i + 1].split(" | "); }
                    else { times = showtimes[i].split(" | "); }
                    for (String t : times) {
                        if (!t.equals("|") && !t.equals("pm") && !t.equals("am")) { output.add(name + "     " + movie + "     " + t); }
                    }
                } else {
                    if (showtimes[i].startsWith(" ")) { output.add(name + "     " + movie + "     " + showtimes[i].substring(1)); }
                    else { output.add(name + "     " + movie + "     " + showtimes[i]); }
                }
            }
            c++;
        }
        return output;
    }

    private static Elements urlRequest() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);

        String googleUrl = "https://www.imdb.com/showtimes/US/50011/" + currentDate + "?ref_=sh_dt";
        Document doc = Jsoup.connect(googleUrl).get();
        return doc.select("[itemType^=http://schema.org/Movietheater]");
    }
}