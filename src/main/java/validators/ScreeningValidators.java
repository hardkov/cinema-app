package validators;

import model.Customer;
import model.Screening;

import java.util.LinkedList;
import java.util.List;

public class ScreeningValidators implements IValidator<Screening>{
    public List<IValidator> validators;

    public ScreeningValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new ScreeningBasePriceValidator());
        validators.add(new ScreeningSeatsLimitValidator());
        validators.add(new ScreeningDateValidator());
    }

    @Override
    public boolean isValid(Screening screening, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(screening, feedback)) return false;
        }

        return true;
    }
}
