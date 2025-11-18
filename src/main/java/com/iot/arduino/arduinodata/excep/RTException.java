package com.iot.arduino.arduinodata.excep;

public class RTException extends RuntimeException {

    public RTException() {
        super();
    }

    public RTException(String message) {
        super(message);
    }

    public RTException(String message, Throwable cause) {
        super(message, cause);
    }

    public RTException(Throwable cause) {
        super(cause);
    }

    protected RTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
