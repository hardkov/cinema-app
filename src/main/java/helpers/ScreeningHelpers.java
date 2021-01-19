package helpers;

import model.Screening;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScreeningHelpers {
    public static boolean doScreeningsOverlap(Screening sc1, Screening sc2){
        LocalDate d1 = sc1.getDate();
        LocalTime t1 = sc1.getTime();
        int l1 = sc1.getMovie().getLength();

        LocalDate d2 = sc2.getDate();
        LocalTime t2 = sc2.getTime();
        int l2 = sc2.getMovie().getLength();

        LocalDateTime sc1Start = d1.atTime(t1);
        LocalDateTime sc1End = sc1Start.plusMinutes(l1);

        LocalDateTime sc2Start = d2.atTime(t2);
        LocalDateTime sc2End = sc2Start.plusMinutes(l2);

        return sc1Start.isBefore(sc2End) && sc2Start.isBefore(sc1End);
    }
}
