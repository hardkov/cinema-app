package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.*;

public class TicketDao {

    Firestore db;
    GenericDao<Ticket> ticketGenericDao;

    private static final String screeningField = "screening";
    private static final String customerField = "customer";
    private static final String priceField = "price";
    private static final String seatIdField = "seatId";

    public TicketDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.ticketGenericDao = new GenericDao<>(Ticket.class);
    }

    public boolean addTicket(Ticket ticket) {
        Map<String, Object> docData = createTicketDocData(ticket);
        if (docData == null) {
            return false;
        }
        ApiFuture<DocumentReference> writeResult = db.collection(ticketPath).add(docData);
        try {

            ticket.setId(writeResult.get().getId());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    // method return null if there is no customer of ticket
    private Map<String, Object> createTicketDocData(Ticket ticket) {
        Map<String, Object> docData = new HashMap<>();
        ScreeningDao screeningDao = new ScreeningDao();
        DocumentReference screeningReference = screeningDao.getScreeningReference(ticket.getScreening());
        docData.put(screeningField, screeningReference);
        CustomerDao customerDao = new CustomerDao();
        Customer customer = ticket.getCustomer();
        DocumentReference customerReference = customerDao.getCustomerReference(customer);
        if (!customerDao.doesCustomerExist(customer.getLogin())) {
            return null;
        }
        docData.put(customerField, customerReference);
        docData.put(priceField, ticket.getPrice());
        docData.put(seatIdField, ticket.getSeatId());
        return docData;
    }

    public DocumentReference getTicketReference(Ticket ticket) {
        return db.collection(ticketPath).document(ticket.getId());
    }

    public Ticket getTicket(String ticketId) {
        DocumentReference docRef = db.collection(ticketPath).document(ticketId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Ticket ticket = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                ScreeningDao screeningDao = new ScreeningDao();
                CustomerDao customerDao = new CustomerDao();
                DocumentReference screeningReference = document.get(screeningField, DocumentReference.class);
                Screening screening = screeningDao.getScreeningWithScreeningId(screeningReference.getId());
                DocumentReference customerReference = document.get(customerField, DocumentReference.class);
                Customer customer = customerDao.getCustomer(customerReference.getId());
                float price = document.get(priceField, Float.class);
                int seatId = document.get(seatIdField, Integer.class);
                ticket = new Ticket(screening, customer, price, seatId);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public List<Ticket> getAllTickets() {
        ApiFuture<QuerySnapshot> future = db.collection(ticketPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            ScreeningDao screeningDao = new ScreeningDao();
            CustomerDao customerDao = new CustomerDao();
            for (QueryDocumentSnapshot document : documents) {
                DocumentReference screeningReference = document.get(screeningField, DocumentReference.class);
                Screening screening = screeningDao.getScreeningWithScreeningId(screeningReference.getId());
                DocumentReference customerReference = document.get(customerField, DocumentReference.class);
                Customer customer = customerDao.getCustomer(customerReference.getId());
                float price = document.get(priceField, Float.class);
                int seatId = document.get(seatIdField, Integer.class);
                tickets.add(new Ticket(screening, customer, price, seatId));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public boolean removeTicket(Ticket ticket) {
        return ticketGenericDao.removeObject(ticketPath, ticket.getId());
    }

    public boolean updateTicket(Ticket ticket) {
        Map<String, Object> docData = createTicketDocData(ticket);
        if (docData == null) {
            return false;
        }
        ApiFuture<WriteResult> writeResult = db.collection(ticketPath).document(ticket.getId()).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }
}
