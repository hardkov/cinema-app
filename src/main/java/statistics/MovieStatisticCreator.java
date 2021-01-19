package statistics;

import model.Ticket;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface MovieStatisticCreator {
    List<MovieStatistic> getMovieStatistics(Predicate<Ticket> predicate);
    Comparator<MovieStatistic> getComparator(SortOrder sortOrder);
}
