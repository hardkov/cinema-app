package validators;

import model.Screening;
import model.Ticket;

import java.util.LinkedList;
import java.util.List;

public class TicketValidators implements IValidator<Ticket> {
    public List<IValidator> validators;

    public TicketValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new TicketSeatIdValidator());
        validators.add(new TicketScreeningDateValidator());
    }

    @Override
    public boolean isValid(Ticket ticket, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(ticket, feedback)) return false;
        }

        return true;
    }
}
