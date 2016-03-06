import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DnsServer extends Remote
{
    String resolveURL(String url) throws RemoteException;

    public final static String LOOKUPNAME = "DNSServer";
}
