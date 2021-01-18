package validators;

import model.Discount;

import java.util.LinkedList;
import java.util.List;

public class DiscountValidators implements IValidator<Discount>{
    LinkedList<IValidator> validators;

    public DiscountValidators(){
        validators = new LinkedList<>();
        validators.add(new DiscountNameValidator());
        validators.add(new DiscountValueValidator());
    }

    @Override
    public boolean isValid(Discount discount, List<String> feedback) {
        for(IValidator validator : validators){
            if(!validator.isValid(discount, feedback)) return false;
        }

        return true;
    }
}
