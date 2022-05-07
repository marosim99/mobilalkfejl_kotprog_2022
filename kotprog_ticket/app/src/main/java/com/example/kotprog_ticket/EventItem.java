package com.example.kotprog_ticket;

public class EventItem {
    private String id;
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private int ticketPrice;
    private int unsoldTickets;

    public EventItem() {
    }

    public EventItem(String eventName, String eventDate, String eventLocation, int ticketPrice, int unsoldTickets) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.ticketPrice = ticketPrice;
        this.unsoldTickets = unsoldTickets;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getUnsoldTickets() {
        return unsoldTickets;
    }

    public void setUnsoldTickets(int unsoldTickets) {
        this.unsoldTickets = unsoldTickets;
    }

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
