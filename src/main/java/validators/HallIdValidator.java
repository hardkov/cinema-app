package validators;

import daos.HallDao;
import model.Hall;

import java.util.List;

public class HallIdValidator implements IValidator<Hall>{
    HallDao hallDao = new HallDao();
    @Override
    public boolean isValid(Hall obj, List<String> feedback) {
        if(obj.getHallId() < 0){
            if(feedback != null) feedback.add("Invalid hall id");
            return false;
        }

        if(hallDao.doesHallExist(obj.getHallId())){
            if(feedback != null) feedback.add("Hall with this id exists");
            return false;
        }

        return true;
    }
}
