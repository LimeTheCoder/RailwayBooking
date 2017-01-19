package entity;


import java.util.Date;

public class Route {
    private long id;
    private Station departure;
    private Station destination;
    private Date departureTime;
    private Date destinationTime;
    private Train train;
    private int price;

    public static class Builder {
        private long id;
        private Station departure;
        private Station destination;
        private Date departureTime;
        private Date destinationTime;
        private Train train;
        private int price;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setDeparture(Station departure) {
            this.departure = departure;
            return this;
        }

        public Builder setDestination(Station destination) {
            this.destination = destination;
            return this;
        }

        public Builder setDepartureTime(Date departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder setDestinationTime(Date destinationTime) {
            this.destinationTime = destinationTime;
            return this;
        }

        public Builder setTrain(Train train) {
            this.train = train;
            return this;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Route build() {
            Route route = new Route();

            route.setId(id);
            route.setDeparture(departure);
            route.setDestination(destination);
            route.setDepartureTime(departureTime);
            route.setDestinationTime(destinationTime);
            route.setPrice(price);
            route.setTrain(train);

            return route;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getDeparture() {
        return departure;
    }

    public void setDeparture(Station departure) {
        this.departure = departure;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(Date destinationTime) {
        this.destinationTime = destinationTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
