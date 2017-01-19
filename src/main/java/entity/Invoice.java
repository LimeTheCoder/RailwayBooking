package entity;


public class Invoice {
    private long id;
    private User passenger;
    private Route route;

    public Invoice() {}

    public Invoice(long id, User passenger, Route route) {
        this.id = id;
        this.passenger = passenger;
        this.route = route;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
