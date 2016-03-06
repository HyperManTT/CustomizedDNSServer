import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HTTPServer extends Remote
{
    String Get(String url) throws RemoteException;

    public final static String LOOKUPNAME = "HTTPServer";
}
