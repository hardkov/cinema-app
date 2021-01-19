package validators;

import daos.ScreeningDao;
import helpers.ScreeningHelpers;
import model.Screening;

import java.util.List;

public class ScreeningDateTimeValidator implements IValidator<Screening> {
    private ScreeningDao screeningDao = new ScreeningDao();

    @Override
    public boolean isValid(Screening obj, List<String> feedback) {
        List<Screening> screenings = screeningDao.getAllScreenings();

        for(Screening screening : screenings){
            if(screening.getHall().equals(obj.getHall()) && ScreeningHelpers.doScreeningsOverlap(screening, obj)){
                if(feedback != null) feedback.add("Screening overlaps with another screening");
                return false;
            }
        }

        return true;
    }
}
