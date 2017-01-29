package com.limethecoder.entity;


public class Invoice {

    public enum Status {
        PAID, PENDING, REJECTED
    }

    private long id;
    private Request request;
    private Route route;
    private Status status;

    public static class Builder {
        private long id;
        private Request request;
        private Route route;
        private Status status;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setRequest(Request request) {
            this.request = request;
            return this;
        }

        public Builder setRoute(Route route) {
            this.route = route;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Invoice build() {
            Invoice invoice = new Invoice();
            invoice.setId(id);
            invoice.setRequest(request);
            invoice.setStatus(status);
            invoice.setRoute(route);
            return invoice;
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
