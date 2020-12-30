package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import daos.HallDao;
import daos.ScreeningDao;
import model.Hall;
import queryBuilders.QueryBuilder;

public class HallScreeningQueryBuilder implements QueryBuilder {

    private Hall hall;

    public HallScreeningQueryBuilder(Hall hall) {
        this.hall = hall;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        DocumentReference hallReference = new HallDao().getHallReference(hall);
        return collectionReference.whereEqualTo(ScreeningDao.hallField, hallReference);
    }
}
