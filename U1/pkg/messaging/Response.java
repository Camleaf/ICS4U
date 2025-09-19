package U1.pkg.messaging;

public class Response {
    public String message;
    public int status;
    public Response(String incMessage, int incStatus){
        message = incMessage;
        status = incStatus;
    }
}
