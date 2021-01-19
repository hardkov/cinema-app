package statistics;

import model.Movie;

public class MovieStatistic {

    private Movie movie;
    private AggregateValue aggregateValue;
    private MovieStatisticKind kind;

    public MovieStatistic(Movie movie, AggregateValue aggregateValue, MovieStatisticKind kind) {
        this.movie = movie;
        this.aggregateValue = aggregateValue;
        this.kind = kind;
    }

    public Movie getMovie() {
        return movie;
    }

    public AggregateValue getAggregateValue() {
        return aggregateValue;
    }

    public MovieStatisticKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "Movie: " + movie.getTitle() + ", bought tickets: " + aggregateValue.getValue();
    }
}
