package comparators;

import model.Employee;
import model.User;

import java.util.Comparator;

public class LoginComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2){
        if(o1 != null && o2 != null){
            return o1.getLogin().compareTo(o2.getLogin());
        }

        return 0;
    }

    @Override
    public String toString(){
        return "Login";
    }
}
