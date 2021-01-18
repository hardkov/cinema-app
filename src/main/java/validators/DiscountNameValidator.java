package validators;

import daos.DiscountDao;
import model.Discount;

import java.util.List;

public class DiscountNameValidator implements IValidator<Discount>{
    DiscountDao discountDao = new DiscountDao();

    @Override
    public boolean isValid(Discount obj, List<String> feedback) {
        String name = obj.getName();
        if(name.length() < 1){
            if(feedback != null) feedback.add("Discount name must be at least one character long");
            return false;
        }

        if(discountDao.doesDiscountExist(name)){
            if(feedback != null) feedback.add("Discount with that name already exists");
            return false;
        }

        return true;
    }
}
