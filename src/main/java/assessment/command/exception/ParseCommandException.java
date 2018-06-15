package assessment.command.exception;

public class ParseCommandException extends Exception{

    public ParseCommandException(String message){
        super(message);
    }

    public ParseCommandException(String message, Throwable throwable){
        super(message, throwable);
    }
}
