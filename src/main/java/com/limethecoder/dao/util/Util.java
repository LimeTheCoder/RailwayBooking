package com.limethecoder.dao.util;


import java.sql.Timestamp;
import java.util.Date;

public class Util {
    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Date toDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
}
