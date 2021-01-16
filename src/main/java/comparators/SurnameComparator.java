package comparators;

import model.Employee;
import model.User;

import java.util.Comparator;

public class SurnameComparator implements Comparator<User> {
    public int compare(User o1, User o2){
        if(o1 != null && o2 != null){
            return o1.getSurname().compareTo(o2.getSurname());
        }

        return 0;
    }

    @Override
    public String toString(){
        return "Surname";
    }
}
