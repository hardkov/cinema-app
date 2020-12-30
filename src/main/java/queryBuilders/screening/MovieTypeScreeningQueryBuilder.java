package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.ScreeningDao;
import model.MovieType;
import queryBuilders.QueryBuilder;

public class MovieTypeScreeningQueryBuilder implements QueryBuilder {

    private MovieType movieType;

    public MovieTypeScreeningQueryBuilder(MovieType movieType) {
        this.movieType = movieType;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        return collectionReference.whereEqualTo(ScreeningDao.typeField, movieType);
    }
}
