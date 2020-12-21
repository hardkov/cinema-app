package model;

import java.time.LocalDate;

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
}
