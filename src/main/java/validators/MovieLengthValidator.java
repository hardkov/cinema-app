package validators;

import model.Movie;

import java.util.List;

public class MovieLengthValidator implements IValidator<Movie> {
    @Override
    public boolean isValid(Movie obj, List<String> feedback) {
        if(obj.getLength() < 1){
            if(feedback != null) feedback.add("Invalid movie length");
            return false;
        }

        return true;
    }
}
