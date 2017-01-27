package entity;


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
        private long id;
        private User passenger;
        private Station departure;
        private Station destination;
        private Date departureTime;
        private Date creationTime;
        private int resultCnt;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPassenger(User passenger) {
            this.passenger = passenger;
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

        public Builder setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder setResultCnt(int resultCnt) {
            this.resultCnt = resultCnt;
            return this;
        }

        public Request build() {
            Request request = new Request();
            request.setId(id);
            request.setCreationTime(creationTime);
            request.setDeparture(departure);
            request.setDestination(destination);
            request.setDepartureTime(departureTime);
            request.setPassenger(passenger);
            request.setResultCnt(resultCnt);
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
