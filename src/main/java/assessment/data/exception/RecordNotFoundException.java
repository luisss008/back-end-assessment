package assessment.data.exception;

public class RecordNotFoundException extends Exception{

    public RecordNotFoundException(String message){
        super(message);
    }

    public RecordNotFoundException(String message, Throwable ex){
        super(message,ex);
    }

}
