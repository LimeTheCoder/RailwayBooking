package com.limethecoder.entity;

public class Station {
    private long id;
    private String name;
    private String city;
    private String country;

    public Station() {}

    public Station(long id) {
        this.id = id;
    }

    public static class Builder {
        private long id;
        private String name;
        private String city;
        private String country;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Station build() {
            Station station = new Station();

            station.setId(id);
            station.setCity(city);
            station.setCountry(country);
            station.setName(name);

            return station;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return name + ", " + city;
    }
}
