import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthNameserver extends Remote
{
    String getHTTPServer(String url) throws RemoteException;

    public final static String LOOKUPNAME = "AuthNameserver";
}