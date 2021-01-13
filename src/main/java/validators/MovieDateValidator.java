package validators;

import model.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovieDateValidator implements IValidator<Movie> {
    @Override
    public boolean isValid(Movie obj, List<String> feedback) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            obj.getDate().format(formatter);
        } catch (NullPointerException e){
            if(feedback != null) feedback.add("Invalid date format");
            return false;
        }

        if(obj.getDate().isBefore(LocalDate.of(1850, 1, 1))){
            if(feedback != null) feedback.add("Date is too early for a movie");
            return false;
        }

        return true;
    }
}
