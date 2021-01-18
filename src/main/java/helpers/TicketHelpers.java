package helpers;

import daos.TicketDao;
import model.Screening;
import model.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class TicketHelpers {
    public static int getFreeSeatId(Screening screening){
        TicketDao ticketDao = new TicketDao();
        List<Ticket> allTickets = ticketDao.getAllTickets();
        List<Ticket> ticketWithScreening = allTickets.stream().filter(t -> t.getScreening().equals(screening)).collect(Collectors.toList());
        int[] takenSeats = new int[screening.getSeatsLimit()];

        for(Ticket ticket: ticketWithScreening){
            int seatId = ticket.getSeatId();
            takenSeats[seatId] = -1;
        }

        for(int seatId = 0; seatId < takenSeats.length; seatId++){
            if(takenSeats[seatId] == 0) return seatId;
        }

        return -1;
    }
}
