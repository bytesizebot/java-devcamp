package za.co.entelect.java_devcamp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceFoundException extends RuntimeException{

    public ResourceFoundException(String message){
        super(message);
    }
}
