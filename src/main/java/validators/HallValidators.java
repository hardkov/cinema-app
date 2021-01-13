package validators;

import model.Hall;
import model.Movie;

import java.util.LinkedList;
import java.util.List;

public class HallValidators implements IValidator<Hall>{
    public List<IValidator> validators;

    public HallValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new HallIdValidator());
        validators.add(new HallSeatsLimitValidator());
    }

    @Override
    public boolean isValid(Hall hall, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(hall, feedback)) return false;
        }

        return true;
    }

}
