package statistics;

import java.util.Comparator;
import java.util.List;

public interface MovieStatisticCreator {
    List<MovieStatistic> getMovieStatistics();
    Comparator<MovieStatistic> getComparator();
}
