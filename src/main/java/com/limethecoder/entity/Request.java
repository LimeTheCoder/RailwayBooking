package com.limethecoder.entity;


import java.util.Date;

public class Request {
    private long id;
    private User passenger;
    private Station departure;
    private Station destination;
    private Date departureTime;
    private Date creationTime;
    private int resultCnt;

    public static class Builder {
        private final Request request = new Request();

        public Builder setId(long id) {
            request.setId(id);
            return this;
        }

        public Builder setPassenger(User passenger) {
            request.setPassenger(passenger);
            return this;
        }

        public Builder setDeparture(Station departure) {
            request.setDeparture(departure);
            return this;
        }

        public Builder setDestination(Station destination) {
            request.setDestination(destination);
            return this;
        }

        public Builder setDepartureTime(Date departureTime) {
            request.setDepartureTime(departureTime);
            return this;
        }

        public Builder setCreationTime(Date creationTime) {
            request.setCreationTime(creationTime);
            return this;
        }

        public Builder setResultCnt(int resultCnt) {
            request.setResultCnt(resultCnt);
            return this;
        }

        public Request build() {
            return request;
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

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public int getResultCnt() {
        return resultCnt;
    }

    public void setResultCnt(int resultCnt) {
        this.resultCnt = resultCnt;
    }
}
