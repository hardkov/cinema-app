package validators;

import model.Screening;

import java.util.List;

public class ScreeningTimeValidator implements IValidator<Screening> {
    @Override
    public boolean isValid(Screening obj, List<String> feedback) {
        if(obj.getTime() == null){
            if(feedback != null) feedback.add("Invalid time format");
            return false;
        }
        return true;
    }
}
