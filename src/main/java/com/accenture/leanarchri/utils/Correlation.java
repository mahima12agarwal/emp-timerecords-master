package com.accenture.leanarchri.utils;

/**
 * Utility class which stores ThreadLocal (Request) correlation Id.
 */
public class Correlation {

    public static final String CORRELATION_ID_HEADER = "correlationId";


    private static final ThreadLocal<String> id = new ThreadLocal<String>();


    public static void setId(String correlationId) {
        id.set(correlationId);
    }

    public static String getId() {
        return id.get();
    }
}
