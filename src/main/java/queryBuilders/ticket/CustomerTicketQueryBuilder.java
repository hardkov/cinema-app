package queryBuilders.ticket;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Query;
import daos.CustomerDao;
import daos.ScreeningDao;
import daos.TicketDao;
import model.Customer;
import queryBuilders.QueryBuilder;

public class CustomerTicketQueryBuilder implements QueryBuilder {

    private Customer customer;

    public CustomerTicketQueryBuilder(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Query build(CollectionReference collectionReference) {
        DocumentReference customerReference = new CustomerDao().getCustomerReference(customer);
        return collectionReference.whereEqualTo(TicketDao.customerField, customerReference);
    }
}
