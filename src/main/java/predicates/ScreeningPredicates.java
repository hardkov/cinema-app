package predicates;

import com.google.common.base.Predicates;
import model.Screening;

import java.util.function.Predicate;

public class ScreeningPredicates {
    public static Predicate<Screening> alwaysTrue(){
        return p -> true;
    }

    public static Predicate<Screening> isScreeningMovieTitleEqual(String title){
        return p -> p.getMovie().getTitle().compareTo(title) == 0 ;
    }

}
