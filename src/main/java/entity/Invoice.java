package entity;


public class Invoice {
    private long id;
    private User passenger;
    private Route route;

    public long getId() {
        return id;
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
