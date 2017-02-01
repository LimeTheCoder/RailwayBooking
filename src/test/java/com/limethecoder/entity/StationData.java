package com.limethecoder.entity;


public enum StationData {
    KYIV(1, "South", "Kyiv", "Ukraine"),
    LVIV(2, "Main", "Lviv", "Ukraine"),
    ODESSA(3, "Central", "Odessa", "Ukraine"),
    KHARKIV(4, "Main", "Kharkiv", "Ukraine");

    public final Station station;

    StationData(long id, String name, String city, String country) {
        station = Station.newBuilder()
                .setId(id)
                .setName(name)
                .setCity(city)
                .setCountry(country)
                .build();
    }
}
