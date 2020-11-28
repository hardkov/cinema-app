package model;

public class Hall {
     private int hallId;
     private int seatsLimit;

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
}
