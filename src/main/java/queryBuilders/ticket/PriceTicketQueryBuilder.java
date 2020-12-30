package queryBuilders.ticket;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.ScreeningDao;
import daos.TicketDao;
import queryBuilders.QueryBuilder;

public class PriceTicketQueryBuilder implements QueryBuilder {

    private float price;

    public PriceTicketQueryBuilder(float price) {
        this.price = price;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        return collectionReference.whereEqualTo(TicketDao.priceField, price);
    }
}
