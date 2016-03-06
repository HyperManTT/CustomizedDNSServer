import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RootServer extends Remote
{
    String getTLD(String url) throws RemoteException;

    public final static String LOOKUPNAME = "RootServer";
}