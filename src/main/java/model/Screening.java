package model;

import java.time.LocalTime;

public class Screening {
    private Movie movie;
    private MovieType movieType;
    private LocalTime time;
    private Hall hall;
    private int seatsLimit;
    private float basePrice;

    public Screening() {
    }

    public Screening(Movie movie, MovieType movieType, LocalTime time, Hall hall, int seatsLimit, float basePrice) {
        this.movie = movie;
        this.movieType = movieType;
        this.time = time;
        this.hall = hall;
        this.seatsLimit = seatsLimit;
        this.basePrice = basePrice;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public LocalTime getTime() {
        return time;
    }

    public Hall getHall() {
        return hall;
    }

    public int getSeatsLimit() {
        return seatsLimit;
    }

    public float getBasePrice() {
        return basePrice;
    }
}
