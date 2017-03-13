package com.kondee.testmodule.exception;

public class PermissionException extends Exception {

    public PermissionException() {
        super();
    }

    public PermissionException(String s) {
        super(s);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }
}
