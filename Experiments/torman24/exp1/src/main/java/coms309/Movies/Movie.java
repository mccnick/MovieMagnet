package coms309.Movies;

public class Movie {
    private String title;
    private String rating;
    private String releaseDate;
    private String director;

    public Movie(){

    }

    public Movie(String title, String rating, String releaseDate, String director){
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.director = director;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getRating(){
        return this.rating;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getReleaseDate(){
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getDirector(){
        return this.director;
    }

    public void setDirector(String director){
        this.director = director;
    }

    @Override
    public String toString(){
        return title + " " + rating + " " + releaseDate + " " + director;
    }
}
