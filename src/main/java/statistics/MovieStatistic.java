package statistics;

import model.Movie;

public class MovieStatistic {
    Movie movie;
    AggregateValue aggregateValue;

    public MovieStatistic(Movie movie, AggregateValue aggregateValue) {
        this.movie = movie;
        this.aggregateValue = aggregateValue;
    }

    public Movie getMovie() {
        return movie;
    }

    public AggregateValue getAggregateValue() {
        return aggregateValue;
    }

    @Override
    public String toString() {
        return "Movie: " + movie.getTitle() + ", bought tickets: " + aggregateValue.getValue();
    }
}
