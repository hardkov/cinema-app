package validators;

import helpers.TicketHelpers;
import model.Ticket;

import java.util.List;

public class TicketSeatIdValidator implements IValidator<Ticket> {
    @Override
    public boolean isValid(Ticket obj, List<String> feedback) {
        if(obj.getSeatId() == -1){
            if(feedback != null) feedback.add("No free seats left");
            return false;
        }

        return true;
    }
}
