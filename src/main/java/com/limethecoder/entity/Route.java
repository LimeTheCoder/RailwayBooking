package com.limethecoder.entity;


import java.util.Date;

public class Route {
    private long id;
    private Station departure;
    private Station destination;
    private Date departureTime;
    private Date destinationTime;
    private Train train;
    private Integer reservedCnt;
    private Integer price;

    public static class Builder {
        private final Route route = new Route();

        public Builder setId(long id) {
            route.setId(id);
            return this;
        }

        public Builder setDeparture(Station departure) {
            route.setDeparture(departure);
            return this;
        }

        public Builder setDestination(Station destination) {
            route.setDestination(destination);
            return this;
        }

        public Builder setDepartureTime(Date departureTime) {
            route.setDepartureTime(departureTime);
            return this;
        }

        public Builder setDestinationTime(Date destinationTime) {
            route.setDestinationTime(destinationTime);
            return this;
        }

        public Builder setTrain(Train train) {
            route.setTrain(train);
            return this;
        }

        public Builder setReservedCnt(Integer reservedCnt) {
            route.setReservedCnt(reservedCnt);
            return this;
        }

        public Builder setPrice(Integer price) {
            route.setPrice(price);
            return this;
        }

        public Route build() {
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

    public Integer getReservedCnt() {
        return reservedCnt;
    }

    public void setReservedCnt(Integer reservedCnt) {
        this.reservedCnt = reservedCnt;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFreePlaces() {
        return train.getCapacity() - reservedCnt;
    }
}
