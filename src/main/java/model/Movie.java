package model;

import java.time.LocalDate;
import java.util.Objects;

public class Movie {
    private String title;
    private LocalDate date;
    private MovieGenre genre;
    private int length;

    public Movie() {}

    public Movie(String title, LocalDate date, MovieGenre genre, int length) {
        this.title = title;
        this.date = date;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
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

    @Override
    public String toString(){
        return String.format("%s, %s, genre: %s", title, date.toString(), genre);
    }
}
