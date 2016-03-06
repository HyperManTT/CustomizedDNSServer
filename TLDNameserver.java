import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TLDNameserver extends Remote
{
    String getAuthNameServer(String url) throws RemoteException;

    public final static String LOOKUPNAME = "TLDNameserver";
}