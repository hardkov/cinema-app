package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.ScreeningDao;
import queryBuilders.QueryBuilder;

public class SeatsLimitScreeningQueryBuilder implements QueryBuilder {

    private int seatsLimit;

    public SeatsLimitScreeningQueryBuilder(int seatsLimit) {
        this.seatsLimit = seatsLimit;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        return collectionReference.whereEqualTo(ScreeningDao.seatsLimitField, seatsLimit);
    }
}
