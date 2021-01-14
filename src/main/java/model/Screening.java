package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Screening {
    private String id;
    private Movie movie;
    private MovieType movieType;
    private LocalTime time;
    private LocalDate date;
    private Hall hall;
    private int seatsLimit;
    private float basePrice;

    public Screening() { }

    public Screening(Movie movie, MovieType movieType, LocalTime time, LocalDate date, Hall hall, int seatsLimit, float basePrice) {
        this.movie = movie;
        this.movieType = movieType;
        this.time = time;
        this.date = date;
        this.hall = hall;
        this.seatsLimit = seatsLimit;
        this.basePrice = basePrice;
    }

    public Screening(String id, Movie movie, MovieType movieType, LocalTime time, LocalDate date, Hall hall, int seatsLimit, float basePrice) {
        this(movie, movieType, time, date, hall, seatsLimit, basePrice);
        this.id = id;
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

    public LocalDate getDate() {
        return date;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screening)) return false;
        Screening screening = (Screening) o;
        return seatsLimit == screening.seatsLimit &&
                Float.compare(screening.basePrice, basePrice) == 0 &&
                movie.equals(screening.movie) &&
                movieType == screening.movieType &&
                time.equals(screening.time) &&
                hall.equals(screening.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, movieType, time, hall, seatsLimit, basePrice);
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %s, hall: %d, seats limit: %d, base price: %.2f", movie.getTitle().toString(), movieType.toString(), time.toString(),
                hall.getHallId(), seatsLimit, basePrice);
    }
}
