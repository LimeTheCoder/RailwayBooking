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
        private final Station station = new Station();

        public Builder setId(long id) {
            station.setId(id);
            return this;
        }

        public Builder setName(String name) {
            station.setName(name);
            return this;
        }

        public Builder setCity(String city) {
            station.setCity(city);
            return this;
        }

        public Builder setCountry(String country) {
            station.setCountry(country);
            return this;
        }

        public Station build() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return id == station.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
