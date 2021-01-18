package validators;

import model.Ticket;

import java.time.LocalDate;
import java.util.List;

public class TicketScreeningDateValidator implements IValidator<Ticket> {
    @Override
    public boolean isValid(Ticket obj, List<String> feedback) {
        if(obj.getScreening().getDate().isBefore(LocalDate.now())){
            if(feedback != null) feedback.add("This screening was in the past");
            return false;
        }

        return true;
    }
}
