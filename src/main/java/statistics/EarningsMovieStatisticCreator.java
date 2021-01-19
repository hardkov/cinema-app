package statistics;

import comparators.EarningsComparator;
import daos.TicketDao;
import model.Movie;
import model.Ticket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static statistics.MovieStatisticKind.EARNINGS;

public class EarningsMovieStatisticCreator implements MovieStatisticCreator {

    private TicketDao ticketDao = new TicketDao();

    @Override
    public List<MovieStatistic> getMovieStatistics(Predicate<Ticket> predicate) {
        List<Ticket> tickets = ticketDao.getAllTickets();
        Map<Movie, AggregateValue<Double>> movieMap = new HashMap<>();
        for (Ticket ticket: tickets) {
            if (predicate != null && predicate.test(ticket)) {
                continue;
            }
            Movie movie = ticket.getScreening().getMovie();
            if(!movieMap.containsKey(movie)) {
                AggregateValue<Double> value = new EarningsAggregateValue((double) ticket.getPrice());
                movieMap.put(movie, value);
                continue;
            }
            AggregateValue<Double> oldValue = movieMap.get(movie);
            oldValue.addValue((double) ticket.getPrice());
        }
        List<MovieStatistic> statistics = movieMap.entrySet().stream()
                .map( entry -> new MovieStatistic(entry.getKey(), entry.getValue(), EARNINGS))
                .collect(Collectors.toList());
        return statistics;
    }

    @Override
    public Comparator<MovieStatistic> getComparator(SortOrder sortOrder) {
        return new EarningsComparator(sortOrder);
    }

    @Override
    public String toString() {
        return "Earnings";
    }
}
