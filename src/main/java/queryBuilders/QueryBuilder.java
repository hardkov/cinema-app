package queryBuilders;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;

public interface QueryBuilder {
    Query build(CollectionReference collectionReference);
}
