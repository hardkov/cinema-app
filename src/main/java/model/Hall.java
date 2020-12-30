package model;

import java.util.Objects;

public class Hall {
     private int hallId;
     private int seatsLimit;

     public Hall() {}

     public Hall(int hallId, int seatsLimit){
         this.hallId = hallId;
         this.seatsLimit = seatsLimit;
     }

    public int getHallId() {
        return hallId;
    }

    public int getSeatsLimit() {
        return seatsLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall)) return false;
        Hall hall = (Hall) o;
        return hallId == hall.hallId &&
                seatsLimit == hall.seatsLimit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, seatsLimit);
    }
}
