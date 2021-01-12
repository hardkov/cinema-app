package comparators;

import model.Movie;

import java.util.Comparator;

public class TitleComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1 != null && o2 != null) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Title";
    }
}
