package validators;

import model.Screening;

import java.security.cert.TrustAnchor;
import java.util.List;

public class ScreeningBasePriceValidator implements IValidator<Screening> {
    @Override
    public boolean isValid(Screening obj, List<String> feedback) {
        if(obj.getBasePrice() < 0){
            if(feedback != null) feedback.add("Base price cannot be negative");
            return false;
        }

        return true;
    }
}
