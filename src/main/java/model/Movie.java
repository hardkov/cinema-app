package model;

import java.time.LocalDate;
import java.util.Objects;

public class Movie {
    private String title;
    private LocalDate date;
    private String genre;

    public Movie(){}

    public Movie(String title, LocalDate date, String genre){
        this.title = title;
        this.date = date;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return title.equals(movie.title) &&
                date.equals(movie.date) &&
                genre.equals(movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, genre);
    }
}
