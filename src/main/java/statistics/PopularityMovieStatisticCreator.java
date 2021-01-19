package statistics;

import comparators.PopularityComparator;
import daos.TicketDao;
import model.Movie;
import model.Ticket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static statistics.MovieStatisticKind.POPULARITY;

public class PopularityMovieStatisticCreator implements MovieStatisticCreator {

    private TicketDao ticketDao = new TicketDao();

    @Override
    public List<MovieStatistic> getMovieStatistics() {
        List<Ticket> tickets = ticketDao.getAllTickets();
        Map<Movie, AggregateValue<Integer>> movieMap = new HashMap<>();
        for (Ticket ticket: tickets) {
            Movie movie = ticket.getScreening().getMovie();
            if(!movieMap.containsKey(movie)) {
                AggregateValue<Integer> value = new PopularityAggregateValue(1);
                movieMap.put(movie, value);
                continue;
            }
            AggregateValue<Integer> oldValue = movieMap.get(movie);
            oldValue.addValue(1);
        }
        List<MovieStatistic> statistics = movieMap.entrySet().stream()
                .map( entry -> new MovieStatistic(entry.getKey(), entry.getValue(), POPULARITY))
                .collect(Collectors.toList());
        return statistics;
    }

    @Override
    public Comparator<MovieStatistic> getComparator(SortOrder sortOrder) {
        return new PopularityComparator(sortOrder);
    }

    @Override
    public String toString() {
        return "Popularity";
    }
}
