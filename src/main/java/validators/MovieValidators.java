package validators;

import model.Movie;
import model.User;

import java.util.LinkedList;
import java.util.List;

public class MovieValidators implements IValidator<Movie>{
    public List<IValidator> validators;

    public MovieValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new MovieTitleValidator());
        validators.add(new MovieDateValidator());
        validators.add(new MovieLengthValidator());
    }

    @Override
    public boolean isValid(Movie movie, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(movie, feedback)) return false;
        }

        return true;
    }

}
