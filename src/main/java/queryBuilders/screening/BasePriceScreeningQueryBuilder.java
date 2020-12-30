package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.ScreeningDao;
import queryBuilders.QueryBuilder;

public class BasePriceScreeningQueryBuilder implements QueryBuilder {

    private float basePrice;

    public BasePriceScreeningQueryBuilder(float basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        return collectionReference.whereEqualTo(ScreeningDao.basePriceField, basePrice);
    }
}
