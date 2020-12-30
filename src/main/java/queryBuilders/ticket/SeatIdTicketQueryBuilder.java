package queryBuilders.ticket;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.CustomerDao;
import daos.ScreeningDao;
import daos.TicketDao;
import queryBuilders.QueryBuilder;

public class SeatIdTicketQueryBuilder implements QueryBuilder {

    private int seatId;

    public SeatIdTicketQueryBuilder(int seatId) {
        this.seatId = seatId;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        return collectionReference.whereEqualTo(TicketDao.seatIdField, seatId);
    }
}
