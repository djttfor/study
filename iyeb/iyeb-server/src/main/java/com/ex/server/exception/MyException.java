package com.ex.server.exception;

public class MyException extends RuntimeException  {

    private static final long serialVersionUID = -8030481791244849827L;

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(Throwable t){super(t);}

    public MyException(String message,Throwable t){
        super(message,t);
    }

    public MyException(String message, boolean enableStackTrace) {
        super(message, null,false, enableStackTrace);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
