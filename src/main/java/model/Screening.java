package model;

import java.time.LocalTime;

public class Screening {
    private String id;
    private Movie movie;
    private MovieType movieType;
    private LocalTime time;
    private Hall hall;
    private int seatsLimit;
    private float basePrice;

    public Screening() { }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s hall: %d seats limit: %d base price: %.2f", movie.getTitle().toString(), movieType.toString(), time.toString(),
                hall.getHallId(), seatsLimit, basePrice);
    }
}
