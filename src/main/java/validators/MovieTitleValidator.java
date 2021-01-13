package validators;

import model.Movie;

import java.util.List;

public class MovieTitleValidator implements IValidator<Movie> {
    @Override
    public boolean isValid(Movie obj, List<String> feedback) {
        if(obj.getTitle().length() < 1){
            if(feedback != null) feedback.add("Title must be at least 1 character long");
            return false;
        }

        return true;
    }
}
