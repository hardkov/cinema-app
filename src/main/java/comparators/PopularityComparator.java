package comparators;

import statistics.MovieStatistic;
import statistics.SortOrder;

import java.util.Comparator;

public class PopularityComparator implements Comparator<MovieStatistic> {

    private SortOrder sortOrder;

    public PopularityComparator(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(MovieStatistic o1, MovieStatistic o2) {
        if (o1 != null && o2 != null) {
            Integer first = (Integer) o1.getAggregateValue().getValue();
            Integer second = (Integer) o2.getAggregateValue().getValue();
            if (sortOrder == SortOrder.ASCENDING) {
                return first.compareTo(second);
            } else {
                return second.compareTo(first);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Popularity";
    }
}
