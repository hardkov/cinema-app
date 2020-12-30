package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import daos.MovieDao;
import daos.ScreeningDao;
import model.Movie;
import queryBuilders.QueryBuilder;

public class MovieScreeningQueryBuilder implements QueryBuilder {

    private Movie movie;

    public MovieScreeningQueryBuilder(Movie movie) {
        this.movie = movie;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        DocumentReference movieReference = new MovieDao().getMovieReference(movie);
        return collectionReference.whereEqualTo(ScreeningDao.moveField, movieReference);
    }
}
