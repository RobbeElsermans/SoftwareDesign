package database.dbController;

import database.ADatabase;
import database.TicketDB;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketController extends AController<ITicket>{
    private static AController<ITicket> ticketCTRL;
    public TicketController(ADatabase<ITicket> db) {
        super(db);
    }

    public static TicketController getInstance() {
        if (ticketCTRL == null) ticketCTRL = new TicketController(TicketDB.getInstance());
        return (TicketController) ticketCTRL;
    }

    public List<ITicket> getTicketsByPayerId(int payerId){
        List<ITicket> tickets = new ArrayList<>();
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            ITicket value = entry.getValue();
            if (value.getPayerId() == payerId) tickets.add(value);
        }
        return tickets;
    }

    public void distributeDebts(int deletedPersonId, int fallGuyId) {
        if (deletedPersonId == fallGuyId) return;   // do nothing if deleted person = fall guy
        List<Integer> ticketsToRemove = new ArrayList<>();
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            ITicket value = entry.getValue();
            int payerId = value.getPayerId();

            // If the person who paid falls away, then those who owed them money lose their debt to that person
            if (payerId == deletedPersonId) {
                int ticketId = entry.getKey();
                ticketsToRemove.add(ticketId);
            } else {
                // If the person who paid is the fall guy, remove the debt of the deleted person
                // Since the debt will be placed on themselves
                if (payerId == fallGuyId) { value.getDebs().remove(deletedPersonId); }
                // If the person who owes money falls away, then the fall guy must take over their debt
                if (value.getDebs().get(deletedPersonId) != null) {
                    double debt = value.getDebs().remove(deletedPersonId);
                    if (value.getDebs().containsKey(fallGuyId)) {
                        // If there was a previous debt --> add debt
                        double oldDebt = value.getDebs().get(fallGuyId);
                        value.getDebs().put(fallGuyId, oldDebt + debt);
                    } else {
                        // If there was no previous debt --> create debt
                        value.getDebs().put(fallGuyId, debt);
                    }
                }
            }
        }
        for (int ticketId: ticketsToRemove) { delValue(ticketId); }
    }
}
