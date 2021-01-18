package validators;

import model.Discount;

import java.util.List;

public class DiscountValueValidator implements IValidator<Discount>{
    @Override
    public boolean isValid(Discount obj, List<String> feedback) {
        Float value = obj.getValue();
        if(value > 1){
            if(feedback != null) feedback.add("Discount value must be less than 1");
            return false;
        }

        if(value < 0){
            if(feedback != null) feedback.add("Discount value must be greater than 1");
            return false;
        }

        return true;
    }
}
