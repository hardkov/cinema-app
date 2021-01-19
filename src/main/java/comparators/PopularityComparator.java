package comparators;

import statistics.MovieStatistic;

import java.util.Comparator;

public class PopularityComparator implements Comparator<MovieStatistic> {

    @Override
    public int compare(MovieStatistic o1, MovieStatistic o2) {
        if (o1 != null && o2 != null) {
            Integer first = (Integer) o1.getAggregateValue().getValue();
            Integer second = (Integer) o2.getAggregateValue().getValue();
            return first.compareTo(second);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Popularity";
    }
}
