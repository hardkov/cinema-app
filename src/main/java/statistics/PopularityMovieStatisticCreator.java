package statistics;

import comparators.PopularityComparator;
import daos.TicketDao;
import model.Customer;
import model.Movie;
import model.Ticket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static statistics.MovieStatisticKind.POPULARITY;
import static statistics.SortOrder.DESCENDING;

public class PopularityMovieStatisticCreator implements MovieStatisticCreator {

    private TicketDao ticketDao = new TicketDao();
    private List<Ticket> tickets = ticketDao.getAllTickets();

    @Override
    public List<MovieStatistic> getMovieStatistics(Predicate<Ticket> predicate) {
        Map<Movie, AggregateValue<Integer>> movieMap = new HashMap<>();
        for (Ticket ticket: tickets) {
            if (predicate != null && predicate.test(ticket)) {
                continue;
            }
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

    public Movie getMoviePropositionForCustomer(Customer customer) {
        List<MovieStatistic> allMovies = getMovieStatistics(null);
        List<MovieStatistic> watchedMovies = getMovieStatistics(ticket -> !ticket.getCustomer().equals(customer));
        Comparator<MovieStatistic> comparator = getComparator(DESCENDING);
        allMovies.sort(comparator);
        watchedMovies.sort(comparator);

        for (MovieStatistic ms: watchedMovies) {
            if (ms.getMovie().getTitle().equals(allMovies.get(0).getMovie().getTitle())) {
                allMovies.remove(0);
            } else {
                break;
            }
        }
        if (allMovies.isEmpty()) {
            return null;
        } else {
            return allMovies.get(0).getMovie();
        }
    }

    @Override
    public String toString() {
        return "Popularity";
    }
}
