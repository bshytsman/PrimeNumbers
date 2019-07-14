package com.jetbrains.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyLogger {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void trace(final String message) {
        System.out.println(message);
    }

    public static void trace() {
        System.out.println("");
    }

    public static void traceObj(final String name, final Object object) {
        System.out.println(name + ": " + toJson(object));
    }

    public static String toJson(final Object object) {
        String outStr = "";
        try {
            outStr = mapper.writeValueAsString(object);
        }
        catch (Exception ignore) {

        }
        return outStr;
    }
}
