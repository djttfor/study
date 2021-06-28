package my.util.exception;

import java.io.Serializable;

public class MyException extends Exception implements Serializable {
    private static final long serialVersionUID = 1820094919486568652L;
    private String message;

    @Override
    public String toString() {
        return "MyException{" +
                "message='" + message + '\'' +
                '}';
    }

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
