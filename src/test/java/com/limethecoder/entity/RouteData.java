package com.limethecoder.entity;


public enum RouteData {
    KYIV_KHARKIV_ROUTE(1, StationData.KYIV.station, StationData.KHARKIV.station,
            TrainData.SMALL_TRAIN.train, 500),
    LVIV_ODESSA_ROUTE(2, StationData.LVIV.station, StationData.ODESSA.station,
            TrainData.BIG_TRAIN.train, 90);

    public final Route route;

    RouteData(long id, Station departure, Station destination,
              Train train, int price) {

        route = Route.newBuilder()
                .setId(id)
                .setDeparture(departure)
                .setDestination(destination)
                .setTrain(train)
                .setPrice(price)
                .build();
    }
}
