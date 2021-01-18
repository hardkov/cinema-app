package validators;

import model.Screening;

import java.util.List;

public class ScreeningSeatsLimitValidator implements IValidator<Screening> {
    @Override
    public boolean isValid(Screening obj, List<String> feedback) {
        if(obj.getSeatsLimit() < 1 ){
            if(feedback != null) feedback.add("Seats limit has to be more than 0");
            return false;
        }

        if(obj.getSeatsLimit() > obj.getHall().getSeatsLimit()){
            if(feedback != null) feedback.add("Seats limit cannot be greater than hall's seats limit");
            return false;
        }

        return true;
    }
}
