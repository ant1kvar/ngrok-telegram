package com.kost.config;

import org.apache.log4j.Logger;

public class Log {
    public static Logger logger = Logger.getLogger(Log.class);

    public static void logWarn(String message) {
        logger.warn(message);
    }
}
