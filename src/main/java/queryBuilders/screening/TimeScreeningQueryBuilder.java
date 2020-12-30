package queryBuilders.screening;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import daos.ScreeningDao;
import helpers.DateConverter;
import queryBuilders.QueryBuilder;

import java.time.LocalTime;

public class TimeScreeningQueryBuilder implements QueryBuilder {

    private LocalTime time;

    public TimeScreeningQueryBuilder(LocalTime time) {
        this.time = time;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        String timeString = new DateConverter().getTimeString(time);
        return collectionReference.whereEqualTo(ScreeningDao.timeField, timeString);
    }
}
