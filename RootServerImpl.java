import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RootServerImpl extends UnicastRemoteObject implements RootServer
{
    public RootServerImpl() throws RemoteException
    {
        super();
    }

    @Override
    public String getTLD(String url) throws RemoteException {
        /*
        For this program, we only parse .com domains. If the
        domain is .com, we return the name of the top-level
        domain server
         */
        String[] urlSplit = url.split("/");
        for (String str1 : urlSplit)
        {
            if(str1.contains(".com"))
            {
                return TLDNameserverImpl.LOOKUPNAME;
            }
        }

        return "Error";
    }
}