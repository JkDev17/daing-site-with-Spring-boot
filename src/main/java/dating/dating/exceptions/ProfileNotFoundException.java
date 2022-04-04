package dating.dating.exceptions;

public class ProfileNotFoundException extends RuntimeException
{
    String message;    

    public ProfileNotFoundException() 
    {
        ;
    }
    public ProfileNotFoundException(String message)
    {
        this.message = message;
    }
}