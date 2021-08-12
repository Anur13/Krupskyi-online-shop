package com.andrew.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MyLogger {
    private static Logger logger = LogManager.getLogger(MyLogger.class);

    public Logger getLogger (){
        return logger;
    }
}
