package com.limethecoder.entity;


public enum RequestData {
    JOHN_REQUEST_1(1, UserData.USER_JOHN.user,
            StationData.KYIV.station, StationData.KHARKIV.station),
    LESLEY_REQUEST_1(2, UserData.USER_LESLEY.user,
            StationData.KYIV.station, StationData.KHARKIV.station),
    JOHN_REQUEST_2(3, UserData.USER_JOHN.user,
                   StationData.LVIV.station, StationData.ODESSA.station);

    public final Request request;

    RequestData(long id, User passenger, Station departure, Station destination) {
        request = Request.newBuilder()
                .setId(id)
                .setPassenger(passenger)
                .setDeparture(departure)
                .setDestination(destination)
                .build();
    }
}
