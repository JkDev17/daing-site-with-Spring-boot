package dating.dating.exceptions;

public class ProfileNotFoundException extends RuntimeException
{
    String message;    
    int numberFromStackTrace;

    public ProfileNotFoundException() 
    {
        ;
    }
    public ProfileNotFoundException(String message)
    {
        this.message = message;
    }

    public ProfileNotFoundException(int numberFromStackTrace)
    {
        this.numberFromStackTrace = numberFromStackTrace;
    }
}