package com.limethecoder.entity;


public enum TrainData {
    SMALL_TRAIN("S6108-19", 3),
    BIG_TRAIN("M6209-19", 13);

    public final Train train;

    TrainData(String serialNo, int capacity) {
        train = new Train(serialNo, capacity);
    }
}
