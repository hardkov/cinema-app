package validators;

import model.Screening;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScreeningDateValidator implements IValidator<Screening> {
    @Override
    public boolean isValid(Screening obj, List<String> feedback) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            obj.getDate().format(formatter);
        } catch (NullPointerException e){
            if(feedback != null) feedback.add("Invalid date format");
            return false;
        }
        return true;
    }
}
