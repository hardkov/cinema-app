package validators;

import model.Discount;

import java.util.List;

public class DiscountNameValidator implements IValidator<Discount>{
    @Override
    public boolean isValid(Discount obj, List<String> feedback) {
        String name = obj.getName();
        if(name.length() < 1){
            if(feedback != null) feedback.add("Discount name must be at least one character long");
            return false;
        }

        return true;
    }
}
