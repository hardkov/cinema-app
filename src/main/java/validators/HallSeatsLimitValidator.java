package validators;

import model.Hall;

import java.util.List;

public class HallSeatsLimitValidator implements IValidator<Hall>{
    @Override
    public boolean isValid(Hall obj, List<String> feedback) {
        if(obj.getSeatsLimit() <= 0){
            if(feedback != null) feedback.add("Seats limit must be greater than 0");
            return false;
        }

        return true;
    }
}
